package ru.aglophotis.mirea.microservice.configuration.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConfigurationService {

    private HashMap<String, String> portsMap;

    public ConfigurationService() {
        portsMap = new HashMap<>();
        portsMap.put("item", "8085");
        portsMap.put("balance", "8095");
        portsMap.put("cart", "8081");
        portsMap.put("currency", "8080");
        portsMap.put("identity", "8083");
        portsMap.put("authorization", "8086");
    }

    public HashMap<String, String> getPortsMap() {
        return portsMap;
    }
}
