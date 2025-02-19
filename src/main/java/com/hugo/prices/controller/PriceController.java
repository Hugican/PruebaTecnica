package com.hugo.prices.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hugo.prices.domain.PriceResponse;
import com.hugo.prices.service.PriceService;

import java.time.LocalDateTime;

/**
 * Controlador REST que expone el endpoint para consultar el precio aplicable.
 * Recibe como parámetros: fecha de aplicación, identificador de producto e
 * identificador de cadena, y devuelve la información del precio (producto,
 * cadena, tarifa, fechas y precio final).
 */
@RestController
@RequestMapping("/api/prices")
public class PriceController {

	private final PriceService priceService;

	public PriceController(PriceService priceService) {
		this.priceService = priceService;
	}

	@GetMapping("/applicable")
	public PriceResponse getApplicablePrice(
			@RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
			@RequestParam("productId") Long productId, 
			@RequestParam("brandId") Long brandId) {
		return priceService.getApplicablePrice(applicationDate, productId, brandId);
	}
}