import java.math.*;
import java.util.*;

class RollerCoaster {
    public static int PASSENGER_NUM = 25;// Number of people totally
    public static int CAR_NUM = 3; // Number of passenger cars
    public static int SEAT_AVAIL = 3; // Number of passengers a car holds

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
            t2[i] = new Thread(theCar);
        }

        for (int i = 0; i < PASSENGER_NUM; i++) {
            t1[i].start();
        }
        for (int i = 0; i < CAR_NUM; i++) {
            t2[i].start();
        }

        try {
            for (int i = 0; i < PASSENGER_NUM; i++) {
                t1[i].join(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Passenger thread join interruption");
        }

        try {
            for (int i = 0; i < CAR_NUM; i++) {
                t2[i].join(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Car thread join interruption");
        }
        System.out.println("Program has terminated Normally");
    }
} // end of RollerCoaster
  // ========================= PASSENGER CLASS ===========================

class Passenger implements Runnable {
    private int id;
    private Monitor mtrPSNGR;

    public Passenger(int num, Monitor mtr) {
        id = num;
        this.mtrPSNGR = mtr;
    }

    public void run() {
        // while(true) {
        for (int i = 0; i < 1; i++) { // every passenger only takes one ride
            try {
                Thread.sleep((int) (Math.random() * 4000));
            } catch (InterruptedException e) {
            }
            mtrPSNGR.tryToGetOnCar(id);
        }
    }
} // end of Passenger class
  // ============================ CAR CLASS ===========================

class Car implements Runnable {
    private int id; // Car ID
    private Monitor carMon;

    public Car(int i, Monitor monitorIn) {
        id = i;
        this.carMon = monitorIn;
    }

    public void run() {
        while (true) {
            
            try {

                carMon.passengerGetOn(id);
                Thread.sleep((int) (Math.random() * 2000));
                carMon.passengerGetOff(id);
            } catch (InterruptedException e) {
            } // Car runs for a while
        
        }
    }
} // end of Car class
  // =========================== Monitor Class ================================

class Monitor {
    private int seats_available = RollerCoaster.SEAT_AVAIL;
    boolean coaster_loading_passengers = false;
    boolean passengers_riding = false;

    private Object notifyPassenger = new Object(); // enter/exit protocol provides mutual exclusion.
    private Object notifyCar = new Object(); // the car waits on this.
    private int temp = 0;
    public static ArrayList<Integer> carQueue = new ArrayList<Integer>();

    public void tryToGetOnCar(int i) {
        synchronized (notifyPassenger) {
            while (!seatAvailable()) {
                try {
                    notifyPassenger.wait(); // Notify the passenger to wait
                } catch (InterruptedException e) {
                }
            }
        }
        System.out.println("Passenger " + i + " gets in car");
        synchronized (notifyCar) {
            notifyCar.notify();
        }
    }

    private synchronized boolean seatAvailable() {
        // Check if seat is still available for passenger who tries to get on.
        if ((seats_available > 0)
                && (seats_available <= RollerCoaster.SEAT_AVAIL)
                && (!passengers_riding)) {
            seats_available--;
            return true;
        } else
            return false;
    }

    public void passengerGetOn(int i) {
        if(temp == 0) {
            System.out.println("Car " + i + " is ready to be loaded");
            temp++;
            carQueue.add(i);
        }

        synchronized (notifyCar) {
            while (!carIsRunning()) {
                try {
                    notifyCar.wait();

                } catch (InterruptedException e) {
                }
            }
        }

            System.out.println("The Car " + carQueue.get(0) + " is full and starts running at timestampe: " + System.currentTimeMillis());

        synchronized (notifyPassenger) {
            
            notifyPassenger.notifyAll();
        }
    }

    private synchronized boolean carIsRunning() {
        // Check if car is running
        if (seats_available == 0) {
            // if there is no seat, car starts to run and reset parameters.
            seats_available = RollerCoaster.SEAT_AVAIL;
            // reset seat available num for the next ride
            coaster_loading_passengers = true; // Indicating car is running.
            passengers_riding = true; // passengers are riding in the car.
            return true;
        } else
            return false;
    }

  public void passengerGetOff(int i) {
    int carId = carQueue.get(0);

    System.out.println("Car " + carQueue.get(0) + " has unloaded");
    System.out.println("Car " + carQueue.get(0) + " is ready to be loaded");
    carQueue.remove(0);
    carQueue.add(carId);

    synchronized (this) {
      // reset parameters
      passengers_riding = false;
      coaster_loading_passengers = false;
    }
    synchronized(notifyPassenger) {notifyPassenger.notifyAll();}
  }
} // end of Monitor class