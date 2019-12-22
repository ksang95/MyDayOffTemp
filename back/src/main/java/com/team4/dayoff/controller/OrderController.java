package com.team4.dayoff.controller;

import java.util.List;

import com.team4.dayoff.entity.OrderDetailView;
import com.team4.dayoff.entity.OrderGroup;
import com.team4.dayoff.repository.OrderDetailViewRepository;
import com.team4.dayoff.repository.OrderGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderController
 */
@RestController
public class OrderController {


    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private OrderDetailViewRepository orderDetailViewRepository;

    @PostMapping("/invoice")
    public OrderGroup aaa(@RequestBody OrderGroup orderGroup) {

        System.out.println(orderGroup);


        return orderGroupRepository.save(orderGroup);



    }

    @GetMapping("/orderDetail/{groupId}")
    public List<OrderDetailView> orderDetail(@PathVariable Integer groupId) {
        List<OrderDetailView> list = orderDetailViewRepository.findByGroupId(groupId);
        
        return list;
    }

    


}