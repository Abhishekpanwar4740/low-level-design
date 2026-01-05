package com.example.demo.snakeNladder;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Board {
    Cell[][] cells;

    public Board(int boardSize, int numberOfSnakes, int numberOfLadders) {
        initializeBoard(boardSize);
        addSnakesAndLadders(numberOfSnakes, numberOfLadders);
    }

    public void initializeBoard(int boardSize) {
        cells = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void addSnakesAndLadders(int numberOfSnakes, int numberOfLadders) {
        while (numberOfSnakes > 0) {
            int snakeHead = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length - 1);
            int snakeTail = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length - 1);

            int tail = min(snakeHead, snakeTail);
            int head = max(snakeTail, snakeHead);

            snakeTail = tail;
            snakeHead = head;

            Jump jump = new Jump(snakeHead, snakeTail);
            Cell cell = getCell(snakeHead);
            cell.jump = jump;
            numberOfSnakes--;

        }
        while (numberOfLadders > 0) {
            int ladderHead = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length - 1);
            int ladderTail = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length - 1);

            int tail = max(ladderHead, ladderTail);
            int head = min(ladderTail, ladderHead);

            ladderTail = tail;
            ladderHead = head;

            Jump jump = new Jump(ladderHead, ladderTail);
            Cell cell = getCell(ladderTail);
            cell.jump = jump;
            numberOfLadders--;
        }
    }

    public Cell getCell(int pos) {
        int row = pos / cells.length;
        int col = pos % cells.length;
        return cells[row][col];
    }
}
