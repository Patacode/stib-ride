package com.g56080.stibRide.util;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableAdapter implements Observable{

    private final List<Observer> observers;

    public ObservableAdapter(){
        observers = new ArrayList<>();
    }

    @Override
    public boolean addObserver(Observer observer){
        return observers.add(observer);
    }

    @Override
    public boolean removeObserver(Observer observer){
        return observers.remove(observer);
    }

    @Override
    public void notifyObservers(Observable source, Object arg){
        observers.forEach(observer -> observer.update(source, arg));
    }
}

