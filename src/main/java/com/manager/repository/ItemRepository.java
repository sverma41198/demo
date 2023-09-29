package com.manager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manager.entities.Item;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    
    @Query("from Item as i ")
    public Page<Item> getAllItem(Pageable pageable);

// for search
    // public List<Item> findByItemNameContainingAndUser(String ItemName,User user);
    public List<Item> findByItemNameContaining(String ItemName);

    Item findByItemName(String itemName);

}