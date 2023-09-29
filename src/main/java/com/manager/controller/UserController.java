package com.manager.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manager.entities.Cards;
import com.manager.entities.Cart;
import com.manager.entities.Item;

@Controller
@RequestMapping("/user")
public class UserController extends Comman {

// home dashboard page
	@Override
	public String dashboard(Integer page, Model model) {
		return super.dashboard(page, model);
	}

// for profile 
	@Override
	public String profile(Model model, Principal principal) {
		return super.profile(model, principal);
	}
// for-setting 
	@Override
	public String setting(Model model) {
		return super.setting(model);
	}

// for change password
	@Override
	public String changePassword(String oldPassword, String newPassword, Principal principal) {
		return super.changePassword(oldPassword, newPassword, principal);
	}

// for cart 
	@Override
	public String cart(Model model, Principal principal) {
		return super.cart( model, principal);
	}

	
// for process cart
	@Override
	public String processCart(Cart cart, Model model, Principal principal) {
		return super.processCart(cart, model, principal);
	}

// for delete the item in cart
	@Override
	public String delete(Cart cart) {
		return super.delete(cart);
	}

// for cards 
	@Override
	public String cards(Model model) {
		return super.cards(model);
	}

// for cards process
	@Override
	public String processCards(Cards cards, Model model, Principal principal) {
		return super.processCards(cards, model, principal);
	}

// for buy
	@Override
	public String buy(Model model, Item cards) {
		return super.buy(model, cards);
	}

// for process buy
	@Override
	public String processBuy(Cards card, Model model, Principal principal, int price) {
		return super.processBuy(card, model, principal, price);
	}
}