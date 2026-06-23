package com.hexaware.hotbyte.repository;

import com.hexaware.hotbyte.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByRestaurantRestaurantId(Integer restaurantId);
    List<MenuItem> findByCategoryCategoryId(Integer categoryId);
}
