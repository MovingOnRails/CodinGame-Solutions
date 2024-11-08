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

    // Tested by hand, OK
    private static void calculationsOverlapTest(){
        // Should give FALSE
        Calculation c1 = new Calculation(0,0,1);
        Calculation c2 = new Calculation(1,2,3);
        boolean calculationsOverlap1 = calculationsOverlap(c1,c2);
        System.out.println("c1: "+c1.toString());
        System.out.println("c2: "+c2.toString());
        System.out.println("calculationsOverlap C1_C2: "+calculationsOverlap1);
        Calculation c3 = new Calculation(2,4,5);
        boolean calculationsOverlap2 = calculationsOverlap(c2,c3);
        System.out.println("c2: "+c2.toString());
        System.out.println("c3: "+c3.toString());
        System.out.println("calculationsOverlap C2_C3: "+calculationsOverlap2);

        // Should give TRUE
        Calculation c4 = new Calculation(3,2,4);
        Calculation c5 = new Calculation(4,1,2);
        Calculation c6 = new Calculation(5,3,5);
        Calculation c7 = new Calculation(6,4,5);
        Calculation c8 = new Calculation(7,1,5);
        boolean calculationsOverlapC4_C5 = calculationsOverlap(c4,c5);
        System.out.println("c4: "+c4.toString());
        System.out.println("c5: "+c5.toString());
        System.out.println("calculationsOverlap C4_C5: "+calculationsOverlapC4_C5);
        boolean calculationsOverlapC4_C6 = calculationsOverlap(c4,c6);
        System.out.println("c4: "+c4.toString());
        System.out.println("c6: "+c6.toString());
        System.out.println("calculationsOverlap C4_C6: "+calculationsOverlapC4_C6);
        boolean calculationsOverlapC4_C7 = calculationsOverlap(c4,c7);
        System.out.println("c4: "+c4.toString());
        System.out.println("c7: "+c7.toString());
        System.out.println("calculationsOverlap C4_C7: "+calculationsOverlapC4_C7);
        boolean calculationsOverlapC4_C8 = calculationsOverlap(c4,c8);
        System.out.println("c4: "+c4.toString());
        System.out.println("c8: "+c8.toString());
        System.out.println("calculationsOverlap C4_C8: "+calculationsOverlapC4_C8);
    }

    private static List<Integer> makeInitialAvailableNodeList(int N){
        List<Integer> res = new LinkedList<>();
        for(int i=0;i<N;i++){
            res.add(i);
        }
        return res;
    }

    private static boolean isAdjacent(List<Integer> adjacentNodes, Integer node){
        for(Integer a : adjacentNodes){
            if(a.equals(node)){
                return true;
            }
        }
        return false;
    }

    // Tested by hand
    private static List<Integer> colourAdjacents(List<Integer> availableNodes, Integer currentNode, List<Integer>[] graph){
        List<Integer> res = new LinkedList<>(availableNodes);
        for(int j=0;j<graph.length;j++){
            boolean isAdjacent = isAdjacent(graph[currentNode], j);
            if(isAdjacent){
                ListIterator it = res.listIterator();
                while(it.hasNext()){
                    Integer n = (Integer) it.next();
                    if(n.equals(j)){
                        it.remove();
                        break;
                    }
                }
            }
        }
        return res;
    }

    private static int calculateMaxGraphSize(List<Integer> availableNodes,List<Integer> selectedNodes,List<Integer>[] graph){
        if(availableNodes.isEmpty()){return selectedNodes.size();}
        int maxGraphSize = selectedNodes.size();
        // We iterate over the availableNodes in order of apparition
        for(int index=0;index<availableNodes.size();index++){
            int currentNode = availableNodes.get(index);
            List<Integer> availableNodesRecursive = availableNodes.subList(index+1, availableNodes.size());
            availableNodesRecursive = colourAdjacents(availableNodesRecursive, currentNode, graph);
            List<Integer> selectedNodesRecursive = new LinkedList<>(selectedNodes);
            selectedNodesRecursive.add(currentNode);

            int recursiveMaxGraphSize = calculateMaxGraphSize(availableNodesRecursive, selectedNodesRecursive, graph);
            if(recursiveMaxGraphSize > maxGraphSize){
                maxGraphSize = recursiveMaxGraphSize;
            }
        }
        return maxGraphSize;
    }

    private static Calculation findCalculationWithNumber(int number, List<Calculation> calculations){
        ListIterator it = calculations.listIterator();
        while(it.hasNext()){
            Calculation currentCalculation =(Calculation) it.next();
            if(currentCalculation.number == number){
                return currentCalculation;
            }
        }
        return new Calculation(-1,-1,-1);
    }

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
        List<Calculation> calculations = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            int J = in.nextInt();
            int D = in.nextInt();
            Calculation calculation = new Calculation(i,J,J+D-1);
            calculations.add(calculation);
            //System.out.println(calculation.toString());
        }
        List<Integer>[] graph = new LinkedList[N];
        for(int i=0;i<N;i++){
            graph[i] = new LinkedList<>();
        }
        for(int i=0;i<N;i++){
            Calculation calculationI = findCalculationWithNumber(i,calculations);
            for(int j=0;j<N;j++){
                Calculation calculationJ = findCalculationWithNumber(j,calculations);
                if(i != j){
                    if(calculationsOverlap(calculationI, calculationJ)){
                        graph[calculationI.number].add(calculationJ.number);
                    }
                }
            }
        }
        //System.out.println("graph: "+Arrays.deepToString(graph));

        List<Integer> availableNodes = makeInitialAvailableNodeList(N);
        List<Integer> selectedNodes = new LinkedList<>();
        int res = calculateMaxGraphSize(availableNodes, selectedNodes, graph);
        System.out.println(res);
    }
}
