package com.example.demo.tictactoe.model;


import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;


public class Board {
    int size;
    PlayingPiece[][] board;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PlayingPiece[][] getBoard() {
        return board;
    }

    public void setBoard(PlayingPiece[][] board) {
        this.board = board;
    }

    public Board(int size) {
        this.size = size;
        this.board = new PlayingPiece[size][size];
    }

    public boolean addPiece(int n, int m, PlayingPiece playingPiece){
        if(board[n][m]!=null)
            return false;
        board[n][m]=playingPiece;
        return true;

    }
    public List<Pair<Integer,Integer>> getFreeCells(){
        List<Pair<Integer,Integer>> freeCells=new ArrayList<>();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j]==null){
                    Pair<Integer,Integer> rowColumn=new Pair<>(i,j);
                    freeCells.add(rowColumn);
                }
            }
        }
        return freeCells;
    }
    public void printBoard() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].pieceType.name() + "   ");
                } else {
                    System.out.print("    ");

                }
                System.out.print(" | ");
            }
            System.out.println();

        }
    }
}
