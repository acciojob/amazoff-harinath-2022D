package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;
@Repository
public class OrderRepository {
    HashMap<String,Order> orders;
    HashMap<String,DeliveryPartner> partners;
    HashMap<String, List<String>> partnerOrderPair;
    HashSet<String> NonAssignedOrders;

    public OrderRepository() {
        orders =new HashMap<>();
        partners =new HashMap<>();
        partnerOrderPair =new HashMap<>();
        NonAssignedOrders=new HashSet<>();

    }

    public void addOrder(Order order) {
        orders.put(order.getId(),order);
        NonAssignedOrders.add(order.getId());
    }

    public void addPartner(String partnerId) {
        DeliveryPartner d1=new DeliveryPartner(partnerId);
        partners.put(partnerId,d1);
    }


    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> list= partnerOrderPair.getOrDefault(partnerId,new ArrayList<>());
        list.add(orderId);
        partnerOrderPair.put(partnerId,list);
        partners.get(partnerId).setNumberOfOrders(partners.get(partnerId).getNumberOfOrders()+1);

        NonAssignedOrders.remove(orderId);
    }

    public Order getOrderById(String orderId) {
        if(!orders.containsKey(orderId)){
            return null;
        }
        return orders.get(orderId);
    }


    public DeliveryPartner getPartnerById(String partnerId) {
        return partners.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {

        return partnerOrderPair.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> list= partnerOrderPair.getOrDefault(partnerId,new ArrayList<>());
        return list;
    }

    public List<String> getAllOrders() {
        List<String> list=new ArrayList<>();
        for(String s: orders.keySet()){
            list.add(s);
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        return NonAssignedOrders.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count=0;
        //converting given string time to integer
        String arr[]=time.split(":"); //12:45
        int hr=Integer.parseInt(arr[0]);
        int min=Integer.parseInt(arr[1]);

        int total=(hr*60+min);

        List<String> list= partnerOrderPair.getOrDefault(partnerId,new ArrayList<>());
        if(list.size()==0)return 0; //no order assigned to partnerId

        for(String s: list){
            Order currentOrder= orders.get(s);
            if(currentOrder.getDeliveryTime()>total){
                count++;
            }
        }

        return count;

    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        //return in HH:MM format
        String str="00:00";
        int max=0;

        List<String>list= partnerOrderPair.getOrDefault(partnerId,new ArrayList<>());
        if(list.size()==0)return str;
        for(String s: list){
            Order currentOrder= orders.get(s);
            max=Math.max(max,currentOrder.getDeliveryTime());
        }
        //convert int to string (140-> 02:20)
        int hr=max/60;
        int min=max%60;

        if(hr<10){
            str="0"+hr+":";
        }else{
            str=hr+":";
        }

        if(min<10){
            str+="0"+min;
        }
        else{
            str+=min;
        }
        return str;


    }

    public void deletePartnerById(String partnerId) {
        if(!partnerOrderPair.isEmpty()){
            NonAssignedOrders.addAll(partnerOrderPair.get(partnerId));
        }
//        if(!partnerDB.containsKey(partnerId)){
//            return;
//        }
        //removing form partnerDB
        partners.remove(partnerId);

//        List<String>list=pairDB.getOrDefault(partnerId,new ArrayList<>());
//        if(list.size()==0)return;
//        isOrderAssigned.addAll(pairDB.get(partnerId));

        //remove form the pairDB
        partnerOrderPair.remove(partnerId);

    }

    public void deleteOrderById(String orderId) {
        //Delete an order and the corresponding partner should be unassigned
        if(orders.containsKey(orderId)){
            if(NonAssignedOrders.contains(orderId)){
                NonAssignedOrders.remove(orderId);
            }
            else{

                for(String str : partnerOrderPair.keySet()){
                    List<String> list= partnerOrderPair.get(str);
                    if(list.contains(orderId)){
                        list.remove(orderId);
                    }
                }
            }
        }


    }

    public boolean checkOrderId(String orderId) {
        return orders.containsKey(orderId);
    }

    public boolean checkPartnerId(String partnerId) {
        return partners.containsKey(partnerId);
    }
}