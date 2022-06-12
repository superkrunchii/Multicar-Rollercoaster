public class Monitor {
    private int carId, passengerCapacity, seatsAvailable;

    private Object notifyPassenger = new Object();
    private Object notifyCar = new Object();

    private int count, line_length;
    private int seats_available = 0;
    boolean coaster_loading_passengers = false;
    boolean passengers_riding = true;

    public void boardCar(int id) {
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

    public synchronized boolean isCarRunning() {
        if (seats_available == 0) {
            seats_available = Rollercoaster.SEAT_AVAIL;
            coaster_loading_passengers = true; 
            passengers_riding = true; 
            return true;
        } else
            return false;
    }

    private synchronized boolean seatAvailable() {
        
        if ((seats_available > 0)
                && (seats_available <= Rollercoaster.SEAT_AVAIL)
                && (!passengers_riding)) {
            seats_available--;
            return true;
        } else
            return false;
    }

    public void unboardCar(int count) {
        synchronized (this) {
            
            passengers_riding = false;
            coaster_loading_passengers = false;
        }
        synchronized (notifyPassenger) {
            notifyPassenger.notifyAll();
        }
    }

    public void tryToGetOnCar(int count) {
        synchronized (notifyPassenger) {
            while (!seatAvailable()) {
                try {
                    notifyPassenger.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        System.out.println("Passenger " + (count + 1) + " gets in car");
        synchronized (notifyCar) {
            notifyCar.notify();
        }
    }
}
