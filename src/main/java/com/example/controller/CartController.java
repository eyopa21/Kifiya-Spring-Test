package com.example.controller;

import com.example.dto.*;
import com.example.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/cart") @RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/quote")
    public ResponseEntity<CartQuoteResponse> quote(@RequestBody @Valid CartRequest request) {
        return ResponseEntity.ok(cartService.calculateQuote(request));
    }

    @PostMapping("/confirm")
    public ResponseEntity<OrderResponse> confirm(
            @RequestBody @Valid CartRequest request,
            @RequestHeader(value = "Idempotency-Key", required = false) String key) {
        return ResponseEntity.ok(cartService.confirmOrder(request, key));
    }
}
