import java.util.concurrent.*;

public class Car implements Runnable {

    private Monitor carMon;
    private int id;

    public Car(int id, Monitor mon) {
        this.id = id;
        this.carMon = mon;
    }

    public void run() {
        while (true) {

            // All Passenger Enters
            carMon.carLoad(id);

            try {
                // Car is running
                int randTime = ThreadLocalRandom.current().nextInt(0, 1000);
                Thread.sleep(randTime);
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
            }

            // All Passengers Leaves
            carMon.carUnload(id);
        }
    }

}
