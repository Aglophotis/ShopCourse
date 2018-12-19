package ru.aglophotis.mirea.microservice.cart.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.cart.dao.CartDao;
import ru.aglophotis.mirea.microservice.cart.entities.Balance;
import ru.aglophotis.mirea.microservice.cart.entities.CartItem;
import ru.aglophotis.mirea.microservice.cart.entities.Currency;
import ru.aglophotis.mirea.microservice.cart.entities.Item;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    private CartDao cartApi;

    public CartService() {
        cartApi = new CartDao();
    }

    public List<CartItem> getCart(int authorId){
        return cartApi.getAll(authorId);
    }

    public String deleteItemFromCart(int id, int authorId){
        if (cartApi.deleteById(id, authorId) == 0){
            return "Error: stuff wasn't found in cart";
        } else {
            return "The stuff was successfully removed from cart";
        }
    }

    public String putItemToCart(int id, int authorId){
        Item item = getItemById(id);
        if (item == null) {
            return "Error: id wasn't found";
        }
        CartItem cartItem = new CartItem();
        cartItem.setAuthorId(authorId);
        cartItem.setItemId(item.getId());
        int nCountInItems = item.getAmount();
        if (nCountInItems == 0) {
            return "The stuffs are over";
        }
        int nCountInCart = cartApi.getCountItems(id, authorId);
        if (nCountInCart < nCountInItems){
            if (cartApi.insert(cartItem) == -1) {
                return "Error adding items to cart";
            }
        } else {
            return "The stuffs are over";
        }
        return "Stuff was been added to cart";
    }

    public String paymentOfCart(int authorId, String token){
        double balance = 0;
        List<CartItem> cartItems = cartApi.getAllDistinct(authorId);
        int paymentAmount = 0;
        if (cartItems.isEmpty()){
            return "Error: cart is empty";
        }

        for (CartItem cartItem : cartItems) {
            Item item = getItemById(cartItem.getItemId());
            int nCountInStuffs = item.getAmount();
            int nCountInCart = cartApi.getCountItems(cartItem.getItemId(), authorId);
            if (nCountInCart > nCountInStuffs) {
                return "Error: the quantity of the items has been changed.";
            }
            paymentAmount += nCountInCart * item.getPrice();
        }
        List<Currency> currencies = getCurrencies();
        List<Balance> balances = getBalances(token);

        for (int i = 0; i < balances.size(); i++) {
            balance += balances.get(i).getBalance() * currencies.get(i).getExchangeRate();
        }

        if (balance < paymentAmount){
            return "You don't have enough money";
        }

        for (int i = 0; i < balances.size(); i++) {
            double exchangeRate = currencies.get(i).getExchangeRate();
            double currencyBalance = 0;
            for (Balance tmpBalance : balances) {
                if (tmpBalance.getCurrencyId() == currencies.get(i).getId()) {
                    currencyBalance = tmpBalance.getBalance() * exchangeRate;
                    break;
                }
            }
            if (currencyBalance >= paymentAmount) {
                updateBalance(token, currencies.get(i).getId(), (currencyBalance-paymentAmount)/exchangeRate);
                break;
            } else {
                updateBalance(token, currencies.get(i).getId(), 0d);
                paymentAmount -= currencyBalance;
            }
        }

        for (CartItem cartItem : cartItems) {
            Item item = getItemById(cartItem.getItemId());
            int nCountInItems = item.getAmount();
            int nCountInCart = cartApi.getCountItems(cartItem.getItemId(), authorId);
            if (nCountInCart <= nCountInItems) {
                updateItemsCount(item.getId(), nCountInItems - nCountInCart);
            }
        }
        cartApi.deleteAll(authorId);
        return "The payment has been successfully completed";
    }

    public String checkToken(String token) {
        String decodedToken = decodeToken(token);
        String[] parseToken = decodedToken.split(":");
        Long timeLive = Long.parseLong(parseToken[5]);
        if (timeLive < new Date().getTime()) {
            return "Incorrect";
        } else {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<>(token);
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8083/token", request, String.class);
            if (response.getBody().equals("Incorrect token")) {
                return "Incorrect";
            } else {
                return parseToken[2];
            }
        }
    }

    private String decodeToken(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        return decodedToken;
    }

    private Item getItemById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8085/item/" + id;
        ResponseEntity<Item> response
                = restTemplate.getForEntity(fooResourceUrl, Item.class);
        return response.getBody();
    }

    private List<Currency> getCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Currency>> response = restTemplate.exchange(
                "http://localhost:8080/currency",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Currency>>(){});
        return response.getBody();
    }

    private List<Balance> getBalances(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<List<Balance>> response = restTemplate.exchange(
                "http://localhost:8095/balance",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<Balance>>(){});
        return response.getBody();
    }

    private void updateBalance(String token, int currencyId, double value) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8095/currency/"
                        + currencyId
                        + "/balance/"
                        + value,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<String>(){});
    }

    private void updateItemsCount(int id, int value) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tmpResponse = restTemplate.exchange(
                "http://localhost:8085/item/"
                        + id
                        + "/"
                        + value,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<String>(){});
    }

}
