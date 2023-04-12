package com.inti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inti.model.Personne;
import com.inti.model.Utilisateur;
import com.inti.repository.IUtilisateurRepository;
@Controller
@RequestMapping("admin")
public class AdministrateurController {
	
	@Autowired
	IUtilisateurRepository iur;

	@GetMapping("listeUtilisateurs")
	public String listeUtilisateurs(Model m) {
		m.addAttribute("listeUtilisateurs", iur.findAll());
		return "listeUtilisateurs";
	}
	@GetMapping("getUtilisateur/{id}")
	public String getVoiture(@PathVariable("id") int id, Model m) {
		m.addAttribute("utilisateur", iur.findById(id).get());
		return "getUtilisateur";
	}
	@GetMapping("deleteUtilisateur/{id}")
	public String deleteUtilisateur(@PathVariable("id") int id) {
		iur.deleteById(id);
		return "redirect://localhost:8080/admin/listeUtilisateurs";
	}
	
	@GetMapping("modifierUtilisateur/{id}")
	public String modifierUtilisateur(@PathVariable("id") int id, Model m)
	{
		System.out.println(id);
		m.addAttribute("u", iur.findById(id).get());
		return "modifierUtilisateur";
	}
	
	@PostMapping("updateUtilisateur")
	public String updateUtilisateur(@ModelAttribute("utilisateur") Utilisateur u)
	{
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		u.setMdp(b.encode(u.getMdp()));
		System.out.println(u);
		System.out.println(u.getNom() + u.getLogin());
		iur.save(u);
		return "redirect://localhost:8080/admin/listeUtilisateurs";
	}
}
