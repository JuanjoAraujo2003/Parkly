package com.parkingadmin.controller;

import com.parkingadmin.dto.CreatePayment;
import com.parkingadmin.dto.Payment;
import com.parkingadmin.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService payments;

    public PaymentController(PaymentService payments) {
        this.payments = payments;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment register(@Valid @RequestBody CreatePayment request) {
        return payments.register(request.vehicle(), request.amount());
    }
}
