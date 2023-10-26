package com.booking.bookingApp.repository;

import com.booking.bookingApp.dto.ProductDto;
import com.booking.bookingApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    @Query(
            nativeQuery = true,
            value = """
                    SELECT DISTINCT p.* FROM product p
                    LEFT JOIN location l ON l.id = p.location_id
                    LEFT JOIN reservation r ON r.product_id = p.id
                    WHERE l.city_id LIKE CONCAT("%", :cityId, "%") and p.category_id LIKE CONCAT("%", :categoryId, "%")
                    AND NOT EXISTS (
                        SELECT * FROM reservation r WHERE r.product_id = p.id
                        AND (
                            (:initialDate >= r.initial_date AND :finalDate <= r.final_date)
                            OR (:initialDate >= r.initial_date AND :initialDate <= r.final_date)
                            OR (:finalDate > r.initial_date AND :finalDate <= r.final_date)
                            OR (:initialDate < r.initial_date AND :finalDate > r.final_date)
                        )
                    )
                    """
    )
    List<Product> filterProducts(@Param("cityId") String cityId, @Param("categoryId") String categoryId, @Param("initialDate") String initialDate, @Param("finalDate") String finalDate);
    @Query(nativeQuery = true, value = "SELECT * FROM product ORDER BY RAND() LIMIT ?1")
    List<Product> findXRandomProducts(Integer nrOfProds);
}
