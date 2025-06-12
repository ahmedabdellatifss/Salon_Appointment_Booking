package com.roaa.payment.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.roaa.payment.domain.PaymentMethod;
import com.roaa.payment.dto.BookingDTO;
import com.roaa.payment.dto.UserDTO;
import com.roaa.payment.model.PaymentOrder;
import com.roaa.payment.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {


    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId ) throws RazorpayException;

    String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;

}
