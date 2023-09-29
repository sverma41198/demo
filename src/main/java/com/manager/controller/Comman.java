package com.manager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manager.entities.Cards;
import com.manager.entities.Cart;
import com.manager.entities.Item;
import com.manager.entities.User;
import com.manager.repository.CardsRepository;
import com.manager.repository.CartRepository;
import com.manager.repository.ItemRepository;
import com.manager.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

public class Comman implements IComman {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CardsRepository cardsRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bEncoder;

	// method for adding common data
	@Override
	public void addCommonData(Model model, Principal principal, HttpServletRequest request) {
		String name = principal.getName();
		User user = userRepository.getUserByUsername(name);
		model.addAttribute("user", user);
		if (request.isUserInRole("ROLE_ADMIN")) {
			model.addAttribute("default", "admin");
		}
		if (request.isUserInRole("ROLE_USER")) {
			model.addAttribute("default", "user");
		}
	}

	// home dashboard page
	@Override
	public String dashboard(@PathVariable("page") Integer page, Model model) {
		model.addAttribute("title", "View Items");
		Pageable pageable = PageRequest.of(page, 5);
		Page<Item> items = itemRepository.getAllItem(pageable);
		model.addAttribute("item", items);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", items.getTotalPages());
		return "normal/user-dashboard";
	}

	// add item form
	@Override
	public String addItem(Model model) {
		model.addAttribute("title", "Add Item");
		model.addAttribute("item", new Item());
		return "normal/add_item";
	}

	// processing of adding item
	@Override
	public String processItem(@ModelAttribute("item") Item item, Model model, MultipartFile file) {
		try {
			if (file.isEmpty()) {
				item.setImage("download.png");
			} else {
				item.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			itemRepository.save(item);
			System.out.println("Successfully added.....");
			model.addAttribute("message", "Successfully added..");
		} catch (Exception exc) {
			System.out.println("error");
			exc.printStackTrace();
		}
		return "redirect:/admin/add-item";
	}

	// for particular item
	@Override
	public String itemDetail(@PathVariable("id") Integer id, Model model, Principal principal) {
		Optional<Item> itemId = itemRepository.findById(id);
		Item item = itemId.get();

		String name = principal.getName();
		User user = userRepository.getUserByUsername(name);

		model.addAttribute("item", item);
		model.addAttribute("title", user.getName());

		return "normal/user-detail";
	}

	// for delete item
	@Override
	public String deleteItem(Integer itemId, Model model, Principal principal) {

		itemRepository.deleteById(itemId);
		System.out.println("deleted.....");

		model.addAttribute("message", "Item Deleted");
		return "redirect:/user/dashboard/0";
	}

	// for update item
	@Override
	public String updateItem(@PathVariable("itemId") Integer itemId, Model model) {
		model.addAttribute("title", "Update Item");
		Item item = itemRepository.findById(itemId).get();
		model.addAttribute("item", item);
		return "normal/update-item";
	}

	// for update process
	@Override
	public String updateProcess(@ModelAttribute Item item, Model model, MultipartFile file) {
		try {
			// old item details
			Item oldItem = this.itemRepository.findById(item.getItemId()).get();
			if (!file.isEmpty()) {
				// delete old photo
				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldItem.getImage());
				file1.delete();
				// update new photo

				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				item.setImage(file.getOriginalFilename());

			} else {
				item.setImage(oldItem.getImage());
			}
			// User user = this.userRepository.getUserByUserName(principal.getName());
			// item.setUser(user);
			this.itemRepository.save(item);
		} catch (Exception e) {

			e.printStackTrace();
		}
		itemRepository.save(item);
		model.addAttribute("message", "Successfully updated");
		return "redirect:/admin/item-detail/" + item.getItemId();
	}

	// for profile
	@Override
	public String profile(Model model, Principal principal) {
		model.addAttribute("title", "Profile");
		return "normal/profile";
	}

	// for-setting
	@Override
	public String setting(Model model) {
		model.addAttribute("title", "Setting");
		return "normal/setting";
	}
	// for change password

	@Override
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			Principal principal) {
		User user = userRepository.getUserByUsername(principal.getName());

		if (bEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(bEncoder.encode(newPassword));
			userRepository.save(user);
			return "redirect:/user/dashboard";
		} else {
			return "redirect:/user/setting";
		}
	}

	// for cart
	@Override
	public String cart(Model model, Principal principal) {
		model.addAttribute("title", "Cart");
		User user = userRepository.getUserByUsername(principal.getName());
		ArrayList<Cart> name = cartRepository.getCartByUserId(user.getId());
		int sum = 0;
		for (Cart i : name) {
			sum += i.getPrice();
		}
		model.addAttribute("name", name);
		model.addAttribute("sum", sum);
		return "normal/cart";
	}

	// for process cart
	@Override
	public String processCart(@ModelAttribute Cart cart, Model model, Principal principal) {
		User user = userRepository.getUserByUsername(principal.getName());
		Boolean existsByItemName = cartRepository.existsByItemName(cart.getItemName());
		Item findName = itemRepository.findByItemName(cart.getItemName());
		if (existsByItemName) {

			Cart cart1 = cartRepository.findByItemName(cart.getItemName());

			cart1.setQuantity(cart1.getQuantity() + 1);
			cart1.setPrice(cart1.getPrice() + cart.getPrice());
			cartRepository.save(cart1);
		} else {
			cart.setImage(findName.getImage());
			cart.setUser(user);
			cart.setQuantity(1);
			cartRepository.save(cart);
		}
		return "redirect:/user/dashboard/0";
	}

	// for delete the item in cart
	public String delete(Cart cart) {
		Cart cart1 = cartRepository.findByItemName(cart.getItemName());
		Item itemprice = itemRepository.findByItemName(cart1.getItemName());
		if (cart1.getQuantity() == 0) {
			cartRepository.delete(cart1);
		} else {
			cart1.setQuantity(cart1.getQuantity() - 1);
			cart1.setPrice(cart1.getPrice() - itemprice.getPrice());
		}
		cartRepository.save(cart1);
		return "redirect:/user/cart";
	}

	// for cards
	@Override
	public String cards(Model model) {
		model.addAttribute("title", "Add Cards");
		model.addAttribute("cards", new Cards());
		return "normal/cards";
	}

	// for cards process
	public String processCards(@ModelAttribute Cards cards, Model model, Principal principal) {
		User user = userRepository.getUserByUsername(principal.getName());
		cards.setUser(user);
		cardsRepository.save(cards);
		System.out.println("Successfully added.....");
		model.addAttribute("message", "Successfully added..");
		return "redirect:/user/cards";
	}

	// for buy
	@Override
	public String buy(Model model, Item item) {
		model.addAttribute("title", "your order");
		model.addAttribute("price", item.getPrice());
		return "normal/buy";
	}

	// for process buy
	@Override
	public String processBuy(Cards card, Model model, Principal principal, int price) {
		User user = userRepository.getUserByUsername(principal.getName());
		List<Cards> cards = cardsRepository.getCardsByUserId(user.getId());
		for (Cards a : cards) {
			if (a.getCardNo().equals(card.getCardNo()) && a.getHolderName().equals(card.getHolderName()) &&
					a.getCvv().equals(card.getCvv()) && a.getPin().equals(card.getPin())) {
				if (a.getAmount() >= price) {
					a.setAmount(a.getAmount() - price);
					cardsRepository.save(a);
					return "normal/buy-success";
				}
			}
		}
		return "redirect:/user/dashboard/0";
	}
}