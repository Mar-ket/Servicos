package com.marketplace.payments;

import com.stripe.exception.StripeException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {
    
    @Autowired
    private StripeService stripeService;
    
    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody String body) {
        try 
        {
            PaymentRequest request = PaymentRequest.fromJsonString(body);
            JSONObject paymentLinkResponse = stripeService.createPaymentLink(
                request.getCurrency(),
                request.getProducts(),
                request.getSuccessUrl(),
                request.getCancelUrl()
            );
            return ResponseEntity.ok(paymentLinkResponse.toString());
        } catch (StripeException | ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/payment_status/{sessionId}")
    public ResponseEntity<String> paymentSessionStatus(@PathVariable String sessionId) 
    {
        try 
        {
            JSONObject paymentStatusResponse = stripeService.getPaymentSessionStatus(sessionId);
            return ResponseEntity.ok(paymentStatusResponse.toString());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
