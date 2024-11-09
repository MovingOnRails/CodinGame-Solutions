import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Pair {
    int earnings;
    int index;
    Pair(int _earnings, int _index){
        earnings = _earnings;
        index = _index;
    }
}

class Solution {

    public static Pair loadRollerCoaster(int index, int[] groups, int C, int[] indexEarningsMap, int[] fromIndexToIndexMap){

        if(fromIndexToIndexMap[index] != -1 && indexEarningsMap[index] != -1){
//            System.out.println("Memorized");
            return new Pair(indexEarningsMap[index],fromIndexToIndexMap[index]);
        } else {
            // Calculate the toIndex and the earnings that have been made on this single ride
            int fromIndex = index;
            boolean isFull = false;
            int currentCapacity = 0;
            while (!isFull) {
                if (index == groups.length || currentCapacity + groups[index] > C) {
                    isFull = true;
                } else {
                    currentCapacity += groups[index];
                    index++;
                }
            }
            int toIndex = index;
            if(index == groups.length){
                toIndex = 0;
            }
            int earnings = currentCapacity;
            indexEarningsMap[fromIndex] = earnings;
            fromIndexToIndexMap[fromIndex] = toIndex;
            return new Pair(earnings, toIndex);

        }
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
        int[] groups = new int[N];
        int[] indexEarningsMap = new int[N];
        int[] fromIndexToIndexMap = new int[N];
        for (int i = 0; i < N; i++) {
            int pi = in.nextInt();
            groups[i] = pi;
            indexEarningsMap[i] = -1;
            fromIndexToIndexMap[i] = -1;
        }
        int numberOfRides = 0;
        BigInteger earnings = new BigInteger("0");
        int index = 0;
        while(numberOfRides < C){
            Pair rideResult = loadRollerCoaster(index, groups, L, indexEarningsMap, fromIndexToIndexMap);

            BigInteger singleRideEarnings = BigInteger.valueOf(rideResult.earnings);
            earnings = earnings.add(singleRideEarnings);
            index = rideResult.index;

            numberOfRides++;
        }
        
        
        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(earnings);
    }
}
