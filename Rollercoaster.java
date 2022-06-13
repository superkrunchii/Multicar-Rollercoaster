import java.util.*;

class Rollercoaster {
    int numPassengers, numCars, carCap;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Monitor m = new Monitor();
        Rollercoaster rc = new Rollercoaster();
        Car c;
        Passenger p;

        System.out.print("Enter number of passengers: ");
        rc.numPassengers = sc.nextInt();
        System.out.print("Enter number of cars: ");
        rc.numCars = sc.nextInt();
        System.out.print("Enter number of available seats: ");
        rc.carCap = sc.nextInt();
        sc.close();

        m.setSeats(rc.carCap);
        m.setRC(rc);

        System.out.println("\n===============================");
        System.out.println("      Simulation Start");
        System.out.println("===============================");

        Thread passengers[] = new Thread[rc.numPassengers];
        Thread cars[] = new Thread[rc.numCars];

        for (int i = 0; i < rc.numPassengers; i++) {
            p = new Passenger(i, m);
            passengers[i] = new Thread(p);
        }
        for (int i = 0; i < rc.numCars; i++) {
            c = new Car(i, m);
            cars[i] = new Thread(c);
        }

        for (int i = 0; i < rc.numPassengers; i++) {
            passengers[i].start();
        }
        for (int i = 0; i < rc.numCars; i++) {
            cars[i].start();
        }

        try {
            for (int i = 0; i < rc.numPassengers; i++) {
                passengers[i].join(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < rc.numCars; i++) {
                cars[i].join(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n===============================");
        System.out.println("      All Rides Completed");
        System.out.println("===============================");
        System.exit(0);
    }
}