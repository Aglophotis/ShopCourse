package ru.aglophotis.mirea.microservice.cart.shop.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.cart.shop.dao.CartDao;
import ru.aglophotis.mirea.microservice.cart.shop.entities.Balance;
import ru.aglophotis.mirea.microservice.cart.shop.entities.CartItem;
import ru.aglophotis.mirea.microservice.cart.shop.entities.Currency;
import ru.aglophotis.mirea.microservice.cart.shop.entities.Item;

import java.util.List;

@Service
public class CartService {

    private CartDao cartApi;

    public CartService() {
        cartApi = new CartDao();
    }

    public List<CartItem> getCart(){
        return cartApi.getAll(1);
    }

    public String deleteItemFromCart(int id){
        int err = cartApi.deleteById(id, 1);
        if (err == 0){
            return "Error: stuff wasn't found in cart";
        } else {
            return "The stuff was successfully removed from cart";
        }
    }

    public String putItemToCart(int id){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8085/item/" + id;
        ResponseEntity<Item> response
                = restTemplate.getForEntity(fooResourceUrl, Item.class);
        Item item = response.getBody();
        CartItem cartItem = new CartItem();
        cartItem.setAuthorId(1);
        cartItem.setItemId(item.getId());
        if (item == null) {
            return "Error: id wasn't found";
        }
        int nCountInItems = item.getAmount();
        if (nCountInItems == 0) {
            return "The stuffs are over";
        }
        int nCountInCart = cartApi.getCountItems(id, 1);
        if (nCountInCart < nCountInItems){
            if (cartApi.insert(cartItem) == -1) {
                return "Error: connection problems";
            }
        } else {
            return "The stuffs are over";
        }
        return "Stuff was been added to cart";
    }

    public String paymentOfCart(){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl;

        List<CartItem> cartItems = cartApi.getAllDistinct();
        int paymentAmount = 0;
        if (cartItems.isEmpty()){
            return "Error: cart is empty";
        }
        for (CartItem cartItem : cartItems) {
            fooResourceUrl = "http://localhost:8085/item/" + cartItem.getItemId();
            ResponseEntity<Item> responseItem
                    = restTemplate.getForEntity(fooResourceUrl, Item.class);
            Item item = responseItem.getBody();
            int nCountInStuffs = item.getAmount();
            int nCountInCart = cartApi.getCountItems(cartItem.getItemId(), 1);
            if (nCountInCart > nCountInStuffs) {
                return "Error: the quantity of the items has been changed.";
            }
            System.out.println(item.getPrice());
            paymentAmount += nCountInCart * item.getPrice();
        }

        ResponseEntity<List<Currency>> responseCurrencies = restTemplate.exchange(
                "http://localhost:8080/currency",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Currency>>(){});
        List<Currency> currencies = responseCurrencies.getBody();
        double balance = 0;

        ResponseEntity<List<Balance>> responseBalances = restTemplate.exchange(
                "http://localhost:8095/balance",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Balance>>(){});
        List<Balance> balances = responseBalances.getBody();

        for (int i = 0; i < balances.size(); i++) {
            balance += balances.get(i).getBalance() * currencies.get(i).getExchangeRate();
        }

        System.out.println(paymentAmount + " " + balance);
        if (balance < paymentAmount){
            return "You don't have enough money";
        }

        for (int i = 0; i < balances.size(); i++) {
            double exchangeRate = currencies.get(i).getExchangeRate();
            double currencyBalance = balances.get(i).getBalance() * exchangeRate;
            if (currencyBalance >= paymentAmount) {
                ResponseEntity<String> tmpResponse = restTemplate.exchange(
                        "http://localhost:8095/currency/"
                                + currencies.get(i).getId()
                                + "/balance/"
                                + (currencyBalance-paymentAmount)/exchangeRate,
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<String>(){});
                break;
            } else {
                ResponseEntity<String> tmpResponse = restTemplate.exchange(
                        "http://localhost:8095/currency/"
                                + currencies.get(i).getId()
                                + "/balance/0",
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<String>(){});
                paymentAmount -= currencyBalance;
            }
        }

        for (CartItem cartItem : cartItems) {
            fooResourceUrl = "http://localhost:8085/item/" + cartItem.getItemId();
            ResponseEntity<Item> responseItem
                    = restTemplate.getForEntity(fooResourceUrl, Item.class);
            Item item = responseItem.getBody();

            int nCountInItems = item.getAmount();
            int nCountInCart = cartApi.getCountItems(cartItem.getItemId(), 1);
            if (nCountInCart <= nCountInItems) {
                fooResourceUrl = "http://localhost:8085/item/"
                        + item.getId()
                        + "/"
                        + (nCountInItems - nCountInCart);
                ResponseEntity<String> tmpResponse = restTemplate.exchange(
                        fooResourceUrl,
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<String>(){});
            }
        }
        cartApi.deleteAll();
        return "The payment has been successfully completed";
    }
}
