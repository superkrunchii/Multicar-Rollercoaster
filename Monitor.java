public class Monitor {
    private int carId, passengerCapacity, seatsAvailable;

    private Object notifyPassenger = new Object();
    private Object notifyCar = new Object();

    // Loading of Car
    public void carLoad(int id) {
        synchronized (notifyCar) {
            while(!isCarRunning()) {
                try {
                    notifyCar.wait();
                } catch(InterruptedException e) {
                    e.printStackTrace(System.out);
                }
            }

            System.out.println("Car is full and loaded " + System.currentTimeMillis());

            synchronized (notifyCar) {
                notifyCar.notifyAll();
            }

        }
    }

    // Check if the Car is Running
    public synchronized boolean isCarRunning() {

    }
}
