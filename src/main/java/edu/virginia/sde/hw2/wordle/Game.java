package edu.virginia.sde.hw2.wordle;

import java.util.Random;

import static edu.virginia.sde.hw2.wordle.GameStatus.*;

public class Game {


    public static final int STARTING_GUESSES = 6;
    private final Dictionary guessDictionary;
    private final String answer;
    private int guessesRemaining;
    private GameStatus gameStatus;
    private static final Random random = new Random();


    public Game() {
        this(DefaultDictionaries.getGuessesDictionary(),
                DefaultDictionaries.getAnswersDictionary().getRandomWord(random),
                STARTING_GUESSES,
                PLAYING);
    }


    protected Game(Dictionary guessDictionary, String answer, int guessesRemaining, GameStatus gameStatus) {
        validate(guessDictionary, answer, guessesRemaining, gameStatus);

        this.guessDictionary = guessDictionary;
        this.answer = answer;
        this.guessesRemaining = guessesRemaining;
        this.gameStatus = gameStatus;
    }

    public Dictionary getGuessDictionary() {
        return guessDictionary;
    }


    public String getAnswer() {
        return answer;
    }

 
    public int getGuessesRemaining() {
        return guessesRemaining;
    }


    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean isGameOver() {
        return gameStatus != PLAYING;
    }


    public GuessResult submitGuess(String guess) {
        if (isGameOver()) {
            throw new GameAlreadyOverException("Game is already over");
        }
        if (!guessDictionary.contains(guess)) {
            throw new IllegalWordException("Invalid word: " + guess);
        }
        guessesRemaining--;
        if (guess.equalsIgnoreCase(answer)) {
            gameStatus = GameStatus.WIN;
            return new GuessResult(guess, answer);
        }
        else {
            if (guessesRemaining == 0) {
                gameStatus = GameStatus.LOSS;
            }
            return new GuessResult(guess, answer);
        }


    }

    private static void validate(Dictionary guessDictionary, String answer, int guessesRemaining, GameStatus gameStatus) {
        validateGuessDictionary(guessDictionary);
        validateAnswer(answer, guessDictionary);
        validateGuessesRemaining(guessesRemaining);
        validateGameStatus(gameStatus, guessesRemaining);
    }

    private static void validateGuessDictionary(Dictionary guessDictionary) {
        if (guessDictionary.isEmpty()) {
            throw new IllegalArgumentException("Cannot create a Game with an empty guessDictionary");
        }
    }

    private static void validateAnswer(String answer, Dictionary guessDictionary) {
        if (answer == null) {
            throw new IllegalArgumentException("Answer cannot be null");
        }
        if (!guessDictionary.contains(answer)) {
            throw new IllegalArgumentException(String.format(
                    "Answer %s is not in the provided Guess Dictionary", answer));
        }
    }

    private static void validateGuessesRemaining(int guessesRemaining) {
        if (guessesRemaining < 0) {
            throw new IllegalArgumentException("The starting guessesRemaining value must be non-negative");
        }
    }

    private static void validateGameStatus(GameStatus gameStatus, int guessesRemaining) {
        if (gameStatus == null) {
            throw new IllegalArgumentException("gameStatus cannot be null");
        }
        if (guessesRemaining > 0 && gameStatus == LOSS) {
            throw new IllegalArgumentException("The game cannot be in a LOSS state with more than zero guesses remaining");
        }
        if (guessesRemaining == 0 && gameStatus == PLAYING) {
            throw new IllegalArgumentException("The game cannot be in a PLAYING state with zero guesses remaining");
        }
    }
}
