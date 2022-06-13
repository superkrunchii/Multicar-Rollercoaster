import java.util.concurrent.*;

public class Car implements Runnable {

    private Monitor mon;
    private int id;

    public Car(int id, Monitor mon) {
        this.id = id;
        this.mon = mon;
    }

    public void run() {
        while (true) {
            System.out.println("Car " + this.id + " is ready to be boarded"); 
            mon.boardCar(id);  
            System.out.println("All aboard Car " + this.id);
            try {
                System.out.println("Car" + this.id + " is running");
                int rand = ThreadLocalRandom.current().nextInt(0, 1000);    
                Thread.sleep(rand);
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
            }
            mon.unboardCar(id);    
            System.out.println("All ashore from Car " + this.id);
        }
    }

}
