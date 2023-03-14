package org.perscholas.homemade.controllers;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.perscholas.homemade.dao.*;
import org.perscholas.homemade.models.Customer;
import org.perscholas.homemade.models.EmailDetails;
import org.perscholas.homemade.models.OrderDetails;
import org.perscholas.homemade.models.Product;
import org.perscholas.homemade.services.EmailService;
import org.perscholas.homemade.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@Slf4j
public class HomeController {
    ChefRepoI chefRepoI;
    OrderRepoI orderRepoI;
    CustomerRepoI customerRepoI;
    ProductRepoI productRepoI;
    OrderService orderService;

    EmailRepoI emailRepoI;
    EmailService emailService;

    @Autowired
    public HomeController(EmailRepoI emailRepoI, EmailService emailService, ChefRepoI chefRepoI, OrderRepoI orderRepoI, CustomerRepoI customerRepoI, ProductRepoI productRepoI, OrderService orderService) {
        this.emailRepoI = emailRepoI;
        this.emailService = emailService;
        this.chefRepoI = chefRepoI;
        this.orderRepoI = orderRepoI;
        this.customerRepoI = customerRepoI;
        this.productRepoI = productRepoI;
        this.orderService=orderService;

    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable(name = "id") int id, Model model) throws Exception{
        if(productRepoI.findById(id)!=null) {
        orderService.addToCart(id);
        model.addAttribute("count",orderRepoI.count());
        }else throw new Exception("Product does not exist");
        return "redirect:/index";
    }


   @GetMapping(value = {"/", "/index"})
    public String showIndex(Model model) {
        log.warn("index main");
        model.addAttribute("products", productRepoI.findAll());
        model.addAttribute("count",orderRepoI.count());
        return "index";
    }

    @GetMapping("/indexByPrice")
    public String showIndexPrice(Model model) {
        log.warn("index by price");
        model.addAttribute("products", productRepoI.findAllByPrice());
        model.addAttribute("count",orderRepoI.count());
        return "index";
    }
    @GetMapping("/indexByDate")
    public String showIndexDate(Model model) {
        model.addAttribute("products", productRepoI.findAllByDate());
        model.addAttribute("count",orderRepoI.count());
        return "index";
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
    public String updateOrderAdd(Model model, @PathVariable(name = "id") int id) throws Exception{
        log.warn("ID:"+id);
        if(productRepoI.findById(id)!=null) {
        orderService.addQuantity(id);
        }else throw new Exception("Product does not exist");
        return "redirect:/checkout";
    }
    @GetMapping("/updateOrderDelete/{id}")
    public String updateOrderDelete(Model model, @PathVariable(name = "id") int id) throws Exception{
        log.warn("ID:"+id);
        if(productRepoI.findById(id)!=null) {
        orderService.removeQuantity(id);
        }else throw new Exception("Product does not exist");
        return "redirect:/checkout";
    }
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(Model model, @PathVariable(name = "id") int id) throws Exception{
        log.warn("ID:"+id);
        if(productRepoI.findById(id)!=null) {
        orderService.deleteOrder(id);
    }else throw new Exception("Product does not exist");
        return "redirect:/checkout";
    }

    @GetMapping("/success")
    public String showSuccessPage(){
        orderRepoI.deleteAll();
        return "success";
    }
    @GetMapping("/aboutUs")
    public String showAboutUsPage(){

        return "aboutUs";
    }
    @GetMapping("/confirm")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new EmailDetails());
        model.addAttribute("count",orderRepoI.count());
        return "customer";
    }
    @PostMapping("/confirm")
    public String confirmOrder(@ModelAttribute("customer") EmailDetails emailDetails){
        emailDetails.setSubject("Your Order Details");
        List<OrderDetails> od = orderRepoI.findAll();
        List<Integer> quantity = new ArrayList<>();
        List<Double> totalPrice =new ArrayList<>();
        List<Product> p = new ArrayList<>();
        for (OrderDetails o:od){
            p.add(o.getProduct());
        }
        List<String> productName=new ArrayList<>();
        for (Product pd:p){
            productName.add(pd.getName());
        }

        for (OrderDetails o:od){
            quantity.add(o.getQuantity());
            totalPrice.add(o.getTotalPrice());
        }

        emailDetails.setMsgBody("Dear customer,\n\n Thank you for shopping with us.\n\n" +
                " Product Name\t Quantity Ordered \t Total Price\n"
                + productName.toString()
                +"\t"+quantity.toString()
                +"\t"+totalPrice.toString()+"\n\n\n Thank you, \nHomemade");
        emailRepoI.save(emailDetails);
        emailService.sendSimpleMail(emailDetails);
        return "redirect:/success";
    }


}
