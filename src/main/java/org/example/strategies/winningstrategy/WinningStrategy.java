package org.example.strategies.winningstrategy;

import org.example.Models.Board;
import org.example.Models.Move;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move move);
    void handleUndo(Board board,Move move);
}
