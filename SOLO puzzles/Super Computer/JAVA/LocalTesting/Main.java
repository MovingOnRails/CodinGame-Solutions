import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static List<Integer> makeInitialAvailableNodeList(int N){
        List<Integer> res = new LinkedList<>();
        for(int i=0;i<N;i++){
            res.add(i);
        }
        return res;
    }

    // Tested by hand
    public static List<Integer> colourAdjacents(List<Integer> availableNodes, Integer currentNode, boolean[][] graph){
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

    public static int calculateMaxGraphSize(List<Integer> availableNodes,List<Integer> selectedNodes,boolean[][] graph){
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

    public static void main(String args[]) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        if(args.length == 1){
            File file = new File(args[0]);
            in = new Scanner(file);
        }
        int E = in.nextInt();
        int N = in.nextInt();
        boolean[][] graph = new boolean[N][N];
        for(int i=0;i<E;i++){
            int E1 = in.nextInt();
            int E2 = in.nextInt();
            graph[E1][E2] = true;
            graph[E2][E1] = true;
        }
        List<Integer> availableNodes = makeInitialAvailableNodeList(N);
        List<Integer> selectedNodes = new LinkedList<>();
        int res = calculateMaxGraphSize(availableNodes, selectedNodes, graph);
        System.out.println("maxGraphSize: "+res);
        //List<Integer> coloredAvailableNodes = colourAdjacents(availableNodes,5,graph);
        //System.out.println("coloredAvailableNodes "+coloredAvailableNodes);
    }
}
