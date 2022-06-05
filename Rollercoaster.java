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

        Passenger[] passengers = new Passenger[totalPassengers];
        Car[] cars = new Car[totalCars];
    }
}