package org.perscholas.homemade.services;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.dao.OrderRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.OrderDetails;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class OrderService {
   OrderRepoI orderRepoI;
   ProductRepoI productRepoI;

   @Autowired
    public OrderService(OrderRepoI orderRepoI, ProductRepoI productRepoI) {
        this.orderRepoI = orderRepoI;
        this.productRepoI = productRepoI;
    }
    public void addToCart(int id){
        Product p = productRepoI.findById(id);
        if(orderRepoI.findByProduct(p).isPresent()){
            OrderDetails o =orderRepoI.findByProduct(p).get();
            int quantity = o.getQuantity()+1;
            double tp=quantity*o.getTotalPrice();
            o.setQuantity(quantity);
            o.setTotalPrice(tp);
            orderRepoI.save(o);
        }else {
            double totalprice = p.getPrice();
            OrderDetails od = new OrderDetails(1, totalprice, OrderDetails.OrderStatus.PENDING, p);
            orderRepoI.save(od);
        }
    }
    public void addQuantity(int id){
        OrderDetails original = orderRepoI.findById(id).get();
        original.setQuantity(original.getQuantity()+1);
        original.setTotalPrice(original.getTotalPrice()+original.getProduct().getPrice());
        orderRepoI.save(original);
    }
    public void removeQuantity(int id){
        OrderDetails original = orderRepoI.findById(id).get();
        if(original.getQuantity()==1) {
            log.warn("In remove quantity Id :"+id);
            orderRepoI.deleteById(id);
        }else {
            original.setQuantity(original.getQuantity() - 1);
            original.setTotalPrice(original.getTotalPrice() - original.getProduct().getPrice());
            orderRepoI.save(original);

        }
    }
    public void deleteOrder(int id){
        log.warn("In delete order method inside delete order service Id:"+id);
       orderRepoI.deleteById(id);
    }



}
