package org.perscholas.homemade.services;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.OrderDetails;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class ProductService {
    ProductRepoI productRepoI;

    @Autowired
    public ProductService(ProductRepoI productRepoI) {
        this.productRepoI = productRepoI;
    }

    public List<Product> findAll(){
        return productRepoI.findAll();
    }

    public Product createOrUpdate(Product product) {
            log.warn(product.toString());
            Product originalProduct = productRepoI.findByName(product.getName()).get();
            log.warn(originalProduct.toString());
            originalProduct.setCategory(product.getCategory());
            originalProduct.setPrice(product.getPrice());
            originalProduct.setDate(product.getDate());
            return productRepoI.save(originalProduct);

    }

}
