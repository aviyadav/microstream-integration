package org.jnosql.demo.se;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.List;

public class App {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Airplane airplane = Airplane.id("1").model("777").year(1994).manufacturer("Boing");
            Airplane airplane2 = Airplane.id("2").model("767").year(1982).manufacturer("Boing");
            Airplane airplane3 = Airplane.id("3").model("747-8").year(2010).manufacturer("Boing");
            Airplane airplane4 = Airplane.id("4").model("E-175").year(2023).manufacturer("Embraer");
            Airplane airplane5 = Airplane.id("5").model("A319").year(1995).manufacturer("Airbus");
            Airport airport = container.select(Airport.class).get();
            airport.saveAll(List.of(airplane, airplane2, airplane3, airplane4, airplane5));
            var boings = airport.findByModel(airplane.getModel());
            var all = airport.findAll().toList();
            System.out.println("The boings: " + boings);
            System.out.println("The boing models avialables: " + boings.size());
            System.out.println("The airport total: " + all.size());
        }
        System.exit(0);
    }

    private App() {
    }
}
