import java.util.*;

class Monitor {
    Rollercoaster rc = new Rollercoaster();
    private int seats_available;
    boolean carBool = false;

    private Object pNotif = new Object(), cNotif = new Object();
    private int temp = 0;
    public static ArrayList<Integer> carQueue = new ArrayList<Integer>();

    public void setSeats(int seats) {
        seats_available = seats;
    }

    public void setRC(Rollercoaster rc) {
        this.rc = rc;
    }

    public void getInCar(int i) {
        synchronized (pNotif) {
            while (!hasVacantSeat()) {
                try {
                    pNotif.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Passenger " + (i + 1) + " gets in car");
        synchronized (cNotif) {
            cNotif.notify();
        }
    }

    private synchronized boolean hasVacantSeat() {
        if ((seats_available > 0)
                && (seats_available <= rc.carCap)
                && (!carBool)) {
            seats_available--;
            return true;
        } else
            return false;
    }

    public void boardCar(int i) {
        if (temp == 0) {
            System.out.println("Car " + (i + 1) + " is ready to be loaded");
            temp++;
            carQueue.add(i);
        }

        synchronized (cNotif) {
            while (!isCarRunning()) {
                try {
                    cNotif.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("All aboard car " + (carQueue.get(0) + 1));
        synchronized (pNotif) {
            pNotif.notifyAll();
        }
    }

    private synchronized boolean isCarRunning() {
        if (seats_available == 0) {
            // Reset
            seats_available = rc.carCap;
            carBool = true;
            return true;
        } else
            return false;
    }

    public void unboardCar(int i) {
        int carId = carQueue.get(0);
        System.out.println("All ashore car " + (carQueue.get(0) + 1));
        System.out.println("Car " + (carQueue.get(0) + 1) + " is ready to be loaded");
        carQueue.remove(0);
        carQueue.add(carId);

        synchronized (this) {
            carBool = false;
        }
        synchronized (pNotif) {
            pNotif.notifyAll();
        }
    }
}