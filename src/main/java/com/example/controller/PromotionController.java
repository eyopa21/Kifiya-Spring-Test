package com.example.controller;


import com.example.dto.PromotionDto;
import com.example.entity.Promotion;
import com.example.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/promotions") @RequiredArgsConstructor
    public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Promotion> create(@RequestBody @Valid PromotionDto dto) {
        return ResponseEntity.ok(promotionService.createPromotion(dto));
    }
}
