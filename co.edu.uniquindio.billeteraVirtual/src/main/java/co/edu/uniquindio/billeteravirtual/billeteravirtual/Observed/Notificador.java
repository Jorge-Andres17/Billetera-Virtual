package co.edu.uniquindio.billeteravirtual.billeteravirtual.Observed;

import java.util.ArrayList;
import java.util.List;

public class Notificador {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    protected void notifyObservers(Object evento) {
        for (Observer o : observers) {
            o.update(evento);
        }
    }
}
