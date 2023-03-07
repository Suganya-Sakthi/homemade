package org.perscholas.homemade.dao;

import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepoI extends JpaRepository<Product,Integer> {
    List<Product> findByChef(Chef chef);

    Optional<Product> findByName(String name);
    Product findById(int id);
}


