package com.team4.dayoff.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team4.dayoff.entity.Code;
import com.team4.dayoff.entity.OrderGroup;
import com.team4.dayoff.repository.CodeRepository;
import com.team4.dayoff.repository.LoginHistoryRepository;
import com.team4.dayoff.repository.OrderGroupRepository;
import com.team4.dayoff.repository.RefundsRepository;
import com.team4.dayoff.repository.WithdrawHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminAnalysisController
 */
@RestController
public class AdminAnalysisController {
    @Autowired
    private WithdrawHistoryRepository withdrawHistoryRepository;
    
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private RefundsRepository refundsRepository;

    private final String[] MONTHS={"01","02","03","04","05","06","07","08","09","10","11","12"};
    private final String[][] SEX_AND_AGE={{"f","10"},{"m","10"},{"f","20"},{"m","20"},{"f","30"},{"m","30"},{"f","40"},{"m","40"},{"f","50"},{"m","50"},{"f","60"},{"m","60"}};

    private void fillMonth(List<String[]> target){
        int i=0;
        for(i=0; i<target.size(); i++){
            if(!target.get(i)[0].equals(MONTHS[i]))
            target.add(i, new String[]{MONTHS[i],"0","0","0"});
        }
        while(target.size()<MONTHS.length){
            target.add(i, new String[]{MONTHS[i++],"0","0","0"});
        }
    }

    private void fillSexAndAge(List<String[]> target){
        int i=0;
        for(i=0; i<target.size(); i++){
            if(!target.get(i)[1].equals(SEX_AND_AGE[i][1]))
            target.add(i,new String[]{SEX_AND_AGE[i][0],SEX_AND_AGE[i][1],"0","0"});
            else if(!target.get(i)[0].equals(SEX_AND_AGE[i][0]))
            target.add(i,new String[]{SEX_AND_AGE[i][0],SEX_AND_AGE[i][1],"0","0"});
        }
        while(target.size()<SEX_AND_AGE.length){
            target.add(i, new String[]{SEX_AND_AGE[i][0],SEX_AND_AGE[i++][1],"0","0"});
        }
    }

    @GetMapping("/usersAnalysis")
    public Map<String,Object> usersAnalysis(){
        Map<String,Object> map=new HashMap<String,Object>();
        String yearMonth=new SimpleDateFormat("yyyy-MM").format(new Date());
        String year=new SimpleDateFormat("yyyy").format(new Date());
        List<String[]> userSexAndAge=withdrawHistoryRepository.countUserSexAndAgeGroupByYearMonth(yearMonth);
        fillSexAndAge(userSexAndAge);
        map.put("userSexAndAge",userSexAndAge);
        List<String[]> userMonth=withdrawHistoryRepository.countUserMonthByYear(year);
        fillMonth(userMonth);
        map.put("userMonth",userMonth);
        List<String[]> userYear=withdrawHistoryRepository.countUserByYear();
        map.put("userYear",userYear);
        String[] allUsers=withdrawHistoryRepository.countAllUser().get(0);
        map.put("allUsers", allUsers);
        List<String[]> withdrawReasons=withdrawHistoryRepository.countReasonByYear(year);
        map.put("withdrawReasons",withdrawReasons);
        List<String> yearsOfUsers=withdrawHistoryRepository.findYearOfUsers();
        map.put("yearsOfUsers",yearsOfUsers);
        List<String> yearMonthsOfUsers=withdrawHistoryRepository.findYearMonthOfUsers();
        map.put("yearMonthsOfUsers",yearMonthsOfUsers);

        List<Code> allWithdrawReasons=codeRepository.findByCodeLikeOrderByCode("02%");
        map.put("allWithdrawReasons",allWithdrawReasons);
        List<String> yearsOfWithdraws=withdrawHistoryRepository.findYearOfWithdraws();
        map.put("yearsOfWithdraws",yearsOfWithdraws);

        List<String[]> loginSexAndAge=loginHistoryRepository.countLoginSexAndAgeGroupByYearMonth(yearMonth);
        fillSexAndAge(loginSexAndAge);
        map.put("loginSexAndAge",loginSexAndAge);
        List<String[]> loginMonth=loginHistoryRepository.countLoginMonthByYear(year);
        fillMonth(loginMonth);
        map.put("loginMonth",loginMonth);
        List<String[]> loginYear=loginHistoryRepository.countLoginYear();
        map.put("loginYear",loginYear);
        List<String> yearsOfLogin=loginHistoryRepository.findYear();
        map.put("yearsOfLogin",yearsOfLogin);
        List<String> yearMonthsOfLogin=loginHistoryRepository.findYearMonth();
        map.put("yearMonthsOfLogin",yearMonthsOfLogin);
        
        return map;
    }

    @GetMapping("/usersAnalysis/user/sexAndAge/{yearMonth}")
    public Map<String,Object> countUserSexAndAgeGroupByYearMonth(@PathVariable String yearMonth){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> userSexAndAge=withdrawHistoryRepository.countUserSexAndAgeGroupByYearMonth(yearMonth);
        fillSexAndAge(userSexAndAge);
        map.put("userSexAndAge",userSexAndAge);

        return map;
    }

    @GetMapping("/usersAnalysis/user/month/{year}")
    public Map<String,Object> countUserMonthByYear(@PathVariable String year){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> userMonth=withdrawHistoryRepository.countUserMonthByYear(year);
        fillMonth(userMonth);
        map.put("userMonth",userMonth);
        
        return map;
    }

    @GetMapping("/usersAnalysis/user/withdrawReasons/{year}")
    public Map<String,Object> countWithdrawReasonByYear(@PathVariable String year){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> withdrawReasons=withdrawHistoryRepository.countReasonByYear(year);
        map.put("withdrawReasons",withdrawReasons);

        return map;
    }

    @GetMapping("/usersAnalysis/login/sexAndAge/{yearMonth}")
    public Map<String,Object> countLoginSexAndAgeGroupByYearMonth(@PathVariable String yearMonth){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> loginSexAndAge=loginHistoryRepository.countLoginSexAndAgeGroupByYearMonth(yearMonth);
        fillSexAndAge(loginSexAndAge);
        map.put("loginSexAndAge",loginSexAndAge);

        return map;
    }

    @GetMapping("/usersAnalysis/login/month/{year}")
    public Map<String,Object> countLoginMonthByYear(@PathVariable String year){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> loginMonth=loginHistoryRepository.countLoginMonthByYear(year);
        fillMonth(loginMonth);
        map.put("loginMonth",loginMonth);
        
        return map;
    }

    @GetMapping("/ordersAnalysis")
    public Map<String,Object> ordersAnalysis(){
        Map<String,Object> map=new HashMap<String,Object>();
        String yearMonth=new SimpleDateFormat("yyyy-MM").format(new Date());
        String year=new SimpleDateFormat("yyyy").format(new Date());

        List<String[]> orderSexAndAge=orderGroupRepository.countOrderSexAndAgeGroupByYearMonth(yearMonth);
        fillSexAndAge(orderSexAndAge);
        map.put("orderSexAndAge",orderSexAndAge);
        List<String[]> orderMonth=orderGroupRepository.countOrderMonthByYear(year);
        fillMonth(orderMonth);
        map.put("orderMonth",orderMonth);
        List<String[]> orderYear=orderGroupRepository.countOrderByYear();
        map.put("orderYear",orderYear);
        List<String[]> refundReasons=refundsRepository.countReasonByYear(year);
        map.put("refundReasons",refundReasons);
        List<String> yearsOfOrders=orderGroupRepository.findYearOfOrders();
        map.put("yearsOfOrders",yearsOfOrders);
        List<String> yearMonthsOfOrders=orderGroupRepository.findYearMonthOfOrders();
        map.put("yearMonthsOfOrders",yearMonthsOfOrders);

        List<Code> allRefundReasons=codeRepository.findByCodeLikeOrderByCode("01%");
        map.put("allRefundReasons",allRefundReasons);
        List<String> yearsOfRefunds=refundsRepository.findYearOfRefunds();
        map.put("yearsOfRefunds",yearsOfRefunds);
        
        return map;
    }

    @GetMapping("/ordersAnalysis/order/sexAndAge/{yearMonth}")
    public Map<String,Object> countOrderSexAndAgeGroupByYearMonth(@PathVariable String yearMonth){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> orderSexAndAge=orderGroupRepository.countOrderSexAndAgeGroupByYearMonth(yearMonth);
        fillSexAndAge(orderSexAndAge);
        map.put("orderSexAndAge",orderSexAndAge);

        return map;
    }

    @GetMapping("/ordersAnalysis/order/month/{year}")
    public Map<String,Object> countOrderMonthByYear(@PathVariable String year){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> orderMonth=orderGroupRepository.countOrderMonthByYear(year);
        fillMonth(orderMonth);
        map.put("orderMonth",orderMonth);
        
        return map;
    }

    @GetMapping("/ordersAnalysis/order/refundReasons/{year}")
    public Map<String,Object> countRefundReasonByYear(@PathVariable String year){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String[]> refundReasons=refundsRepository.countReasonByYear(year);
        map.put("refundReasons",refundReasons);
        
        return map;
    }

}