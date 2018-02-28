import edu.princeton.cs.algs4.*;

public class SlowCongress {
  private double[] priorityArray; // population, votes, or whatever determines priority
  private String[] statesArray;
  private int[] seatsArray;
  private int seatsLeft;
  private int numberOfStates;

  public SlowCongress(int totalSeatNumber, int numberOfStates, double[] priorities, String[] stateNames ) {

    this.priorityArray = priorities;
    this.statesArray = stateNames;
    this.seatsArray = new int[numberOfStates];
    this.seatsLeft = totalSeatNumber;
    this.numberOfStates = numberOfStates;

    //Initially give each state 1 seat if there are enough seats - there will always be enough seats for the first round (definition of Huntington-Hill rule)
    for (int i = 0; i<numberOfStates; i++) {
      seatsArray[i] = 1;
      seatsLeft--;
      priorityArray[i] = calculatePriority(priorityArray[i], seatsArray[i]);
      //StdOut.println("State: " + statesArray[i] + " Priority: " + priorityArray[i]);
    }
  }

  public double calculatePriority(double currentPriority, int currentSeats) {
    double newPriority;
    double calculatedOriginal; // Calculates the initial population so this does not have to be stored in an array - this might not be the most efficient way

    if (currentSeats > 1) {
      calculatedOriginal = currentPriority * Math.sqrt((currentSeats-1)*(currentSeats));
    }
    else { //if it is the first seat received --> currentPriority has the original population
      calculatedOriginal = currentPriority;
    }

    //newPriority = currentPriority/Math.sqrt(currentSeats*(currentSeats + 1)); //Go back to this if we decide to store the original population in an array
    newPriority = calculatedOriginal/Math.sqrt(currentSeats*(currentSeats + 1));
    return newPriority;
  }

  public void giveSeat() {
    //Gives a seat to the state with highest priority but find index it first
    double max = 0;
    int maxIndex = 0;

    // Find highest priority
    if (this.seatsLeft!=0) {
      for (int j = 0; j < numberOfStates; j++) {
        if (priorityArray[j] > max) {
          max = priorityArray[j];
          maxIndex = j;
        }
      }

      // Give the seat and update priority
      seatsArray[maxIndex]++;
      seatsLeft--;
      priorityArray[maxIndex] = calculatePriority(priorityArray[maxIndex], seatsArray[maxIndex]);
    }
  }

  public void giveAllSeats() {
    while (seatsLeft!=0) {
      giveSeat();
    }
    for (int i=0; i<numberOfStates; i++) {
      StdOut.println(statesArray[i] + " " + seatsArray[i]); //Maybe move this to another method called e.g. getCurrentSeating or printCurrentSeating
    }
  }

  /*public void printCurrentSeating() {
    for (int i=0; i<numberOfStates; i++) {
      StdOut.println(statesArray[i] + " " + seatsArray[i]); //Maybe move this to another method called e.g. getCurrentSeating or printCurrentSeating
    }
  }*/

  public static void main(String[] args) {
    // Load data from StdIn
    int stateNumber = StdIn.readInt();
    int seatNumber = StdIn.readInt();
    StdIn.readLine(); //Perhaps handle this more gracefully
    String[] states = new String[stateNumber];
    double[] priorities = new double[stateNumber];

    int k = 0;
    while (!StdIn.isEmpty()) {
      states[k] = StdIn.readLine();
      priorities[k] = Double.parseDouble(StdIn.readLine());
      k++;
    }

    SlowCongress congress = new SlowCongress(seatNumber, stateNumber, priorities, states);
    congress.giveAllSeats();
  }
}
