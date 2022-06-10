public class Rollercoaster {
    public static int PASSENGER_NUM = 20;// Number of people totally
    public static int CAR_NUM = 5; // Number of passenger cars
    public static int SEAT_AVAIL = 4; // Number of passengers a car holds

    public static void main(String[] args) {
        Monitor rcMon = new Monitor();

        Car theCar;
        Passenger aPassenger;

        /* Create arrays of threads for initialization */
        Thread t1[] = new Thread[PASSENGER_NUM];
        Thread t2[] = new Thread[CAR_NUM];
        /* Fill the thread arrays */
        for (int i = 0; i < PASSENGER_NUM; i++) {
            aPassenger = new Passenger(i, rcMon);
            t1[i] = new Thread(aPassenger);
        }
        for (int i = 0; i < CAR_NUM; i++) {
            theCar = new Car(i, rcMon);
            System.out.println("i = "+i);   //<===
            t2[i] = new Thread(theCar);
        }

        for (int i = 0; i < PASSENGER_NUM; i++) {
            t1[i].start();
        }
        for (int i = 0; i < CAR_NUM; i++) {
            System.out.println("started car i = "+i);   //<===
            t2[i].start();
        }

        try {
            for (int i = 0; i < PASSENGER_NUM; i++) {
                t1[i].join();
            }
        } catch (InterruptedException e) {
            System.err.println("Passenger thread join interruption");
        }

        try {
            for (int i = 0; i < CAR_NUM; i++) {
                t2[i].join();
            }
        } catch (InterruptedException e) {
            System.err.println("Car thread join interruption");
        }
        System.out.println("Program has terminated Normally");
    }
}