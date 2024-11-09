import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static int loadRollerCoaster(Queue<Integer> groups, int C){
        boolean isFull = false;
        int currentCapacity = 0;
        Queue<Integer> groupsOnRide = new LinkedList<>();
        while(!isFull){
            Integer nextGroup = groups.peek();
            if(nextGroup == null || currentCapacity + nextGroup > C){
                isFull = true;
            } else {
                nextGroup = groups.poll();
                groupsOnRide.add(nextGroup);
                currentCapacity += nextGroup;
            }
        }
        while(!groupsOnRide.isEmpty()){
            Integer group = groupsOnRide.poll();
            groups.add(group);
        }
        return currentCapacity;
    }

    public static void main(String args[]) throws FileNotFoundException {


        Scanner in = new Scanner(System.in);
        if(args.length == 1){
            File file = new File(args[0]);
            in = new Scanner(file);
        }

        int L = in.nextInt();
        int C = in.nextInt();
        int N = in.nextInt();
        Queue<Integer> groups = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            int pi = in.nextInt();
            groups.add(pi);
        }
        int numberOfRides = 0;
        BigInteger earnings = new BigInteger("0");
        while(numberOfRides < C){
            BigInteger singleRideEarnings = BigInteger.valueOf(loadRollerCoaster(groups,L));
            earnings = earnings.add(singleRideEarnings);
            numberOfRides++;
        }
        
        
        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(earnings);
    }
}
