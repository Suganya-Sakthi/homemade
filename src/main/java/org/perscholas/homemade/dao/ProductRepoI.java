package org.perscholas.homemade.dao;

import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepoI extends JpaRepository<Product,Integer> {
}


