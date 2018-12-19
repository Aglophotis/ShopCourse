package ru.aglophotis.mirea.microservice.currency.services;

import org.springframework.stereotype.Service;
import ru.aglophotis.mirea.microservice.currency.dao.CurrencyDao;
import ru.aglophotis.mirea.microservice.currency.entities.Currency;

import java.util.List;

@Service
public class CurrencyService {

    private CurrencyDao currencyDao;

    public CurrencyService() {
        currencyDao = new CurrencyDao();
    }

    public List<Currency> getCurrencies(){
        return currencyDao.getAll();
    }

    public Currency getCurrency(int id){
        return currencyDao.getById(id);
    }
}
