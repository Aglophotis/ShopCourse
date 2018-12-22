package ru.aglophotis.mirea.microservice.identity.entities;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PortsConfiguration {
    private HashMap<String, String> mapPorts;

    public PortsConfiguration(HashMap<String, String> mapPorts) {
        this.mapPorts = mapPorts;
    }

    public PortsConfiguration() {
    }

    public HashMap<String, String> getMapPorts() {
        return mapPorts;
    }

    public void setMapPorts(HashMap<String, String> mapPorts) {
        this.mapPorts = mapPorts;
    }

    public String getPort(String name) {
        return mapPorts.get(name);
    }
}
