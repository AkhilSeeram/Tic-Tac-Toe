package org.example.strategies.botplayingstrategy;

import org.example.Models.BotDifficultyLevel;

public class BotPlayingStrategyFactor {
    public static BotPlayingStrategy getBotPlayingStrategyForDifficultyLevel(BotDifficultyLevel difficultyLevel) {

        return new EasyBotPlayingStrategy();
        //    return switch (difficultyLevel) {
//            case EASY -> new EasyBotPlayingStrategy();
//            case MEDIUM -> new MediumBotPlayingStrategy();
//            case HARD -> new HardBotPlayingStrategy();
//        };
    }
}
