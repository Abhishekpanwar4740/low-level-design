package com.example.demo.tictactoe;

import com.example.demo.tictactoe.model.*;
import org.antlr.v4.runtime.misc.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    Deque<Player> players;
    Board gameBoard;

    public TicTacToeGame() {

    }

    public void initializeGame() {
        players = new LinkedList<>();
        PlayingPieceX crossPiece = new PlayingPieceX();
        Player player1 = new Player(crossPiece, "Player 1");

        PlayingPieceO noughtsPiece = new PlayingPieceO();
        Player player2 = new Player(noughtsPiece, "Player 2");

        players.add(player1);
        players.add(player2);

        gameBoard = new Board(3);

    }

    public String startGame() {
        boolean noWinner = true;
        while (noWinner) {

            Player playerTurn = players.removeFirst();

            gameBoard.printBoard();
            List<Pair<Integer, Integer>> freeSpaces = gameBoard.getFreeCells();
            if (freeSpaces.isEmpty()) {
                noWinner = false;
                continue;
            }

            System.out.println("Player:" + playerTurn.getName() + " Enter row,column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow, inputColumn, playerTurn.getPlayingPiece());
            if (!pieceAddedSuccessfully) {
                System.out.println("Incorrect position chosen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);
            boolean winner = isThereWinner(inputRow, inputColumn, playerTurn.getPlayingPiece().pieceType);
            if (winner) {
                return playerTurn.getName();
            }

        }
        return "tie";
    }

    public boolean isThereWinner(int row, int column, PieceType pieceType) {
        boolean columnMatch = true;
        boolean rowMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        for (int i = 0; i < gameBoard.getSize(); i++) {
            if (gameBoard.getBoard()[row][i] == null || gameBoard.getBoard()[row][i].pieceType != pieceType) {
                rowMatch = false;
            }
        }
        for (int i = 0; i < gameBoard.getSize(); i++) {
            if (gameBoard.getBoard()[i][column] == null || gameBoard.getBoard()[i][column].pieceType != pieceType) {
                columnMatch = false;
            }
        }
        for (int i = 0, j = 0; i < gameBoard.getSize(); i++, j++) {
            if (gameBoard.getBoard()[i][j] == null || gameBoard.getBoard()[i][j].pieceType != pieceType) {
                diagonalMatch = false;
            }
        }
        for (int i = 0, j = gameBoard.getSize() - 1; i < gameBoard.getSize(); i++, j--) {
            if (gameBoard.getBoard()[i][j] == null || gameBoard.getBoard()[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
            }
        }
        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }
}
