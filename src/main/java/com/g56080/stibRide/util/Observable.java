package com.g56080.stibRide.util;

public interface Observable{
    boolean addObserver(Observer observer);
    boolean removeObserver(Observer observer);
    void notifyObservers(Observable source, Object arg);
}

