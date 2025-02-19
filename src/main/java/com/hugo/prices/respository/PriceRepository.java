package com.hugo.prices.respository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hugo.prices.entity.Price;


public interface PriceRepository extends JpaRepository<Price, Long> {

	@Query("SELECT p FROM Price p " +
	           "WHERE p.brandId = :brandId " +
	           "  AND p.productId = :productId " +
	           "  AND :applicationDate BETWEEN p.startDate AND p.endDate " +
	           "ORDER BY p.priority DESC")
	    List<Price> findApplicablePrices(@Param("brandId") Long brandId,
	                                     @Param("productId") Long productId, 
	                                     @Param("applicationDate") LocalDateTime applicationDate);
	}