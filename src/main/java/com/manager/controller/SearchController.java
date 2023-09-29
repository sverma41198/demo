package com.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.manager.entities.Item;
import com.manager.repository.ItemRepository;

@RestController
public class SearchController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query){

        System.out.println(query);
        
        List<Item> items = itemRepository.findByItemNameContaining(query);
        return ResponseEntity.ok(items);
    }
}