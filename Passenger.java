import java.math.*;
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
}