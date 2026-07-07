package com.hexaware.hotbyte.repository;

import com.hexaware.hotbyte.entity.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage, Integer> {
    List<MenuImage> findByMenuItem_MenuItemId(Integer menuItemId);
    void deleteByMenuItem_MenuItemId(Integer menuItemId);
}
