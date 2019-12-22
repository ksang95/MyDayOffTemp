package com.team4.dayoff.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team4.dayoff.entity.Code;
import com.team4.dayoff.entity.OrderView;
import com.team4.dayoff.entity.Orders;
import com.team4.dayoff.entity.Refunds;
import com.team4.dayoff.repository.CodeRepository;
import com.team4.dayoff.repository.OrderViewRepository;
import com.team4.dayoff.repository.OrdersRepository;
import com.team4.dayoff.repository.RefundsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * RefundsController
 */
@RestController
public class RefundsController {

    @Autowired
    OrderViewRepository orderViewRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    RefundsRepository refundsRepository;

    @Autowired
    CodeRepository codeRepository;


    @GetMapping("/refundRequest")
    public List<Code> refundRequest() {
        List<Code> list = codeRepository.findByCodeLikeOrderByCode("01%");

        return list;
    }

    @PostMapping("/refundRequestProcess")
    public void refundRequestProcess(@RequestBody Refunds refunds) {
        Orders orders=ordersRepository.findById(refunds.getId()).get();
        orders.setCode(new Code("0004","환불신청완료"));
        refunds.setOrders(orders);
        refunds.setId(null);
        Refunds savedRefunds=refundsRepository.save(refunds);
    }
}