package edu.virginia.sde.hw2.wordle;

public class IllegalWordException extends IllegalArgumentException {
    public IllegalWordException(String message) {
        super(message);
    }
}
