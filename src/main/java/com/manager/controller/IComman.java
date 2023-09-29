package com.manager.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manager.entities.Cards;
import com.manager.entities.Cart;
import com.manager.entities.Item;

import jakarta.servlet.http.HttpServletRequest;

public interface IComman {
    
    @ModelAttribute
	public void addCommonData(Model model, Principal principal, HttpServletRequest request);

	@GetMapping("/dashboard/{page}")
	public String dashboard(@PathVariable("page") Integer page, Model model);

	@GetMapping("/add-item")
	public String addItem(Model model);

	@PostMapping("/process-item")
	public String processItem(@ModelAttribute Item item, Model model,@RequestParam("myImage") MultipartFile file);

	@GetMapping("/item-detail/{id}")
	public String itemDetail(@PathVariable("id") Integer id,Model model, Principal principal);

	@GetMapping("/delete/{itemId}")
	public String deleteItem(@PathVariable("itemId") Integer itemId,Model model, Principal principal);

	@PostMapping("/update/{itemId}")
	public String updateItem(@PathVariable("itemId") Integer itemId, Model model);

	@PostMapping("/update-process")
	public String updateProcess(@ModelAttribute Item item, Model model, MultipartFile file);

	@GetMapping("/profile")
	public String profile(Model model, Principal principal);

	@GetMapping("/setting")
	public String setting(Model model);

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Principal principal);

	@GetMapping("/cart")
	public String cart(Model model, Principal principal);

	@PostMapping("/process-cart")
	public String processCart(@ModelAttribute Cart cart, Model model, Principal principal);

	@GetMapping("/cards")
	public String cards(Model model);

	@PostMapping("/process-cards")
	public String processCards(@ModelAttribute Cards card, Model model, Principal principal);

	@PostMapping("/process-delete")
	public String delete(@ModelAttribute Cart cart);

	@GetMapping("/buy")
	public String buy(Model model, @ModelAttribute Item item);

	@PostMapping("process-buy")
	public String processBuy(@ModelAttribute Cards cards, Model model, Principal principal, @RequestParam("price") int price);

}

