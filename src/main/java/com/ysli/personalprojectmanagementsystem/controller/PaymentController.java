package com.ysli.personalprojectmanagementsystem.controller;

import com.ysli.personalprojectmanagementsystem.modal.PlanType;
import com.ysli.personalprojectmanagementsystem.modal.User;
import com.ysli.personalprojectmanagementsystem.response.PaymentLinkResponse;
import com.ysli.personalprojectmanagementsystem.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {


    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/payments/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable String planType,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
//        User user = userService.findUserProfileByJwt(jwt);
//        int amount = 799 * 100;
//
//        // Adjust amount based on plan type
//        if (planType.equals("ANNUALLY")) {
//            // Apply 30% discount for annual plan
//            amount = (int) (amount * 0.7 * 12);
//        }
//
//

        User user = userService.findUserProfileByJwt(jwt);

        // Return a mock payment link to simulate a free payment
        String paymentLinkUrl = "http://localhost:5173/upgrade_plan/success?planType=" + planType;
        String paymentLinkId = "free_" + planType; // Use a dummy ID to signify it’s free

        // Create response object
        PaymentLinkResponse response = new PaymentLinkResponse(paymentLinkUrl, paymentLinkId);

        return new ResponseEntity<PaymentLinkResponse>(response, HttpStatus.CREATED);


    }

}
