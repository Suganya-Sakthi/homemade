package org.perscholas.homemade.dao;

import org.perscholas.homemade.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepoI extends JpaRepository<Customer, Integer> {
}
