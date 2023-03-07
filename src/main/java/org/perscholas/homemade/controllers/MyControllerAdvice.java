package org.perscholas.homemade.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.models.Chef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

    @Autowired
    ChefRepoI chefRepoI;

    @ModelAttribute
    public void theChef(Model model, HttpServletRequest request, HttpSession http){
        Principal p = request.getUserPrincipal();
        Chef chef = null;
        if(p != null){
            chef =  chefRepoI.findByEmail(p.getName()).get();
            http.setAttribute("theChef", chef);
            log.warn("session attr theChef in advice controller  " + http.getAttribute("theChef").toString());

        }
        model.addAttribute("theChef", chef);
    }

}
