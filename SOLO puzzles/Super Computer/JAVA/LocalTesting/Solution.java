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
}

public class Solution {

    private static List<Integer> makeInitialAvailableNodeList(int N){
        List<Integer> res = new LinkedList<>();
        for(int i=0;i<N;i++){
            res.add(i);
        }
        return res;
    }

    // Tested by hand
    private static List<Integer> colourAdjacents(List<Integer> availableNodes, Integer currentNode, boolean[][] graph){
        List<Integer> res = new LinkedList<>(availableNodes);
        for(int j=0;j<graph.length;j++){
            boolean isAdjacent = graph[currentNode][j];
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

    private static int calculateMaxGraphSize(List<Integer> availableNodes,List<Integer> selectedNodes,boolean[][] graph){
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
            Calculation calculation = new Calculation(i,J,J+D);
            calculations.add(calculation);
        }
        boolean[][] graph = new boolean[N][N];
        for(int i=0;i<N;i++){
            Calculation calculationI = findCalculationWithNumber(i,calculations);
            for(int j=0;j<N;j++){
                Calculation calculationJ = findCalculationWithNumber(j,calculations);
                if(i != j){
                    if(calculationsOverlap(calculationI, calculationJ)){
                        graph[calculationI.number][calculationJ.number] = true;
                        graph[calculationJ.number][calculationI.number] = true;
                    }
                }
            }
        }
        // TODO make tests for the translation from Intervals to adjacent nodes
        // TODO test calculationsOverlap by hand
        List<Integer> availableNodes = makeInitialAvailableNodeList(N);
        List<Integer> selectedNodes = new LinkedList<>();
        int res = calculateMaxGraphSize(availableNodes, selectedNodes, graph);
        System.out.println("maxGraphSize: "+res);
        //List<Integer> coloredAvailableNodes = colourAdjacents(availableNodes,5,graph);
        //System.out.println("coloredAvailableNodes "+coloredAvailableNodes);
    }
}
