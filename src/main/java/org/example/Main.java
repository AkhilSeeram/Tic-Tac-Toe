package org.example;


import org.example.Models.*;
import org.example.controllers.GameController;
import org.example.strategies.winningstrategy.OrderOneColumnWinningStrategy;
import org.example.strategies.winningstrategy.OrderOneDiagonalWinningStrategy;
import org.example.strategies.winningstrategy.OrderOneRowWinningStrategy;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameController gameController=new GameController();
        Scanner scanner=new Scanner(System.in);
        int dimension=3;
        Game game;
        List<Player> players=List.of(
                new Player(new Symbol('X'),"Akhil", PlayerType.HUMAN),
                new Player(new Symbol('O'),"Rakshit", PlayerType.HUMAN));
        try{
            game=gameController.createGame(
                    dimension,
                    players,
                    List.of(
                            new OrderOneColumnWinningStrategy(dimension,players),
                            new OrderOneDiagonalWinningStrategy(players),
                            new OrderOneRowWinningStrategy(dimension,players)
                    )
            );
        }
        catch (Exception e){
            System.out.println("Something went wrong");
            return;
        }
        System.out.println("---------Game is getting started--------");
        while (gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)){
            gameController.displayBoard(game);
            System.out.println("Do you want to undo? (y/n)");
            String input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                gameController.undo(game);
            } else {
                // move next player
                gameController.makeMove(game);
            }
        }
        gameController.printResult(game);
    }
}