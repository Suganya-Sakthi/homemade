package org.perscholas.homemade.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.MathHelper;
import org.perscholas.homemade.dao.*;
import org.perscholas.homemade.models.*;
import org.perscholas.homemade.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
public class HomeController {
    ChefRepoI chefRepoI;
    OrderRepoI orderRepoI;
    CustomerRepoI customerRepoI;
    ProductRepoI productRepoI;
    OrderService orderService;

    @Autowired
    public HomeController(ChefRepoI chefRepoI, OrderRepoI orderRepoI, CustomerRepoI customerRepoI, ProductRepoI productRepoI, OrderService orderService) {
        this.chefRepoI = chefRepoI;
        this.orderRepoI = orderRepoI;
        this.customerRepoI = customerRepoI;
        this.productRepoI = productRepoI;
        this.orderService=orderService;

    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable(name = "id") int id){
        orderService.addToCart(id);
        return "redirect:/index";
    }


   @GetMapping(value = {"/", "index"})
    public String showIndex(Model model) {
        model.addAttribute("products", productRepoI.findAll());
       model.addAttribute("count",orderRepoI.count());
        return "index";
    }


    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }

    @PostMapping("/checkout")
    public String checkout() {
        return "redirect:/success";
    }


    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        model.addAttribute("orders", orderRepoI.findAll());
        model.addAttribute("count",orderRepoI.count());
        Double totalPrice=0.0;
        if( orderRepoI.sumTotalPrice()!=null) {
           totalPrice = orderRepoI.sumTotalPrice();
        }
        model.addAttribute("subtotal",totalPrice);
        return "checkout";
    }

    @GetMapping("/updateOrderAdd/{id}")
    public String updateOrderAdd(Model model, @PathVariable(name = "id") int id){
        orderService.addQuantity(id);
        return "redirect:/checkout";
    }
    @GetMapping("/updateOrderDelete/{id}")
    public String updateOrderDelete(Model model, @PathVariable(name = "id") int id){
        orderService.removeQuantity(id);
        return "redirect:/checkout";
    }
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(Model model, @PathVariable(name = "id") int id){
        orderService.deleteOrder(id);
        return "redirect:/checkout";
    }

}
