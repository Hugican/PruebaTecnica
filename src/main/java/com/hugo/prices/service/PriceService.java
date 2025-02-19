package com.hugo.prices.service;

import java.time.LocalDateTime;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.hugo.prices.respository.PriceRepository;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

   
}
