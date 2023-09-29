package com.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manager.entities.Cards;

public interface CardsRepository extends JpaRepository<Cards, Integer> {
    
    public List<Cards> getCardsByUserId(int userId);

}
