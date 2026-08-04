package com.bodega.controlweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(HttpSession session) {
		if (session.getAttribute("usuarioLogueado") == null) {
			return "redirect:/login";
		}
		return "/Home/home";
	}
}
