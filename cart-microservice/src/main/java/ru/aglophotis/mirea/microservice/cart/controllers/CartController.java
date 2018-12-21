package ru.aglophotis.mirea.microservice.cart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.aglophotis.mirea.microservice.cart.entities.CartItem;
import ru.aglophotis.mirea.microservice.cart.services.CartService;
import ru.aglophotis.mirea.microservice.cart.utils.TokenUtils;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    private TokenUtils tokenUtils;

    public CartController() {
        tokenUtils = new TokenUtils();
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    @ResponseBody
    public List<CartItem> getCart(@RequestHeader(value = "Authorization", required = true) String token) {
        return cartService.getCart(tokenUtils.getPayload(token).getSub());
    }

    @RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteItem(@PathVariable("id") int id,
                             @RequestHeader(value = "Authorization", required = true) String token) {
            return cartService.deleteItemFromCart(id, tokenUtils.getPayload(token).getSub());
    }

    @RequestMapping(value = "/cart/item/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String putItem(@PathVariable("id") int id,
                          @RequestHeader(value = "Authorization", required = true) String token) {
        return cartService.putItemToCart(id, tokenUtils.getPayload(token).getSub());
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public String pay(@RequestHeader(value = "Authorization", required = true) String token) {
        return cartService.paymentOfCart(tokenUtils.getPayload(token).getSub(), token);
    }
}
