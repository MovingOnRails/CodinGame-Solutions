import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Position {
    public int y;
    public int x;
    Position(int y,int x){
        this.x = x;
        this.y = y;
    }
    Position move(Direction direction, List<Direction> moveList, boolean addDirectionToMoveList){
        if(addDirectionToMoveList){
            moveList.add(direction);
        }
        switch(direction){
            case SOUTH:
                return this.moveSOUTH();
            case EAST:
                return this.moveEAST();
            case NORTH:
                return this.moveNORTH();
            case WEST:
                return this.moveWEST();
        }
        return new Position(-1,-1);
    }
    Position moveSOUTH(){
        return new Position(this.y+1,this.x);
    }
    Position moveEAST(){
        return new Position(this.y, this.x+1);
    }
    Position moveNORTH(){
        return new Position(this.y-1, this.x);
    }
    Position moveWEST(){
        return new Position(this.y,this.x-1);
    }

    public String asString(){
        return "("+this.y+","+this.x+")";
    }
    
    boolean equals(Position other){
        return (this.x == other.x) && (this.y == other.y);
    }
}

enum Direction {
    SOUTH("SOUTH"), 
    EAST("EAST"), 
    NORTH("NORTH"), 
    WEST("WEST");

    public String strValue;

    Direction(String strValue){
        this.strValue = strValue;
    }

    public Direction nextDirection(){
        return this.values()[(this.ordinal()+1) % this.values().length];
    }
}

enum Priority {
    OFF("OFF"),
    SOUTH("SOUTH"),
    EAST("EAST"),
    NORTH("NORTH"),
    WEST("WEST");

    public String strValue;

    Priority(String strValue){
        this.strValue = strValue;
    }

    public Priority nextPriority(boolean inverterON){
        if(inverterON){
            if(this.ordinal() == 0){
                return this.values()[this.values().length-1];
            } else {
                return this.values()[this.ordinal()-1];
            }
        } else {
            return this.values()[(this.ordinal()+1) % this.values().length];
        }
    }

    public Direction asDirection(){
        return Direction.values()[this.ordinal()-1];
    }
}

class RobotState {
    Position position = new Position(-1,-1);
    Direction direction = Direction.SOUTH;
    boolean beerMode = false;
    Priority priority = Priority.OFF;
    List<Direction> moveList = new LinkedList<>();
    boolean inverterON = false;

    Position nextTentativePosition(){
        Position res = new Position(-1,-1);
        if(this.priority == Priority.OFF){
            res = this.position.move(this.direction, this.moveList, false);
        } else {
            Direction dir = this.priority.asDirection();
            res = this.position.move(dir, this.moveList, false);
        }
        return res;
    }

    Position move(){
        if(this.priority == Priority.OFF){
            this.position = this.position.move(this.direction, this.moveList, true);
        }else{
            Direction dir = this.priority.asDirection();
            this.position = this.position.move(dir, this.moveList, true);
            this.direction = dir;
        }
        this.priority = Priority.OFF;
        return this.position;
    }

    boolean deepEquals(RobotState other){
        System.out.println("----RobotState.deepEquals----");
        boolean positionEquals = this.position.equals(other.position);
        boolean directionEquals = this.direction == other.direction;
        boolean beermodeEquals = this.beerMode == other.beerMode;
        boolean priorityEquals = this.priority == other.priority;
        boolean inverterONEquals = this.inverterON == other.inverterON;

        System.out.println("this.position: "+position.asString()+"    "+"other.position: "+other.position.asString());
        System.out.println("this.direction: "+this.direction.strValue+"    "+"other.direction: "+other.direction.strValue);
        System.out.println("this.beermode: "+this.beerMode+"    "+"other.beermode: "+other.beerMode);
        System.out.println("this.priority: "+this.priority.strValue+"    "+"other.priority: "+other.priority.strValue);
        System.out.println("this.inverterON: "+this.inverterON+"    "+"other.inverterON: "+other.inverterON);

        return  positionEquals &&
                directionEquals &&
                beermodeEquals &&
                priorityEquals &&
                inverterONEquals;
    }
}

class JourneyState{
    RobotState robotState = new RobotState();
    char[][] map;
    int L;
    int C;
    JourneyState(RobotState robotState, char[][] map, int L, int C){
        RobotState newRobotState = new RobotState();

        newRobotState.position = new Position(robotState.position.y,robotState.position.x);
        newRobotState.direction = robotState.direction;
        newRobotState.beerMode = robotState.beerMode;
        newRobotState.priority = robotState.priority;
        newRobotState.inverterON = robotState.inverterON;
        
        this.robotState = newRobotState;
        char[][] newMap = new char[L][C];
        for(int i=0;i<L;i++){
            for(int j=0;j<C;j++){
                newMap[i][j] = map[i][j];
            }
        }
        this.map = newMap;
        this.L = L;
        this.C = C;
    }
    boolean deepEquals(JourneyState other){
        boolean robotStateDeepEquals = other.robotState.deepEquals(this.robotState);
        boolean mapDeepEquals = Arrays.deepEquals(this.map, other.map);
        System.out.println("robotStateDeepEquals: "+robotStateDeepEquals);
        System.out.println("mapDeepEquals: "+mapDeepEquals);
        return  robotStateDeepEquals && mapDeepEquals;
    }
}

public class Solution {

    public static Position teleport(Position robotPosition, char[][] map, int L, int C){
        List<Position> teleports = new ArrayList<>();
        for(int i=0;i<L;i++){
            for(int j=0;j<C;j++){
                if(map[i][j] == 'T'){
                    teleports.add(new Position(i,j));
                }
            }
        }
        Position teleportTO = new Position(-1,-1);
        for(Position t : teleports){
            if(!t.equals(robotPosition)){
                teleportTO = t;
            }
        }
        return teleportTO;
    }

    
    public static RobotState getNextState(char nextCell, RobotState robotState, char[][] map, int L, int C){
        switch(nextCell){
            case '@':
            case ' ':
                robotState.move();
                return robotState;
            case 'X':
                if(robotState.beerMode){
                    robotState.move();
                    Position robotPosition = robotState.position;
                    map[robotPosition.y][robotPosition.x] = ' ';
                } else {
                    robotState.priority = robotState.priority.nextPriority(robotState.inverterON);
                }
                return robotState;
            case '#':
                robotState.priority = robotState.priority.nextPriority(robotState.inverterON);
                return robotState;
            case 'S':
                robotState.move();
                robotState.direction = Direction.SOUTH;
                return robotState;
            case 'E':
                robotState.move();
                robotState.direction = Direction.EAST;
                return robotState;
            case 'N':
                robotState.move();
                robotState.direction = Direction.NORTH;
                return robotState;
            case 'W':
                robotState.move();
                robotState.direction = Direction.WEST;
                return robotState;
            case 'B':
                robotState.move();
                robotState.beerMode = !robotState.beerMode;
                return robotState;
            case 'I':
                robotState.move();
                robotState.inverterON = !robotState.inverterON;
                return robotState;
            case 'T':
                robotState.move();
                robotState.position = teleport(robotState.position, map, L, C);
                return robotState;
            case '$':
                robotState.move();
                return robotState;
        }
        return new RobotState();
    }

    public static Position getInitialPosition(char[][] map, int L, int C){
        for(int i=0;i<L;i++){
            for(int j=0;j<C;j++){
                char currentCell = map[i][j];
                if(currentCell == '@'){
                    return new Position(i,j);
                }
            }
        }
        return new Position(-1,-1);
    }

    public static void printMap(char[][] map, int L, int C){
        for(int i=0;i<L;i++){
            for(int j=0;j<C;j++){
                System.out.print(map[i][j]);
            }
            System.out.print('\n');
        }
    }

	public static void printMapWithRobot(char[][] map, int L, int C, Position robotPosition){
         for(int i=0;i<L;i++){
            for(int j=0;j<C;j++){
				if(robotPosition.y == i && robotPosition.x == j){
                    System.out.print("R");
                }else{
                    System.out.print(map[i][j]);
                }
            }
            System.out.print('\n');
         }

	}

    public static void printMoveList(List<Direction> moveList){
        for(Direction d : moveList){
            System.out.println(d.strValue);
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int C = in.nextInt();
        char[][] map = new char[L][C];
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < L; i++) {
            String row = in.nextLine();
            for(int j = 0; j < C; j++){
                char cell = row.charAt(j);
                map[i][j] = cell;
            }
        }

        RobotState currentRobotState = new RobotState();
        currentRobotState.position = getInitialPosition(map, L, C);
        currentRobotState.direction = Direction.SOUTH;
        currentRobotState.beerMode = false;
        currentRobotState.moveList = new LinkedList<>();

        List<JourneyState> journeyStates = new LinkedList<>();
        boolean isLooping = false;
        printMap(map,L,C);
        while(true){
            Position nextTentativePosition = currentRobotState.nextTentativePosition();
            char nextCell = map[nextTentativePosition.y][nextTentativePosition.x];
            printMapWithRobot(map,L,C,currentRobotState.position);
            JourneyState currentJourneyState = new JourneyState(currentRobotState, map, L, C);
            journeyStates.add(currentJourneyState);

            RobotState nextRobotState = getNextState(nextCell, currentRobotState, map, L, C);
            JourneyState nextJourneyState = new JourneyState(nextRobotState, map, L, C);

            for(JourneyState journeyState : journeyStates){
                if(journeyState.deepEquals(nextJourneyState)){
                    isLooping = true;
                    break;
                }
            }

            if(nextCell == '$' || isLooping){
                break;
            }

            currentRobotState = nextRobotState;
            System.out.println("-----------------------------------------------------------------------------------");
        }
        if(!isLooping){
            printMoveList(currentRobotState.moveList);
        } else {
            System.out.println("LOOP");
        }
        
    }
}
