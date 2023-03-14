package org.perscholas.homemade.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.AuthGroupRepoI;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.OrderRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.AuthGroup;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Customer;
import org.perscholas.homemade.models.Product;
import org.perscholas.homemade.services.ImageService;
import org.perscholas.homemade.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    OrderRepoI orderRepoI;

    //This  is an autowired constructor of all the repositories used in this controller
    @Autowired
    public ChefController(ChefRepoI chefRepoI, ProductRepoI productRepoI, ProductService productService, ImageService imageService, AuthGroupRepoI authGroupRepoI, OrderRepoI orderRepoI) {
        this.chefRepoI = chefRepoI;
        this.productRepoI = productRepoI;
        this.productService = productService;
        this.imageService=imageService;
        this.authGroupRepoI=authGroupRepoI;
        this.orderRepoI=orderRepoI;
    }

    //This mapping is to create a new instance of a chef model
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("chef", new Chef());
        model.addAttribute("count",orderRepoI.count());
        return "chefRegistration";
    }

    //This is to add a new chef to the chef model
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.error("Chef has errors: " + chef);
            log.error(bindingResult.getAllErrors().toString());
            return "chefRegistration";
        }
        chefRepoI.save(chef);
        authGroupRepoI.save(new AuthGroup(chef.getEmail(),"ROLE_CHEF"));
        return "redirect:/chefLogin";
    }
    //This mapping is to show the login page to the user
    @GetMapping("/chefLogin")
    public String showChefLogin(Model model) {
        log.warn("Chef Login Controller");
        model.addAttribute("count",orderRepoI.count());
        return "chefLogin";
    }
    //This model attribute is used to get the pricipal user from the spring security
    @ModelAttribute
    public final void theChef(Model model, HttpServletRequest request, HttpSession http){
        Principal p = request.getUserPrincipal();
        Chef ss = null;
        if(p != null){
            ss =  chefRepoI.findByEmail(p.getName()).get();
            http.setAttribute("theChef", ss);
            log.warn("session attr theChef in advice controller  " + http.getAttribute("theChef").toString());

        }
        model.addAttribute("theChef", ss);
    }
    //This is a mapping to get and display all the products of the logged in chef
    @GetMapping("/chefProduct")
    public String showChefProduct(@ModelAttribute("theChef") Chef chef, Model model){
        log.warn(chef.toString());
        model.addAttribute("products",productRepoI.findByChef(chef));
        model.addAttribute("count",orderRepoI.count());
        model.addAttribute("chefName",chef.getName());
        return "chefProduct";
    }

    //This is a mapping to create a new instance of a product
    @GetMapping("/addNewProduct")
    public String showAddNewProductPage(Model model, @ModelAttribute("theChef") Chef chef){
        model.addAttribute("product", new Product());
        model.addAttribute("count",orderRepoI.count());
        model.addAttribute("chefName",chef.getName());
        log.warn("chef name in add new product get mapping:"+chef.getName());
        return "addNewProduct";
    }
    //This is a mapping to add new product to the product model
    @PostMapping("/addNewProduct")
    public String addNewProductPage(@Valid @ModelAttribute("product") Product product,
                                    BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile file, Model model, Principal principal
                                    ) throws Exception{

        Chef chef=chefRepoI.findByEmail(principal.getName()).get();

        model.addAttribute("chefName",chef.getName());
       log.warn("Name of chef:"+chef.getName().toString());
        if(bindingResult.hasErrors()){
            log.error("Product has errors: " + product);
            log.error(bindingResult.getAllErrors().toString());
            return "addNewProduct";
        }
        product.setChef(chef);
        productRepoI.save(product);
        imageService.save(file,product);
        return "redirect:/chefProduct";
    }

    //This mapping is to show the update form
    @GetMapping("/updateForm/{id}")
    public String showUpdateForm(@PathVariable(name = "id") int id, Model model, @ModelAttribute("theChef") Chef chef) throws Exception {

        if(productRepoI.findById(id)!=null) {
        model.addAttribute("product",productRepoI.findById(id));
        model.addAttribute("count",orderRepoI.count());
        model.addAttribute("chefName",chef.getName());
        }else throw new Exception("Product does not exist");
        return "chefUpdateProduct";
    }

    //This is a mapping to get values of the chosen product which needs to be updated
    @GetMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("product") Product product,@ModelAttribute("theChef") Chef chef, Model model) throws Exception{
        log.warn("From Update product get mapping:"+product.toString());
        if(productRepoI.findById(product.getId()).isPresent()) {
        model.addAttribute("product",product);
        model.addAttribute("updateProduct",new Product());
        model.addAttribute("count",orderRepoI.count());
            model.addAttribute("chefName",chef.getName());
        }else throw new Exception("Product does not exist");
        return "chefUpdateProduct";
    }

    //This is a mapping to update a product for the chosen product
    @PostMapping("/updateProduct")
    public String createorupdateProduct(@ModelAttribute("updateProduct") Product product, Model model) throws Exception{

        if(productRepoI.findByName(product.getName()).isPresent()) {
        Product p = productService.createOrUpdate(product);
        model.addAttribute("updateProduct",p);
        }else throw new Exception("Product does not exist");
        return "redirect:/chefProduct";
    }

    //This is a mapping to delete a product with a given id
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@ModelAttribute("product") Product product, Model model) throws Exception {
        if(productRepoI.findById(product.getId()).isPresent()) {
            productRepoI.delete(product);
        }else throw new Exception("Product does not exist");
        return "redirect:/chefProduct";
    }

}
