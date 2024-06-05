package com.cbl.cityrtgs.services.inward.factory;

public interface Inward {

    String getServiceType();

    void processInward(long id, Object document);
}