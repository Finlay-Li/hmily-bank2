package com.dodou.scaffold.controller;

import com.dodou.scaffold.dao.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Finlay
 * @ClassName: Bank2Controller
 * @Description:
 * @date: 2019-11-11 4:56 PM
 */
@RestController
public class Bank2Controller {

    @Autowired
    AccountInfoService accountInfoService;

    @GetMapping("/transfer")
    public Boolean transfer(@RequestParam("amount") Double amount) {
        this.accountInfoService.updateAccountBalance("2", amount);
        return true;
    }
}
