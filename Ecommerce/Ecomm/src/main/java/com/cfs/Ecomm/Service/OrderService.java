package com.cfs.Ecomm.Service;

import com.cfs.Ecomm.DTO.OrderDTO;
import com.cfs.Ecomm.DTO.OrderItemDto;
import com.cfs.Ecomm.Model.OrderItem;
import com.cfs.Ecomm.Model.Orders;
import com.cfs.Ecomm.Model.Product;
import com.cfs.Ecomm.Model.User;
import com.cfs.Ecomm.Repo.OrderRepo;
import com.cfs.Ecomm.Repo.ProductRepo;
import com.cfs.Ecomm.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    public OrderDTO placeOrder(Long userId, Map<Long,Integer> productQuantities,double totalAmount){
        User user =userRepo.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found......."));

        Orders order= new Orders();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("Pending");
        order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems=new ArrayList<>();
        List<OrderItemDto> orderItemDtos=new ArrayList<>();

        for(Map.Entry<Long , Integer> entry:productQuantities.entrySet()){
            Product product=productRepo.findById(entry.getKey())
                    .orElseThrow(()->new RuntimeException("Product Not Found....."));

            OrderItem orderItem=new OrderItem();
            orderItem.setOrders(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);

            orderItemDtos.add(new OrderItemDto(product.getName(),product.getPrice(),entry.getValue()));

        }
        order.setOrderItem(orderItems);
        Orders saveOrder=orderRepo.save(order);
        return new OrderDTO(saveOrder.getId(),saveOrder.getTotalAmount(),saveOrder.getStatus(),saveOrder.getOrderDate(),orderItemDtos);
    }

    public List<OrderDTO> getAllOrders(){
        List<Orders> orders=orderRepo.findAllordersWithUsers();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OrderDTO convertToDTO(Orders orders) {
        List<OrderItemDto> orderItems=orders.getOrderItem().stream()
                .map(item->new OrderItemDto(
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity())).collect(Collectors.toList());
                return new OrderDTO(
                        orders.getId(),
                        orders.getTotalAmount(),
                        orders.getStatus(),
                        orders.getOrderDate(),
                        orders.getUser()!=null ? orders.getUser().getName():"UNKNOWN",
                        orders.getUser()!=null ? orders.getUser().getEmail():"UNKNOWN",
                        orderItems
                );
    }
    public List<OrderDTO> getOrderByUser(Long userId){
        Optional<User> userOp=userRepo.findById(userId);
        if(userOp.isEmpty()){
            throw new RuntimeException("user not found");
        }
        User user=userOp.get();
        List<Orders> orderList=orderRepo.findByUser(user);
        return orderList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


}
