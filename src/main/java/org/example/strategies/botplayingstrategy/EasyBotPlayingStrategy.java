package org.example.strategies.botplayingstrategy;

import org.example.Models.Board;
import org.example.Models.Cell;
import org.example.Models.CellState;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Cell makeMove(Board board) {
        for(List<Cell> row: board.getBoard()){
            for (Cell cell: row){
                if(cell.getCellState()== CellState.EMPTY) return cell;
            }
        }
        return null;
    }
}
