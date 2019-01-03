package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //Prompt user to set the board size and # of bombs
        System.out.println("Enter board size and number of bombs(n, m, # of bombs):");
        String input1 = br.readLine();
        String[] init = input1.split(",");

        if (init.length != 3) {
            throw new IllegalArgumentException("Not valid numbers!");
        }

        int n = Integer.parseInt(init[0].trim());
        int m = Integer.parseInt(init[1].trim());
        int count = Integer.parseInt(init[2].trim());

        MineSweeper mineSweeper = new MineSweeper(n, m, count);
        System.out.println("The field size is " + n + " * " + m + ", and there are " + count + "mines.");

        //Prompt user to play the game by choosing initial click
        System.out.println("Start game!\nEnter your initial position(x, y):");
        String input2 = br.readLine();
        String[] first = input2.trim().split(",");

        int firstX = Integer.parseInt(first[0].trim());
        int firstY = Integer.parseInt(first[1].trim());

        if (firstX <= 0 || firstX > n || firstY <= 0 || firstY > m) {
            throw new IllegalArgumentException("x should be 1~" + n + ", y should be 1~" + m);
        }

        mineSweeper.setBombs(new Point(firstX - 1, firstY - 1));
        mineSweeper.buildBoard();
        mineSweeper.playGame(new Point(firstX - 1, firstY - 1), 1);
        mineSweeper.printBoard();

        while (true) {
            //Prompt user to take action
            System.out.println("Enter next step(x, y, left-click=1/right-click=-1):");
            String line = br.readLine();
            String[] nextStep = line.split(",");

            int x = Integer.parseInt(nextStep[0].trim());
            int y = Integer.parseInt(nextStep[1].trim());
            int action = Integer.parseInt(nextStep[2].trim());
            if (mineSweeper.playGame(new Point(x - 1, y - 1), action)) {
                mineSweeper.printBoard();
            } else {
                mineSweeper.showBoard();
                return;
            }
        }
    }
}
