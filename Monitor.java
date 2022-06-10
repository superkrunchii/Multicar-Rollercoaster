public class Monitor {
    private int carId, passengerCapacity, seatsAvailable;

    private Object notifyPassenger = new Object();
    private Object notifyCar = new Object();

    private int i, line_length; // Number of passengers waiting to board the car.
    private int seats_available = 0;
    boolean coaster_loading_passengers = false;
    boolean passengers_riding = true;

    // Loading of Car
    public void carLoad(int id) {
        synchronized (notifyCar) {
            while (!isCarRunning()) {
                try {
                    notifyCar.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
            }

            System.out.println("Car " + id + " is full and loaded boom");

            synchronized (notifyCar) {
                notifyCar.notifyAll();
            }

        }
    }

    // Check if the Car is Running
    public synchronized boolean isCarRunning() {
        if (seats_available == 0) {
            // if there is no seat, car starts to run and reset parameters.
            seats_available = Rollercoaster.SEAT_AVAIL;
            // reset seat available num for the next ride
            coaster_loading_passengers = true; // Indicating car is running.
            passengers_riding = true; // passengers are riding in the car.
            return true;
        } else
            return false;
    }

    private synchronized boolean seatAvailable() {
        // Check if seat is still available for passenger who tries to get on.
        if ((seats_available > 0)
                && (seats_available <= Rollercoaster.SEAT_AVAIL)
                && (!passengers_riding)) {
            seats_available--;
            return true;
        } else
            return false;
    }

    public void carUnload(int i) {
        synchronized (this) {
            // reset parameters
            passengers_riding = false;
            coaster_loading_passengers = false;
        }
        synchronized (notifyPassenger) {
            notifyPassenger.notifyAll();
        }
    }

    public void tryToGetOnCar(int i) {
        synchronized (notifyPassenger) {
            while (!seatAvailable()) {
                try {
                    notifyPassenger.wait(); // Notify the passenger to wait
                } catch (InterruptedException e) {
                }
            }
        }
        System.out.println("Passenger " + (i + 1) + " gets in car at timestamp: " + System.currentTimeMillis());
        synchronized (notifyCar) {
            notifyCar.notify();
        }
    }
}
