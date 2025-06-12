package com.roaa.payment.service.impl;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.roaa.payment.domain.PaymentMethod;
import com.roaa.payment.domain.PaymentOrderStatus;
import com.roaa.payment.dto.BookingDTO;
import com.roaa.payment.dto.UserDTO;
import com.roaa.payment.model.PaymentOrder;
import com.roaa.payment.payload.response.PaymentLinkResponse;
import com.roaa.payment.repository.PaymentOrderRepository;
import com.roaa.payment.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException {

        Long amount =(long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());

        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){

            PaymentLink payment = createRazorpayPaymentLink(user, savedOrder.getAmount(), savedOrder.getId());

            String paymentUrl = payment.get("short_url");
            String paymentUrlId = payment.get("id");

            paymentLinkResponse.setPayment_link_id(paymentUrl);
            paymentLinkResponse.setPayment_link_url(paymentUrlId);

            savedOrder.setPaymentLinkId(paymentUrlId);

            paymentOrderRepository.save(savedOrder);

        }else {
            String paymentUrl = createStripePaymentLink(user, savedOrder.getAmount(), savedOrder.getId());

            paymentLinkResponse.setPayment_link_url(paymentUrl);
        }

        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {

        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);

        if(paymentOrder == null)
        {
            throw new Exception("payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {

        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long Amount, Long orderId) throws RazorpayException {

        Long amount =  Amount * 100;

        RazorpayClient razorpayClient = new  RazorpayClient(razorpayApiKey, razorpayApiSecret);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", "EP");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());

        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);

        paymentLinkRequest.put("notify", notify);

        paymentLinkRequest.put("reminder_enable", true);

        paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/"+orderId);

        paymentLinkRequest.put("callback_method", "get");

        return razorpayClient.paymentLink.create(paymentLinkRequest);

    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel/"+orderId)
                .addLineItem(SessionCreateParams.LineItem
                        .builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams
                                .LineItem
                                .PriceData
                                .builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("salon appointment booking").build()
                                ).build()
                        ).build()
                ).build();
        Session session = Session.create(params);
        return session.getUrl();

    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {

        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING))
        {
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY))
            {
                RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
                Payment payment = razorpayClient.payments.fetch(paymentId);
                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if (status.equals("captured"))
                {

                    // Will produce kafka event
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);
                    return  true;
                }
                return false;
            }else{
                // Will produce kafka event
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return  true;
            }
        }
        return false;
    }
}
