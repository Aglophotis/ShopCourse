package ru.aglophotis.mirea.microservice.cart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.cart.entities.CartItem;
import ru.aglophotis.mirea.microservice.cart.services.CartService;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    @ResponseBody
    public List<CartItem> getCart(@RequestHeader(value = "Authorization", required = true) String token) {
        String checkResult = cartService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return cartService.getCart(Integer.parseInt(checkResult));
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteItem(@PathVariable("id") int id,
                             @RequestHeader(value = "Authorization", required = true) String token) {
        String checkResult = cartService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return cartService.deleteItemFromCart(id, Integer.parseInt(checkResult));
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/cart/item/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String putItem(@PathVariable("id") int id,
                          @RequestHeader(value = "Authorization", required = true) String token) {
        String checkResult = cartService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return cartService.putItemToCart(id, Integer.parseInt(checkResult));
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public String pay(@RequestHeader(value = "Authorization", required = true) String token) {
        String checkResult = cartService.checkToken(token);
        if (!checkResult.equals("Incorrect")) {
            return cartService.paymentOfCart(Integer.parseInt(checkResult), token);
        } else {
            return null;
        }
    }
}
