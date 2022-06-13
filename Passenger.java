class Passenger implements Runnable {
    private int id;
    private Monitor mp;

    public Passenger(int num, Monitor m) {
        id = num;
        this.mp = m;
    }

    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 4000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mp.getInCar(id);
    }
}