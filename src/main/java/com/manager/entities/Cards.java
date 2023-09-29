package com.manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cards {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @Size(min = 10,max=10)
    private String cardNo;
    private String holderName;
    @Size(min = 3,max = 3)
    private String cvv;
    private int amount;
    @Size(min = 4, max = 4)
    private String pin;

    @ManyToOne
	@JsonIgnore
	private User user;

}
