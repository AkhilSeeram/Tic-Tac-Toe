package org.example.strategies.botplayingstrategy;

import org.example.Models.Board;
import org.example.Models.Cell;

public interface BotPlayingStrategy {
    Cell makeMove(Board board);
}
