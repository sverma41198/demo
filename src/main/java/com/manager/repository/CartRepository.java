package com.manager.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import com.manager.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Boolean existsByItemName(String itemName);

    Cart findByItemName(String itemName);

    ArrayList<Cart> getCartByUserId(int userId);

}