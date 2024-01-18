package org.example.Models;

import org.example.strategies.botplayingstrategy.BotPlayingStrategy;
import org.example.strategies.botplayingstrategy.BotPlayingStrategyFactor;

public class Bot extends Player {
    private BotDifficultyLevel botDifficultyLevel;
    public Bot(Symbol symbol, String name, BotDifficultyLevel botDifficultyLevel) {
        super(symbol, name, PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
    }
    @Override
    Cell makeMove(Board board) {
        BotPlayingStrategy botPlayingStrategy= BotPlayingStrategyFactor.getBotPlayingStrategyForDifficultyLevel(botDifficultyLevel);
        return botPlayingStrategy.makeMove(board);
    }
    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }

    public void setBotDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        this.botDifficultyLevel = botDifficultyLevel;
    }
}
