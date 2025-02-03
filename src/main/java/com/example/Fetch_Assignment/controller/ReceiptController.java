package com.example.Fetch_Assignment.controller;

import com.example.Fetch_Assignment.service.ReceiptService;
import com.example.Fetch_Assignment.model.Receipt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> handleReceiptProcessing(@RequestBody Receipt receipt) {
        String receiptId = receiptService.processReceipt(receipt);

        Map<String, String> responsePayload = new HashMap<>();
        responsePayload.put("id", receiptId);
        System.out.println("Generated Response: " + responsePayload);
        return ResponseEntity.ok(responsePayload);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> fetchPoints(@PathVariable("id") String id) {
        Integer accumulatedPoints = receiptService.getPoints(id);

        if (accumulatedPoints == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Map<String, Integer> responsePayload = new HashMap<>();
        responsePayload.put("points", accumulatedPoints);

        return ResponseEntity.ok(responsePayload);
    }

}
