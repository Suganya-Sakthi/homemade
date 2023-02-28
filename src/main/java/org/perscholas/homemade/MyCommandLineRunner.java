package org.perscholas.homemade;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.*;
import org.perscholas.homemade.models.AuthGroup;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import static java.util.Calendar.AM;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {

    ChefRepoI chefRepoI;
    CustomerRepoI customerRepoI;
    OrderRepoI orderRepoI;
    ProductRepoI productRepoI;

    AuthGroupRepoI authGroupRepoI;
    @Autowired
    public MyCommandLineRunner(ChefRepoI chefRepoI, CustomerRepoI customerRepoI, OrderRepoI orderRepoI, ProductRepoI productRepoI, AuthGroupRepoI authGroupRepoI) {
        this.chefRepoI = chefRepoI;
        this.customerRepoI = customerRepoI;
        this.orderRepoI = orderRepoI;
        this.productRepoI = productRepoI;
        this.authGroupRepoI=authGroupRepoI;
    }
    @PostConstruct
    void created(){
        log.warn("=============== My CommandLineRunner Got Created ===============");
    }

    @Override
    public void run(String... args) throws Exception {
        Chef chef = new Chef("James","james@gmail.com","password","555-555-5555","434 Oak Ave","Troy", "Michigan",12345);
        Chef c1 = new Chef("John Smith","johnsmith@example.com", "MyP@ssw0rd123","555-555-5555","123 Main St","Dallas","Texas",45678);
        Chef c2 = new Chef("Sarah Johnson", "sjohnson@example.com","Secret123!","555-123-4567","456 Oak Ave","Troy", "Michigan",23467);
        Chef c3 = new Chef(" Michael Chen","mchen@example.com","Pa$$w0rd321","555-867-5309","789 Elm St","Dallas", "Texas",46788);
        Chef c4 = new Chef("Emily Lee"," elee@example.com","Qwerty123!","555-444-5555","321 Maple Rd","Troy", "Michigan",24677);

        chefRepoI.saveAndFlush(chef);
        chefRepoI.saveAndFlush(c1);
        chefRepoI.saveAndFlush(c2);
        chefRepoI.saveAndFlush(c3);
        chefRepoI.saveAndFlush(c4);

        Product p1=new Product("Pasta","Italian",10.00,LocalDate.of(2023,03,23),c1);
        Product p2=new Product("Chicken Burger","American",20.00, LocalDate.of(2023,02,23),c2);
        Product p3=new Product("Chicken Sandwich","American",15.00,LocalDate.of(2023,02,26),c3);
        Product p4=new Product("Samosa","Indian",10.00,LocalDate.of(2023,02,23),c4);
        Product p5=new Product("Salad","American",10.00,LocalDate.of(2023,02,27),c1);

        productRepoI.saveAndFlush(p1);
        productRepoI.saveAndFlush(p2);
        productRepoI.saveAndFlush(p3);
        productRepoI.saveAndFlush(p4);
        productRepoI.saveAndFlush(p5);

        AuthGroup authGroup1 = new AuthGroup("james@gmail.com", "ROLE_CHEF");

        authGroupRepoI.save(authGroup1);





    }
}
