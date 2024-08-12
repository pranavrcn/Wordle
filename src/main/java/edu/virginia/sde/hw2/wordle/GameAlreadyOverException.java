package edu.virginia.sde.hw2.wordle;

public class GameAlreadyOverException extends IllegalStateException {
    public GameAlreadyOverException(String message) {
        super(message);
    }
}
