package org.perscholas.homemade.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.AuthGroupRepoI;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.AuthGroup;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Product;
import org.perscholas.homemade.services.ImageService;
import org.perscholas.homemade.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
@Slf4j
public class ChefController {

    ChefRepoI chefRepoI;
    ProductRepoI productRepoI;
    ProductService productService;
    ImageService imageService;
    AuthGroupRepoI authGroupRepoI;



    @Autowired
    public ChefController(ChefRepoI chefRepoI, ProductRepoI productRepoI, ProductService productService, ImageService imageService, AuthGroupRepoI authGroupRepoI) {
        this.chefRepoI = chefRepoI;
        this.productRepoI = productRepoI;
        this.productService = productService;
        this.imageService=imageService;
        this.authGroupRepoI=authGroupRepoI;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "chefRegistration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("chef") Chef user) {
        chefRepoI.save(user);
        authGroupRepoI.save(new AuthGroup(user.getEmail(),"ROLE_CHEF"));
        return "redirect:/chefLogin";
    }

    @GetMapping("/chefLogin")
    public String showChefLogin() {
        log.warn("Chef Login Controller");
        return "chefLogin";
    }

    @ModelAttribute
    public void theChef(Model model, HttpServletRequest request, HttpSession http){
        Principal p = request.getUserPrincipal();
        Chef ss = null;
        if(p != null){
            ss =  chefRepoI.findByEmail(p.getName()).get();
            http.setAttribute("theChef", ss);
            log.warn("session attr theChef in advice controller  " + http.getAttribute("theChef").toString());

        }
        model.addAttribute("theChef", ss);
    }
    @GetMapping("/chefProduct")
    public String showChefProduct(@ModelAttribute("theChef") Chef chef, Model model){
        log.warn(chef.toString());
        model.addAttribute("products",productRepoI.findByChef(chef));
        return "chefProduct";
    }
    @GetMapping("/addNewProduct")
    public String showAddNewProductPage(Model model){
        model.addAttribute("product", new Product());
        return "addNewProduct";
    }
    @PostMapping("/addNewProduct")
    public String addNewProductPage(@ModelAttribute("product") Product product, @ModelAttribute("theChef") Chef chef,  @RequestParam("file") MultipartFile file) throws Exception{
        product.setChef(chef);
        productRepoI.save(product);
        imageService.save(file,product);
        return "redirect:/chefProduct";
    }

    @GetMapping("/updateForm/{id}")
    public String showUpdateForm(@PathVariable(name = "id") int id, Model model) throws Exception {
        model.addAttribute("product",productRepoI.findById(id));
        return "chefUpdateProduct";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("product") Product product, Model model){
        model.addAttribute("product",product);
        model.addAttribute("updateProduct",new Product());
        return "chefUpdateProduct";
    }
    @PostMapping("/updateProduct")
    public String createorupdateProduct(@ModelAttribute("updateProduct") Product product, Model model){
        log.warn(product.toString());
        Product p = productService.createOrUpdate(product);
        model.addAttribute("updateProduct",p);
        return "redirect:/chefProduct";
    }
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@ModelAttribute("product") Product product, Model model){
        productRepoI.delete(product);
        return "redirect:/chefProduct";
    }
}
