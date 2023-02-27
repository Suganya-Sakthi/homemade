package org.perscholas.homemade.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.CustomerRepoI;
import org.perscholas.homemade.dao.OrderRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Customer;
import org.perscholas.homemade.models.OrderDetails;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class HomeController {
    ChefRepoI chefRepoI;
    OrderRepoI orderRepoI;
    CustomerRepoI customerRepoI;
    ProductRepoI productRepoI;

    @Autowired
    public HomeController(ChefRepoI chefRepoI, OrderRepoI orderRepoI, CustomerRepoI customerRepoI, ProductRepoI productRepoI) {
        this.chefRepoI = chefRepoI;
        this.orderRepoI = orderRepoI;
        this.customerRepoI = customerRepoI;
        this.productRepoI = productRepoI;

    }

    @GetMapping("/index")
    public String showIndex(Model model) {
        model.addAttribute("products", productRepoI.findAll());
        return "index";
    }



    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }


    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("customer") Customer customer) {

        customerRepoI.save(customer);
        return "redirect:/success";
    }


    @PostMapping("/addOrder")
    public String addOrder(@ModelAttribute("order") OrderDetails order) {
        orderRepoI.save(order);
        return "index";
    }

    @GetMapping("/checkout")
    public String showCheckout() {
        return "checkout";
    }

}
