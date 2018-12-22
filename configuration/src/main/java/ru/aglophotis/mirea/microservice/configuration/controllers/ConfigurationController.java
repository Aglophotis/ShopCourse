package ru.aglophotis.mirea.microservice.configuration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.aglophotis.mirea.microservice.configuration.services.ConfigurationService;

import java.util.HashMap;

@Controller
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, String> getCurrencies() {
        return configurationService.getPortsMap();
    }
}
