package org.perscholas.homemade.controllers;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.CustomerRepoI;
import org.perscholas.homemade.dao.OrderRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@SessionAttributes("order")
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

    @ModelAttribute("order")
    public OrderDetails shoppingCart() {
        return new OrderDetails();
    }
    @GetMapping("/addToCart")
    public String addToCart(final Model model, @ModelAttribute("order") OrderDetails order, final Product product){

        if (order != null) {
            //add product to the shopping cart list
            order.addProduct(product);
            model.addAttribute("order", order);
        } else {
            OrderDetails order1 = new OrderDetails();
            order1.addProduct(product);
            model.addAttribute("order", order1);
        }

        return "redirect:/index";
    }


   @GetMapping(value = {"/", "index"})
    public ModelAndView showIndex(Model model, @ModelAttribute("order") OrderDetails order) {
        model.addAttribute("products", productRepoI.findAll());
        if(order !=null){
            model.addAttribute("order",order);
        }else{
            model.addAttribute("order", new OrderDetails());
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.getModel();
        return mv;
    }


    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }

    @PostMapping("/checkout")
    public String checkout() {
        return "redirect:/success";
    }


    @PostMapping("/addOrder")
    public String addOrder(@ModelAttribute("order") OrderDetails order) {
        orderRepoI.save(order);
        return "index";
    }

    @GetMapping("/checkout")
    public String showCheckout(@SessionAttribute("order") OrderDetails order, final Model model) {
        model.addAttribute("products",order.getProducts());
        log.warn(order.getProducts().toString());
        return "checkout";
    }

}
