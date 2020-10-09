import java.util.Arrays;

public class EscapePods {

    //main method to test code
    public static void main(String[] args) throws Exception {
        int[][] test = {{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}};
        System.out.println(escapePods(new int[]{0}, new int[]{3}, test));
    }

    public static int escapePods(int[] entrances, int[] exits, int[][] path) {
        int bunnysThatEscape = 0;

        for (int entrance : entrances) {
            for (int i = 0; i < path.length; i++) {
                //find bunnies that are at the entrance
                if (path[entrance][i] != 0) {
                    int bunnyCount = path[entrance][i]; // number of bunnies at this entrance
                    //pass to recursive helper function
                    bunnysThatEscape += followPath(bunnyCount, i, exits, path);
                }
            }
        }

        return bunnysThatEscape;
    }

    public static int followPath(int bunnyCount, int location, int[] exits, int[][] path){
        int count = 0;
        int corridor = 0; //the column in the array

        //whilst there are still bunnies making there way to escape pods and we have still got rooms
        while (bunnyCount > 0 && corridor < path.length) {
            //check to see if we can travel to this room
            if (path[location][corridor] != 0) {
                int corrdiorSize = path[location][corridor]; //number of bunnies that can travel to this room
                int bunniesMoving = 0;

                //is the number of bunnies that can travel to this room less than the number of bunnies
                // if so set the number of bunnies that can travel this way to 0 and
                // reduce the number of bunnies accordingly
                if (corrdiorSize < bunnyCount) {
                    bunnyCount -= corrdiorSize;
                    bunniesMoving = corrdiorSize;
                    path[location][corridor] = 0;
                }
                else {
                    bunniesMoving = bunnyCount;
                    bunnyCount = 0;
                    path[location][corridor] = bunnyCount;
                }

                //check to see if the room the bunnies are travelling to is an escape pod
                // increase the count according to the number of bunnies going to escape pods
                if (Arrays.binarySearch(exits, corridor) >= 0){
                    count += bunniesMoving;
                }
                else {
                    //if this isn't an escape route follow the bunnies into the next room
                    count += followPath(bunniesMoving, corridor, exits, path);
                }

            }
            corridor++;
        }

        return  count;
    }
}
