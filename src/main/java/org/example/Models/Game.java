package org.example.Models;

import org.example.strategies.winningstrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameStatus gameStatus;
    private int currentMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;
    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.moves = new ArrayList<>();
        this.board = new Board(dimension);
        this.players = players;
        this.currentMovePlayerIndex = 0;
        this.winningStrategies = winningStrategies;
        this.gameStatus = GameStatus.IN_PROGRESS;
    }
    public static Builder getBuilder(){
        return new Builder();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameState(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getNextMovePlayerIndex() {
        return currentMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.currentMovePlayerIndex = nextMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
    public void printResult() {
        if (gameStatus.equals(GameStatus.ENDED)) {
            System.out.println("Game has ended.");
            System.out.println("Winner is: " + winner.getName());
        } else {
            System.out.println("Game is draw");
        }
    }
    public void printBoard() {
        this.board.print();
    }
    public void undo(){
        if(moves.isEmpty()){
            System.out.println("invalid undo");
            return;
        }
        Move lastMove=moves.get(moves.size()-1);
        Cell cellInBoard = lastMove.getCell();
        cellInBoard.setCellState(CellState.EMPTY);
        moves.remove(lastMove);
        currentMovePlayerIndex--;
        currentMovePlayerIndex=currentMovePlayerIndex+players.size();
        currentMovePlayerIndex %=players.size();
        for (WinningStrategy winningStrategy:winningStrategies){
            winningStrategy.handleUndo(board,lastMove);
        }
        System.out.println("Move undo completed");
    }
    private boolean validateMove(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();

        if (row < 0 || col < 0 || row >= board.getSize() || col >= board.getSize()) {
            return false;
        }

        return board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY);
    }
    public void makeMove(){
        Player currentPlayer=players.get(currentMovePlayerIndex);
        Cell proposedCell=currentPlayer.makeMove(board);
        if(!validateMove(proposedCell)) {
            return;
        }
        Cell cellInBoard = board.getBoard().get(proposedCell.getRow()).get(proposedCell.getCol());
        cellInBoard.setCellState(CellState.FILLED);
        cellInBoard.setPlayer(currentPlayer);
        Move move=new Move(currentPlayer,cellInBoard);
        moves.add(move);
        if(checkGameWon(currentPlayer,move)) return;
        if(checkDraw()) return;
        currentMovePlayerIndex++;
        currentMovePlayerIndex %=players.size();
    }
    private boolean checkGameWon(Player currentPlayer,Move move){
        for(WinningStrategy winningStrategy:winningStrategies){
            if(winningStrategy.checkWinner(board,move)){
                gameStatus=GameStatus.ENDED;
                winner=currentPlayer;
                return true;
            }
        }
        return false;
    }
    private boolean checkDraw(){
        if(moves.size()== board.getSize()*board.getSize()){
            gameStatus=GameStatus.DRAW;
            return true;
        }
        return false;
    }
    public static class Builder{
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimension;
        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }
        private boolean valid(){
            if (this.players.size()<2) return false;
            if (this.players.size() != this.dimension - 1) {
                return false;
            }
            int botCount = 0;
            for (Player player: this.players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) {
                    botCount += 1;
                }
            }
            if (botCount >= 2) {
                return false;
            }

            HashSet<Character> existingSymbols=new HashSet<>();
            for(Player player:players){
                if(existingSymbols.contains(player.getSymbol().getaChar())){
                    return false;
                }
                existingSymbols.add(player.getSymbol().getaChar());
            }
            return true;
        }
        public Game build(){
            if(!valid()) {
                throw new RuntimeException("Invalid params for game");
            }
            return new Game(dimension,players,winningStrategies);
        }
    }
}
