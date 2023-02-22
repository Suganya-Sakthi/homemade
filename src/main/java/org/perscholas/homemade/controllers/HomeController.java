package org.perscholas.homemade.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.CustomerRepoI;
import org.perscholas.homemade.dao.OrderRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Customer;
import org.perscholas.homemade.models.Order;
import org.perscholas.homemade.models.Product;
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
    OrderRepoI orderRepoI;
    CustomerRepoI customerRepoI;
    ProductRepoI productRepoI;

    @Autowired
    public HomeController(ChefRepoI chefRepoI, OrderRepoI orderRepoI, CustomerRepoI customerRepoI, ProductRepoI productRepoI) {
        this.chefRepoI = chefRepoI;
        this.orderRepoI = orderRepoI;
        this.customerRepoI=customerRepoI;
        this.productRepoI=productRepoI;

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
        return "chefRegistration";
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

    @GetMapping("/checkout")
    public String showCheckout(){
        return "checkout";
    }
    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("order") Order order, @ModelAttribute("customer") Customer customer){
        orderRepoI.save(order);
        customerRepoI.save(customer);
        return  "redirect:/success";
    }

    @GetMapping("/chefLogin")
    public String showChefLogin() {
        return "chefLogin";
    }

    @PostMapping("/product")
    public String product(@ModelAttribute("product") Product product) {
        productRepoI.save(product);
        return "redirect:/success";
    }

}
