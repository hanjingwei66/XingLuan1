package com.shuojie.service.sensorService;


public interface SensorSubject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
    void clearObserver();
}
