package com.hugo.prices.service;

import java.time.LocalDateTime;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.hugo.prices.domain.PriceResponse;
import com.hugo.prices.entity.Price;
import com.hugo.prices.error.PriceNotFoundException;
import com.hugo.prices.respository.PriceRepository;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    
    /**
     * Retorna la tarifa aplicable para el producto y marca en la fecha indicada,
     * en caso de existir varios productos que lo complan se selecciona la de mayor prioridad
     *  o lanzando una excepción si no existe ninguno.
     */
    public PriceResponse getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findApplicablePrices(brandId, productId, applicationDate)
                .stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .map(price -> new PriceResponse(
                        price.getProductId(),
                        price.getBrandId(),
                        price.getPriceList(),
                        price.getStartDate(),
                        price.getEndDate(),
                        price.getPrice()
                ))
                .orElseThrow(() -> new PriceNotFoundException("No se encontró tarifa para los parámetros proporcionados."));
    }
}
