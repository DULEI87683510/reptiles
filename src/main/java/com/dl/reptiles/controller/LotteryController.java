package com.dl.reptiles.controller;

import com.dl.reptiles.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName LotteryController
 *@Description TODO
 *@Author DL
 *@Date 2019/10/9 13:42    
 *@Version 1.0
 */
@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;
    @GetMapping("/getWinningNumber")
    public String getWinningNumber(){

        return lotteryService.getWinningNumber();
    }
}