package org.perscholas.homemade.controllers;

import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ChefController {

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
        model.addAttribute("chef",new Chef());
        return "chefLogin";
    }

    @PostMapping("/chefLogin")
    public String showProductPage() {

        return "redirect:/chefProduct";
    }

    @GetMapping("/chefProduct")
    public String showChefProduct(){
        return "chefProduct";
    }
    @GetMapping("/addNewProduct")
    public String showAddNewProductPage(Model model){
        model.addAttribute("product", new Product());
        return "addNewProduct";
    }
    @PostMapping("/addNewProduct")
    public String addNewProductPage(@ModelAttribute("product") Product product, @ModelAttribute("theChef") Chef chef){
        product.setChef(chef);
        productRepoI.save(product);
        return "chefProduct";
    }
}
