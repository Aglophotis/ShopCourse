package ru.aglophotis.mirea.microservice.balance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.balance.entities.Balance;
import ru.aglophotis.mirea.microservice.balance.services.BalanceService;
import ru.aglophotis.mirea.microservice.balance.utils.TokenUtils;

import java.util.List;

@Controller
public class BalanceController {

    @Autowired
    private BalanceService balanceService;
    private TokenUtils tokenUtils;

    public BalanceController() {
        tokenUtils = new TokenUtils();
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    @ResponseBody
    public List<Balance> getBalance(@RequestHeader(value = "Authorization", required = true) String token) {
//        if (tokenUtils.validateToken(token)) {
            return balanceService.getBalance(tokenUtils.getPayload(token).getSub());
//        } else {
//            return null;
//        }
    }

    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    @ResponseBody
    public String createWallet(@RequestBody Integer id) {
        return balanceService.createWallet(id);
    }

    @RequestMapping(value = "/currency/{id}/balance/{value}", method = RequestMethod.PUT)
    @ResponseBody
    public String putBalance(@PathVariable("id") int id,
                             @PathVariable("value") double value,
                             @RequestHeader(value = "Authorization", required = true) String token) {
//        if (tokenUtils.validateToken(token)) {
            return balanceService.increaseBalance(id, value, tokenUtils.getPayload(token).getSub());
//        } else {
//            return "Please log in again";
//        }
    }

    @RequestMapping(value = "/currency/{id}/balance/{value}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteBalance(@PathVariable("id") int id,
                                @PathVariable("value") double value,
                                @RequestHeader(value = "Authorization", required = true) String token) {
//        if (tokenUtils.validateToken(token)) {
            return balanceService.decreaseBalance(id, value, tokenUtils.getPayload(token).getSub());
//        } else {
//            return "Please log in again";
//        }
    }

    @RequestMapping(value = "/currency/{id}/balance/{value}", method = RequestMethod.POST)
    @ResponseBody
    public String setBalance(@PathVariable("id") int id,
                             @PathVariable("value") double value,
                             @RequestHeader(value = "Authorization", required = true) String token) {
//        if (tokenUtils.validateToken(token)) {
            return balanceService.setBalance(id, value, tokenUtils.getPayload(token).getSub());
//        } else {
//            return "Please log in again";
//        }
    }
}