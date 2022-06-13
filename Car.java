import java.util.concurrent.*;

public class Car implements Runnable {

    private Monitor mon;
    private int id;

    public Car(int id, Monitor m) {
        this.id = id;
        mon = m;
    }

    public void run() {
        while (true) {
            try {
                mon.boardCar(id);
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
                mon.unboardCar(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
