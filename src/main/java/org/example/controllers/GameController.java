package org.example.controllers;

import org.example.Models.Game;
import org.example.Models.GameStatus;
import org.example.Models.Player;
import org.example.strategies.winningstrategy.WinningStrategy;

import java.util.List;

public class GameController {
    public Game createGame(int dimension, List<Player> players, List<WinningStrategy> winningStrategies){
        return Game.getBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }
    public void displayBoard(Game game){
        game.printBoard();
    }
    public void undo(Game game){
        game.undo();
    }
    public void makeMove(Game game){
        game.makeMove();
    }
    public GameStatus getGameStatus(Game game) {
        return game.getGameStatus();
    }
    public void printResult(Game game) {
        game.printResult();
    }
}
