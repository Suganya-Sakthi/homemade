package org.perscholas.homemade.controllers;

import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("chef")
public class ChefController {



    @ModelAttribute("chef")
    public Chef setupChef(){
        return new Chef();
    }
    ChefRepoI chefRepoI;

    ProductRepoI productRepoI;


    @Autowired
    public ChefController(ChefRepoI chefRepoI, ProductRepoI productRepoI) {
        this.chefRepoI = chefRepoI;
        this.productRepoI=productRepoI;
    }



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chefRegistration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("chef") Chef user) {
        chefRepoI.save(user);
        return "redirect:/chefLogin";
    }

    @GetMapping("/chefLogin")
    public String showChefLogin(@ModelAttribute("chef") Chef chef, Model model) {
        model.addAttribute("chef",chef);
        return "chefLogin";
    }


    @PostMapping("/chefLogin")
    public String showProductPage(@ModelAttribute("chef") Chef chef, Model model) {
        model.addAttribute(chef.getProducts());
        return "redirect:/chefProduct";
    }
    @GetMapping("/addNewProduct")
    public String showAddNewProductPage(){
        return "addNewProduct";
    }

    @GetMapping("/chefProduct")
    public String showChefProduct(Model model){
        return "chefProduct";
    }
   /* @PostMapping("/chefProduct")
    public String product(@ModelAttribute("product") Product product) {
        productRepoI.save(product);
        return "redirect:/success";
    }*/


}