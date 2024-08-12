package edu.virginia.sde.hw2.wordle;

/**
 * This enum describes the current state of the World game, either {@link GameStatus#PLAYING},
 * {@link GameStatus#WIN}, or {@link GameStatus#LOSS},
 */
public enum GameStatus {
    /**
     * The game is ongoing
     */
    PLAYING,
    /**
     * The game has ended in a loss
     */
    LOSS,
    /**
     * The game has ended in a win
     */
    WIN
}
