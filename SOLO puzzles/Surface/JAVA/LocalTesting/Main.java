import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

class Coordinate {
    int X = -1;
    int Y = -1;
    Coordinate(int _X, int _Y){
        X = _X;
        Y = _Y;
    }
}
class Solution {

    public static void printMap(char[][] map, int H, int L){
        for(int i=0;i<H;i++){
            for(int j=0;j<L;j++){
                System.err.print(map[i][j]);
            }
            System.err.print('\n');
        }
    }

    public static void fillVisitedWithDefaultValue(int[][] visited, int defaultValue, int H, int L){
        for(int i=0;i<H;i++){
            for(int j=0;j<L;j++){
                visited[i][j] = defaultValue;
            }
        }
    }

    public static int lakeSize = 0;

    public static void floodFill(Coordinate coordinate, char[][] map, int[][] visited, int H, int L,
                                 LinkedList<Coordinate> visitedCoordinates){
        if( coordinate.X < 0 || coordinate.X == L  ||
            coordinate.Y < 0 || coordinate.Y == H  ||
            map[coordinate.Y][coordinate.X] == '#' ||
            visited[coordinate.Y][coordinate.X] != -1){
            return;
        }
        visited[coordinate.Y][coordinate.X] = 0;
        visitedCoordinates.add(new Coordinate(coordinate.X, coordinate.Y));
        lakeSize++;
        Coordinate oneToTheNorth = new Coordinate(coordinate.X, coordinate.Y-1);
        floodFill(oneToTheNorth, map, visited, H, L, visitedCoordinates);

        Coordinate oneToTheSouth = new Coordinate(coordinate.X, coordinate.Y+1);
        floodFill(oneToTheSouth, map, visited, H, L, visitedCoordinates);

        Coordinate oneToTheEast = new Coordinate(coordinate.X+1, coordinate.Y);
        floodFill(oneToTheEast, map, visited, H, L, visitedCoordinates);

        Coordinate oneToTheWest = new Coordinate(coordinate.X-1, coordinate.Y);
        floodFill(oneToTheWest, map, visited, H, L, visitedCoordinates);

        return;
    }

    public static void fillVisitedCoordinatesWithLakeSize(int[][] visited, LinkedList<Coordinate> visitedCoordinates, int lakeSize){
        for(Coordinate c : visitedCoordinates){
            visited[c.Y][c.X] = lakeSize;
        }
        return;
    }

    public static void main(String args[]) throws FileNotFoundException{
        Scanner in = new Scanner(System.in);
        if(args.length == 1){
            File file = new File(args[0]);
            in = new Scanner(file);
        }
        int L = in.nextInt();
        //System.err.println(L);
        int H = in.nextInt();
        //System.err.println(H);
        if (in.hasNextLine()) {
            in.nextLine();
        }
        char[][] map = new char[H][L];
        for (int i = 0; i < H; i++) {
            String row = in.nextLine();
            for(int j=0;j<L;j++){
                char c = row.charAt(j);
                map[i][j] = c;
            }
        }
        //printMap(map, H, L);
        int N = in.nextInt();
        //System.err.println(N);
        Coordinate[] coordinates = new Coordinate[N];
        for (int i = 0; i < N; i++) {
            int X = in.nextInt();
            int Y = in.nextInt();
            //System.err.print(X+" "+Y+"\n");
            Coordinate coordinate = new Coordinate(X, Y);
            coordinates[i] = coordinate;
        }

        int[][] visited = new int[H][L];
        fillVisitedWithDefaultValue(visited, -1, H, L);
        for (int i = 0; i < N; i++) {
            Coordinate coordinate = coordinates[i];
            lakeSize = 0;
            LinkedList<Coordinate> visitedCoordinates = new LinkedList<>();
            int solution = 0;
            if(visited[coordinate.Y][coordinate.X] != -1){
                solution = visited[coordinate.Y][coordinate.X];
            } else {
                floodFill(coordinate, map, visited, H, L, visitedCoordinates);
                fillVisitedCoordinatesWithLakeSize(visited, visitedCoordinates, lakeSize);
                solution = lakeSize;
            }

            
            System.out.println(solution);
        }
    }
}
