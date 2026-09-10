package com.cfs.Ecomm.Repo;


import com.cfs.Ecomm.Model.Orders;
import com.cfs.Ecomm.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Long> {

    @Query("SELECT o FROM Orders o JOIN FETCH o.user")
    List<Orders> findAllordersWithUsers();

    List<Orders> findByUser(User user);

}
