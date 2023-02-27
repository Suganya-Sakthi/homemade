package org.perscholas.homemade.dao;

import org.perscholas.homemade.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepoI extends JpaRepository<OrderDetails, Integer> {
}
