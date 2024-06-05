package com.cbl.cityrtgs.services.inward.factory;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InwardFactory {

    private static final Map<String, Inward> inwardServiceCache = new HashMap<>();
    private final List<Inward> services;

    public InwardFactory(List<Inward> services) {
        this.services = services;
    }

    public static Inward getService(String type) {
        Inward inwardService = inwardServiceCache.get(type);
        return inwardService;
    }

    @PostConstruct
    public void initServiceCache() {
        for (Inward inward : services) {
            inwardServiceCache.put(inward.getServiceType(), inward);
        }
    }
}
