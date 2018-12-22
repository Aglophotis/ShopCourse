package ru.aglophotis.mirea.microservice.balance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.aglophotis.mirea.microservice.balance.dao.BalanceDao;
import ru.aglophotis.mirea.microservice.balance.entities.Balance;
import ru.aglophotis.mirea.microservice.balance.entities.Currency;
import ru.aglophotis.mirea.microservice.balance.entities.PortsConfiguration;

import java.util.List;

@Service
public class BalanceService {

    private BalanceDao balanceDao;

    @Autowired
    private PortsConfiguration portsConfiguration;

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
                "http://localhost:" +
                        portsConfiguration.getPort("currency") +
                        "/currency",
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
        Balance balance = balanceDao.getByCurrencyId(idCurrency, authorId);
        if (balance == null) {
            return "Error: connection problems";
        }
        balance.setBalance(balance.getBalance() + value);
        balanceDao.update(balance);
        return "Balance successfully updated";
    }

    public String decreaseBalance(int idCurrency, double value, int authorId) {
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
}
