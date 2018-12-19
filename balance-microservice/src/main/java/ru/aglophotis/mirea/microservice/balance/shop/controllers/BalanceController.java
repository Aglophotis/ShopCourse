package ru.aglophotis.mirea.microservice.balance.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.balance.shop.entities.Balance;
import ru.aglophotis.mirea.microservice.balance.shop.services.BalanceService;

import java.util.List;

@Controller
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    @ResponseBody
    public List<Balance> getBalance(@RequestHeader(value = "Authorization", required = true) String token) {
        String checkResult = balanceService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return balanceService.getBalance(Integer.parseInt(checkResult));
        } else {
            return null;
        }
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
        String checkResult = balanceService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return balanceService.increaseBalance(id, value, Integer.parseInt(checkResult));
        } else {
            return "Please log in again";
        }
    }

    @RequestMapping(value = "/currency/{id}/balance/{value}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteBalance(@PathVariable("id") int id,
                                @PathVariable("value") double value,
                                @RequestHeader(value = "Authorization", required = true) String token) {
        String checkResult = balanceService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return balanceService.decreaseBalance(id, value, Integer.parseInt(checkResult));
        } else {
            return "Please log in again";
        }
    }

    @RequestMapping(value = "/currency/{id}/balance/{value}", method = RequestMethod.POST)
    @ResponseBody
    public String setBalance(@PathVariable("id") int id,
                             @PathVariable("value") double value,
                             @RequestHeader(value = "Authorization", required = true) String token) {
        String checkResult = balanceService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return balanceService.setBalance(id, value, Integer.parseInt(checkResult));
        } else {
            return "Please log in again";
        }
    }
}