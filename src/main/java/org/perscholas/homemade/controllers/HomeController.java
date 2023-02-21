package org.perscholas.homemade.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.models.Chef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Slf4j
@SessionAttributes(value = {"msg"})
public class HomeController {
    ChefRepoI chefRepoI;

    @Autowired
    public HomeController(ChefRepoI chefRepoI) {
        this.chefRepoI = chefRepoI;
    }

    @GetMapping("/index")
    public String model(Model model, HttpServletRequest httpServletRequest){
        log.warn(model.getAttribute("msg").toString());
        HttpSession session = httpServletRequest.getSession();
        log.warn(session.getId());
        session.setAttribute("msg", "changed in index method!!");
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chefRegisteration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("chef") Chef user) {
        chefRepoI.save(user);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
