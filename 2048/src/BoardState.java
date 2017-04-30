import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by JohnnyOn on 4/29/17.
 */
public class BoardState {
    int[][] curr;
    int[] currArray;
    int emptySpaces;
    boolean newGame;
    BoardState() {
        newBoard();

    }

    public int[] convertToXY(int coord) {
        int y = coord / 4;
        int x = coord - y*4;
        return new int[]{y,x};
    }

    public void newBoard() {
        emptySpaces = 16;
        this.curr = new int[4][4];
        this.currArray = new int[16];
        Random rand = new Random();
        int n = rand.nextInt(16);
        int[] location = convertToXY(n);
        int twoOrFour = 2*(rand.nextInt(2) + 1);
        this.curr[location[0]][location[1]] = twoOrFour;


        currArray[n] = twoOrFour;
        emptySpaces--;
        newGame = true;
    }

    public void insert() {
        if (emptySpaces == 0) {
            return;
        }
        newGame = false;
        Random rand = new Random();
        int n = rand.nextInt(emptySpaces);
        int numEmpty = 0;
        int insertNum = 0;
            for (int j = 0; j < 16; j++) {
                if (currArray[j] == 0) {
                    numEmpty++;
                    if (numEmpty == n + 1) {
                        insertNum = j;
                        break;
                    }
                }
            }
        int twoOrFour = (rand.nextInt(2) + 1)*2;
        currArray[insertNum] = twoOrFour;
        int[] coord = convertToXY(insertNum);
        curr[coord[0]][coord[1]] = twoOrFour;
        emptySpaces--;
        if (emptySpaces == 0) {
            //leftright
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (curr[i][j] == curr[i][j+ 1] ){
                            return;
                    }
                }
            }
            //updown
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (curr[i][j] == curr[i+1][j] ){
                        return;
                    }
                }
            }
            Scanner sc = new Scanner(System.in);
            System.out.print("GAME OVER. Start Over? Press 1");
            int i = 0;
            while (i != 1) {
                i = sc.nextInt();
            }
            newBoard();

        }
    }

    public void right() {
        boolean success = false;
        for (int i = 0; i < 4; i++) {
            int [] row = curr[i];
            //condenseRight
            if (!row.equals(condenseRight(row))) {
                success = true;
                row = condenseRight(row);
            }

            //add if same
            for (int j = 3; j > 0; j--) {
                if (row[j] != 0 && row[j] == row[j-1]) {
                    row[j] = 2*row[j];
                    row[j-1] = 0;
                    row = condenseRight(row);
                    emptySpaces++;
                }
            }

            curr[i] = row;
            for (int j = 0; j <4; j++ ) {
                currArray[i*4 + j] = row[j];
            }
        }
        if (success) {
            insert();
        } else {
            System.out.print("cannot go right");
        }
    }

    public void left() {
        //copy
        int[][] tempCurr = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempCurr[i][j] = curr[i][j];
            }
        }
        //flip
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curr[i][3-j] = tempCurr[i][j];
            }
        }

        right();

        //flipback

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempCurr[i][j] = curr[i][j];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curr[i][3-j] = tempCurr[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                currArray[i*4 +j] = curr[i][j];
            }
        }


    }

    public int[] condenseRight(int[] row) {
        LinkedList<Integer> nonZero = new LinkedList<>();
        int[] newRow = new int[4];
        for (int j = 3; j >= 0; j--) {
            if (row[j] != 0) {
                nonZero.addLast(row[j]);
            }
        }

        //put in
        for (int j = 3; j >= 0; j--) {
            if (nonZero.size() != 0) {
                newRow[j] = nonZero.poll();
            } else {
                newRow[j] = 0;
            }
        }
        return newRow;
    }

    public void up() {
        //copy
        int[][] tempCurr = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempCurr[i][j] = curr[i][j];
            }
        }
        //flip
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curr[j][3-i] = tempCurr[i][j];
            }
        }

        right();

        //flipback

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempCurr[i][j] = curr[i][j];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curr[3-j][i] = tempCurr[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                currArray[i*4 +j] = curr[i][j];
            }
        }

    }

    public void down() {
        //copy
        int[][] tempCurr = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempCurr[i][j] = curr[i][j];
            }
        }
        //flip
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curr[3-j][i] = tempCurr[i][j];
            }
        }

        right();

        //flipback

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempCurr[i][j] = curr[i][j];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curr[j][3-i] = tempCurr[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                currArray[i*4 +j] = curr[i][j];
            }
        }

    }

    public void printBoard() {
        System.out.println("  --- --- --- ---");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(" | ");
                System.out.print(curr[i][j]);
            }
            System.out.println(" |");
            System.out.println("  --- --- --- ---");

        }
    }

    public void play(String input) {
        if (input.equals("w")) {
            up();
            printBoard();
        } else if (input.equals("s")) {
            down();
            printBoard();
        } else if (input.equals("a")) {
            left();
            printBoard();
        } else if (input.equals("d")) {
            right();
            printBoard();
        }
    }
    public static void main(String[] args) {
        BoardState x = new BoardState();
        x.printBoard();
//        int[] row = new int[4];
//        row[0] = 4;
//        row[3] = 4;
//        int[] newRow = x.condenseRight(row);
//        int[][] state = new int[4][4];
//        state[0][1] = 4;
//        state[0][2] = 2;
//        state[2][1] = 2;
//        state[3][0] = 4;
//        state[3][1] = 4;
//        state[3][2] = 2;
//        state[3][3] = 2;
//        int[] stateArray = new int[16];
//        stateArray[1] = 4;
//        stateArray[2] = 2;
//        stateArray[9] = 2;
//        stateArray[12] = 4;
//        stateArray[13] = 4;
//        stateArray[14] = 2;
//        stateArray[15] = 2;
//        x.curr = state;
//        x.currArray = stateArray;
//        x.emptySpaces = 9;
//        x.down();
//        x.printBoard();
        Scanner board = new Scanner(System.in);
        x.newGame = false;
        while (!x.newGame) {
            String line = board.next();
            for (int i = 0; i < line.length(); i++) {
                String input = line.substring(i,i+1);
                x.play(input);

            }
        }
    }
}
