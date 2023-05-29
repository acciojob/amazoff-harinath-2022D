package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;


    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }


    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }


    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if(order == null){
            throw new RuntimeException("Order Id Does Not Exists");
        }
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new RuntimeException("Partner id does not Exists");
        }
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new RuntimeException("Partner id does not Exists");
        }
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new RuntimeException("Partner id does not Exists");
        }
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new RuntimeException("Partner id does not Exists");
        }
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new RuntimeException("Partner id does not Exists");
        }
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        if(!orderRepository.checkPartnerId(partnerId)){
            throw new RuntimeException("Partner id does not Exists");
        }
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        if(!orderRepository.checkOrderId(orderId)){
            throw new RuntimeException("Order Id Does Not Exists");
        }
        orderRepository.deleteOrderById(orderId);

    }


}
