package pucp.edu.pe.sap_backend.SimulatedAnnealing;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private Random random = new Random();
    public State solution(Problem problem, Map<Integer, Double> schedule,int maxIterations) {
        State current = problem.getInitialState(); // Use the updated method to get the initial state
        for (int t = 0;t < maxIterations; t++) {
            double temperature = schedule.getOrDefault(t, 0.0);
            if (temperature == 0) {
                return current;
            }
            State next = problem.getRandomSuccessor(current); // Use the updated method to get a random successor
            double deltaE = problem.calculateEnergy(next) - problem.calculateEnergy(current); // Use the updated method to calculate energy

            System.out.println("\n Additional Information: \n");
            System.out.println("Iteration: " + t);
            System.out.println("Temperature: " + temperature);
            System.out.println("Current Energy: " + problem.calculateEnergy(current));
            System.out.println("Delta Energy: " + deltaE);
            System.out.println("Acceptance Probability: " + Math.exp(deltaE / temperature));



            if (deltaE > 0 || Math.random() < Math.exp(deltaE / temperature)) {
                current = next;
            }
        }
        return current;
    }

    public static Map<Integer,Double> generateCoolingSchedule(double initialTemp,double coolingRate,int maxIterations){
       Map<Integer,Double> schedule = new HashMap<>();
       schedule.put(0,initialTemp);
       for(int i = 1; i < maxIterations; i++){
           initialTemp*=coolingRate;
           schedule.put(i,initialTemp);
       }
       return schedule;
    }

}
