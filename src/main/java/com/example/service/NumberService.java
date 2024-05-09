package com.example.service;

import java.math.BigDecimal;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class NumberService {

    @Cacheable(
      value = "squareCache", 
      key = "#number", 
      condition = "#number>10")
    public BigDecimal square(Long number) {
        BigDecimal square = BigDecimal.valueOf(number)
          .multiply(BigDecimal.valueOf(number));
        return square;
    }
}