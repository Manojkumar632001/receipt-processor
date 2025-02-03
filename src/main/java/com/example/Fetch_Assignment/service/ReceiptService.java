package com.example.Fetch_Assignment.service;

import com.example.Fetch_Assignment.model.Receipt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReceiptService {

    private final Map<String, Integer> receiptPointsRepository = new HashMap<>();

    public String processReceipt(Receipt receipt) {
        String receiptId = UUID.randomUUID().toString();
        int calculatedPoints = computePoints(receipt);
        receiptPointsRepository.put(receiptId, calculatedPoints);
        return receiptId;
    }

    public Integer getPoints(String id) {
        return receiptPointsRepository.get(id);
    }

    private int computePoints(Receipt receipt) {
        int points = 0;

        // Assign points based on retailer name length (excluding non-alphanumeric characters)
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Bonus points for round dollar totals
        double totalAmount = Double.parseDouble(receipt.getTotal());
        if (totalAmount == Math.floor(totalAmount)) {
            points += 50;
        }

        // Extra points for multiples of 0.25 in total amount
        if (totalAmount % 0.25 == 0) {
            points += 25;
        }

        // Reward points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // Points based on item description length
        for (var item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }

        // Bonus for purchases made on odd days
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 != 0) {
            points += 6;
        }

        // Extra reward for transactions between 2:00 PM - 4:00 PM
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        if (hour >= 14 && hour < 16) {
            points += 10;
        }

        return points;
    }
}
