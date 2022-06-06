import java.util.*;

public class Rollercoaster {
    public static void main(String[] args) {
        
        int totalPassengers , carCapacity, totalCars;

        Scanner sc = new Scanner(System.in);

        System.out.println("Total Passengers: ");
        totalPassengers = sc.nextInt();
        System.out.println("Car Capacity: ");
        carCapacity = sc.nextInt();
        System.out.println("Total Cars: ");
        totalCars = sc.nextInt();

        Thread carThread[] = new Thread[totalCars];

        Passenger[] passengers = new Passenger[totalPassengers];
        Car[] cars = new Car[totalCars];
        Monitor mon = new Monitor();
        
        for (int i = 0; i < carCapacity; i++) {
            cars[i] = new Car(i, mon);
            carThread[i] = new Thread(cars[i]);
        }
    }
}