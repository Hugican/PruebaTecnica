package com.hugo.prices.respository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hugo.prices.entity.Price;


public interface PriceRepository extends JpaRepository<Price, Long> {

	
	/**
	 * Consulta personalizada que obtiene una lista de precios aplicables para un producto de una determinada marca
	 * en función de la fecha de aplicación proporcionada.
	 * 
	 * Se seleccionan aquellos registros de la entidad Price donde:
	 * - El identificador de la marca (brandId) coincide con el parámetro dado.
	 * - El identificador del producto (productId) coincide con el parámetro dado.
	 * - La fecha de aplicación (applicationDate) se encuentra entre la fecha de inicio (startDate) y la fecha de finalización (endDate) del precio.
	 * 
	 * Además, los resultados se ordenan de forma descendente según la prioridad (priority), de modo que el precio con mayor prioridad
	 * aparezca primero.
	 * 
	 * @param brandId         Identificador de la marca.
	 * @param productId       Identificador del producto.
	 * @param applicationDate Fecha para la cual se debe verificar la validez del precio.
	 * @return Lista de precios aplicables ordenada por prioridad en orden descendente.
	 */
	@Query("SELECT p FROM Price p " +
	           "WHERE p.brandId = :brandId " +
	           "  AND p.productId = :productId " +
	           "  AND :applicationDate BETWEEN p.startDate AND p.endDate " +
	           "ORDER BY p.priority DESC")
	    List<Price> findApplicablePrices(@Param("brandId") Long brandId,
	                                     @Param("productId") Long productId, 
	                                     @Param("applicationDate") LocalDateTime applicationDate);
	}