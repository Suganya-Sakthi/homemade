package org.perscholas.homemade.dao;

import jakarta.persistence.NamedAttributeNode;
import org.perscholas.homemade.models.OrderDetails;
import org.perscholas.homemade.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.hibernate.FetchMode.SELECT;
import static org.hibernate.sql.ast.Clause.FROM;

@Repository
public interface OrderRepoI extends JpaRepository<OrderDetails, Integer> {

     Optional<OrderDetails> findByProduct(Product product);

     @Query(value = "SELECT SUM(o.total_price) FROM order_details o;",nativeQuery = true)
     Double sumTotalPrice();
     @Query(value="SELECT * FROM order_details WHERE status='0'",nativeQuery = true)
     Optional<OrderDetails> findByStatusPending();
     @Query(value="SELECT * FROM order_details WHERE status='1'",nativeQuery = true)
     Optional<OrderDetails> findByStatusCompleted();

}
