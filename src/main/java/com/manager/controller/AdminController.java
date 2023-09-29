package com.manager.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.manager.entities.Item;

@Controller
@RequestMapping("/admin")
public class AdminController extends Comman {

// home dashboard page
@Override
public String dashboard(Integer page, Model model) {
	return super.dashboard(page, model);
}	

// add item form
	@Override
	public String addItem(Model model) {
		return super.addItem(model);
	}

// processing of adding item
	@Override
	public String processItem(Item item, Model model, MultipartFile file) {
		return super.processItem(item, model, file);
	}

// for particular item
	@Override
	public String itemDetail(Integer id, Model model, Principal principal) {
		return super.itemDetail(id, model, principal);
	}

// for delete item
	@Override
	public String deleteItem(Integer itemId, Model model, Principal principal) {
		return super.deleteItem(itemId, model, principal);
	}

// for update item
	@Override
	public String updateItem(Integer itemId, Model model) {
		return super.updateItem(itemId, model);
	}

// for update process
	@Override
	public String updateProcess(Item item, Model model,MultipartFile file) {
		return super.updateProcess(item, model, file);
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

}