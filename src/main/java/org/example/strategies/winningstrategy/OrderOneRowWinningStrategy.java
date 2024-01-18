package org.example.strategies.winningstrategy;

import org.example.Models.Board;
import org.example.Models.Move;
import org.example.Models.Player;
import org.example.Models.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneRowWinningStrategy implements WinningStrategy{
    private List<Map<Symbol,Integer>> rowmaps;
    public OrderOneRowWinningStrategy(int dimension, List<Player> players){
        rowmaps=new ArrayList<>();
        for(int i=0;i<dimension;i++){
            rowmaps.add(new HashMap<>());
        }
        for(Map<Symbol,Integer> rowmap:rowmaps){
            for(Player player:players){
                rowmap.put(player.getSymbol(),0);
            }
        }
    }
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row=move.getCell().getRow();
        rowmaps.get(row).put(move.getPlayer().getSymbol(),1+rowmaps.get(row).get(move.getPlayer().getSymbol()));
        if(rowmaps.get(row).get(move.getPlayer().getSymbol())==board.getSize()){
            return true;
        }
        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row=move.getCell().getRow();
        rowmaps.get(row).put(move.getPlayer().getSymbol(),rowmaps.get(row).get(move.getPlayer().getSymbol())-1);
    }

}
