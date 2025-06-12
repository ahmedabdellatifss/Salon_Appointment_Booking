package com.roaa.payment.controller;

import com.razorpay.RazorpayException;
import com.roaa.payment.domain.PaymentMethod;
import com.roaa.payment.dto.BookingDTO;
import com.roaa.payment.dto.UserDTO;
import com.roaa.payment.model.PaymentOrder;
import com.roaa.payment.payload.response.PaymentLinkResponse;
import com.roaa.payment.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod
    ) throws StripeException, RazorpayException {

        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setFullName("Ahmed Mohammed");
        user.setEmail("ahmed@gmail.com");

        PaymentLinkResponse res = paymentService.createOrder(user, booking, paymentMethod);

        return ResponseEntity.ok(res);

    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {

        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);

        return ResponseEntity.ok(res);
    }


    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws Exception
    {
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean res = paymentService.proceedPayment(paymentOrder,paymentId, paymentLinkId);

        return ResponseEntity.ok(res);
    }

}
