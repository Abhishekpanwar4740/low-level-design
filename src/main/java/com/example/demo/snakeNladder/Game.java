package com.example.demo.snakeNladder;

import java.util.Deque;
import java.util.LinkedList;

public class Game {
    Board board;
    Dice dice;
    Deque<Player> playerList = new LinkedList<>();
    Player winner;

    public Game() {
        initializeGame();
    }

    public void initializeGame() {
        board = new Board(10, 5, 4);
        dice = new Dice(1);
        winner = null;
        addPlayers();
    }

    public void addPlayers() {
        Player player1 = new Player("p1", 0);
        Player player2 = new Player("p2", 0);
        playerList.add(player1);
        playerList.add(player2);
    }

    public void startGame() {
        while (winner == null) {
            Player playerTurn = findPlayerTurn();
            System.out.println("player turn is:" + playerTurn.id + " current position is: " + playerTurn.currentPos);
            int number = dice.rollDice();
            int playerNewPosition = playerTurn.currentPos + number;
            playerNewPosition = findJumpCheck(playerNewPosition);
            playerTurn.currentPos = playerNewPosition;

            System.out.println("player turn is:" + playerTurn.id + " new Position is: " + playerNewPosition);
            //check for winning condition
            if (playerNewPosition >= board.cells.length * board.cells.length - 1) {

                winner = playerTurn;
            }

        }
        System.out.println("WINNER IS:" + winner.id);
    }

    public Player findPlayerTurn() {
        Player player = playerList.removeFirst();
        playerList.addLast(player);
        return player;
    }

    public int findJumpCheck(int playerPos) {
        if (playerPos > board.cells.length * board.cells.length - 1) {
            return playerPos;
        }
        Cell cell = board.getCell(playerPos);
        if (cell.jump != null && cell.jump.start == playerPos) {
            String jumpBy = (cell.jump.start < cell.jump.end) ? "ladder" : "Snake";
            System.out.println("jump done by: " + jumpBy);
            return cell.jump.end;
        }
        return playerPos;
    }
}
