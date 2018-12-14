package ru.aglophotis.mirea.microservice.balance.shop.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.balance.shop.dao.BalanceDao;
import ru.aglophotis.mirea.microservice.balance.shop.entities.Balance;
import ru.aglophotis.mirea.microservice.balance.shop.entities.Currency;

import java.util.List;

@Service
public class BalanceService {

    private BalanceDao balanceDao;

    public BalanceService() {
        balanceDao = new BalanceDao();
    }

    public List<Balance> getBalance() {
        return balanceDao.getAll(1);
    }

    public void setBalance(double value, int currencyId, int authorId) {
        balanceDao.updateById(value, currencyId, authorId);
    }

    public String increaseBalance(int idCurrency, double value) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/currency/" + idCurrency;
        ResponseEntity<Currency> response
                = restTemplate.getForEntity(fooResourceUrl, Currency.class);
        Currency currency = response.getBody();
        if (currency == null) {
            return "Error: connection problems";
        }
        Balance balance = balanceDao.getByCurrencyId(idCurrency, 1);
        if (balance == null) {
            return "Error: connection problems";
        }
        balance.setBalance(balance.getBalance() + value);
        balanceDao.update(balance);
        return "Balance successfully updated";
    }

    public String decreaseBalance(int idCurrency, double value) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/currency/" + idCurrency;
        ResponseEntity<Currency> response
                = restTemplate.getForEntity(fooResourceUrl, Currency.class);
        Currency currency = response.getBody();
        if (currency == null) {
            return "Error: connection problems";
        }
        Balance balance = balanceDao.getByCurrencyId(idCurrency, 1);
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
}
