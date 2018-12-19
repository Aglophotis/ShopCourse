package ru.aglophotis.mirea.microservice.balance.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.balance.dao.BalanceDao;
import ru.aglophotis.mirea.microservice.balance.entities.Balance;
import ru.aglophotis.mirea.microservice.balance.entities.Currency;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class BalanceService {

    private BalanceDao balanceDao;

    public BalanceService() {
        balanceDao = new BalanceDao();
    }

    public List<Balance> getBalance(int authorId) {
        return balanceDao.getAll(authorId);
    }

    public String setBalance(int currencyId, double value, int authorId) {
        if (balanceDao.updateById(value, currencyId, authorId) == -1) {
            return "Balance update failed";
        } else {
            return "Balance successfully updated";
        }
    }

    public String createWallet(int id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Currency>> responseCurrencies = restTemplate.exchange(
                "http://localhost:8080/currency",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Currency>>(){});
        for (Currency currency : responseCurrencies.getBody()) {
            Balance balance = new Balance(id, currency.getId(), 0d);
            if (balanceDao.insert(balance) == -1) {
                return "Error creating wallet";
            }
        }
        return "Wallet has been created";
    }

    public String increaseBalance(int idCurrency, double value, int authorId) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/currency/" + idCurrency;
        ResponseEntity<Currency> response
                = restTemplate.getForEntity(fooResourceUrl, Currency.class);
        Currency currency = response.getBody();
        if (currency == null) {
            return "Error: connection problems";
        }
        Balance balance = balanceDao.getByCurrencyId(idCurrency, authorId);
        if (balance == null) {
            return "Error: connection problems";
        }
        balance.setBalance(balance.getBalance() + value);
        balanceDao.update(balance);
        return "Balance successfully updated";
    }

    public String decreaseBalance(int idCurrency, double value, int authorId) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/currency/" + idCurrency;
        ResponseEntity<Currency> response
                = restTemplate.getForEntity(fooResourceUrl, Currency.class);
        Currency currency = response.getBody();
        if (currency == null) {
            return "Error: connection problems";
        }
        Balance balance = balanceDao.getByCurrencyId(idCurrency, authorId);
        if (balance == null) {
            return "Error: connection problems";
        }
        if (balance.getBalance() == 0){
            return "Your balance is zero";
        }
        if (balance.getBalance() < value){
            return "Error: your balance is below the amount withdrawn";
        }
        balance.setBalance(balance.getBalance() - value);
        balanceDao.update(balance);
        return "Balance successfully updated";
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
}
