package com.team4.dayoff.controller;

import com.team4.dayoff.entity.OrderGroup;
import com.team4.dayoff.repository.OrderGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderController
 */
@RestController
public class OrderController {


    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @PostMapping("/invoice")
    public OrderGroup aaa(@RequestBody OrderGroup orderGroup) {

        System.out.println(orderGroup);


        return orderGroupRepository.save(orderGroup);



    }
}