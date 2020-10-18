package com.dibasb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String home(Model model) {
		//model.addAllAttributes("message","findByRepository");
		return "userhome";
	}

	@PostMapping("/messages")
	public String saveMessage(String message) {
		//messageRepository.save(message);
		return "redirect:/home";
	}
	
	
}
