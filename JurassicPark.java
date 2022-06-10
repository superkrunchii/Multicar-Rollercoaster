
package DeadLock;

import java.util.concurrent.Semaphore;
import java.util.Random;



class Passenger extends JurassicPark implements Runnable {
   private int id = 0;
   public Passenger(int id) { this.id = id; }

   public void run() {
      while (true) {
         nap(1+(int)random.nextInt(1000*wanderTime));
         System.out.println(" passenger"+id+" wants to ride");

         // TODO: Increment laman ng car by 1, check if full car, then call car to leave if full 
         V(carTaken);  // ???????
         P(carFilled);
         System.out.println(" passenger"+id+" taking a ride");

         // TODO: Unboard - decrement 1 after the ride until all passengers are out of car
         P(passengerReleased);
         System.out.println(" passenger"+id+" finished riding");
      }
   }
}

class Car extends JurassicPark implements Runnable {
   private int id = 0;
   private int cap = 0;
   public Car(int id, int cap) { 
      this.id = id; 
      this.cap=cap;
   }

   public void run() {
      while (true) {
         System.out.println(" car"+id+" ready to load");
         // TODO: wait current car in queue
         // make car available for load
         // put in n amount of passengers based on cap, wait if full
         V(carAvail);  
         P(carTaken);  
         V(carFilled);
         System.out.println(" car"+id+" going on safari");
         nap(1+(int)random.nextInt(1000*rideTime));
         System.out.println(" car"+id+" has returned");
         V(passengerReleased);
      }
   }
}

public class JurassicPark {

   static final int numPassengers = 10, numCars = 2, cap=5;
   static final int wanderTime = 5, rideTime = 4, runTime = 60;
   static final Semaphore carAvail = new Semaphore(0);
   static final Semaphore carTaken = new Semaphore(0);
   static final Semaphore carFilled = new Semaphore(0);
   static final Semaphore passengerReleased = new Semaphore(0);
   Random random=new Random();

   public static void main(String[] args){
      for (int i = 0; i < numPassengers; i++)
         new Thread(new Passenger(i)).start();
      for (int i = 0; i < numCars; i++)
         new Thread(new Car(i,cap)).start();
      nap(1000*runTime);
      
   }
   
   public static void nap(int time){
       try{
       Thread.sleep(time);
       }
       catch(InterruptedException ex){}
   }
   public static void P(Semaphore x){
       try{
       x.acquire();
       }
       catch(InterruptedException ex){
           
       }
       
   }
   
    public static void V(Semaphore x){
        x.release();
        
       
       
       
   }

}
