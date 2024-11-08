import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Calculation {
    int number = -1;
    int startDay = -1;
    int endDay = -1;
    Calculation(int number, int startDay, int endDay){
        this.number = number;
        this.startDay = startDay;
        this.endDay = endDay;
    }
    public String toString(){
        return "("+this.number+", "+this.startDay+", "+this.endDay+")";
    }
}

public class Solution {

    private static boolean calculationsOverlap(Calculation c1, Calculation c2){
        return !(c1.startDay > c2.endDay || c1.endDay < c2.startDay);
    }

    public static void main(String args[]) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        if(args.length == 1){
            File file = new File(args[0]);
            in = new Scanner(file);
        }
        int N = in.nextInt();
        Calculation[] calculations = new Calculation[N];
        for (int i = 0; i < N; i++) {
            int J = in.nextInt();
            int D = in.nextInt();
            Calculation calculation = new Calculation(i,J,J+D-1);
            calculations[i] = calculation;
        }
        Arrays.sort(calculations, Comparator.comparingInt(calc -> calc.endDay));
        int index = 0;
        int selectedCount = 0;
        while(index<N){
            Calculation curr = calculations[index];
            selectedCount++;
            index++;
            if(index < N){
                Calculation following = calculations[index];
                while(calculationsOverlap(curr, following) && index<N){
                    index++;
                    if (index<N){
                        following = calculations[index];
                    }
                }
            }
        }
        System.out.println(selectedCount);
    }
}
