package edu.virginia.sde.hw2.wordle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.virginia.sde.hw2.wordle.GameStatus.*;
import static edu.virginia.sde.hw2.wordle.LetterResult.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static Dictionary defaultGuessesDictionary, defaultAnswersDictionary;
    @BeforeAll
    public static void initialize() {
        defaultGuessesDictionary = DefaultDictionaries.getGuessesDictionary();
        defaultAnswersDictionary = DefaultDictionaries.getAnswersDictionary();
    }
    @Test
    public void test_init_zeroArgumentConstructor() {
        var game = new Game();

        assertEquals(defaultGuessesDictionary, game.getGuessDictionary());
        assertTrue(defaultAnswersDictionary.contains(game.getAnswer()));
        assertEquals(6, game.getGuessesRemaining());
        assertEquals(PLAYING, game.getGameStatus());
    }

    @Test
    public void test_init_4ArgumentConstructor() {
        var game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);

        assertEquals(defaultGuessesDictionary, game.getGuessDictionary());
        assertEquals("TREND", game.getAnswer());
        assertEquals(6, game.getGuessesRemaining());
        assertEquals(PLAYING, game.getGameStatus());
    }

    @Test
    public void test_isGameOver_WIN_True() {
        var game = new Game(defaultGuessesDictionary, "TREND", 5, WIN);

        assertEquals(WIN, game.getGameStatus());
        assertTrue(game.isGameOver());
    }
    @Test
    public void testSubmitValidGuess() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);
        int initialGuesses = game.getGuessesRemaining();
        game.submitGuess("APPLE"); // Assuming "APPLE" is a valid word in the dictionary
        assertEquals(initialGuesses - 1, game.getGuessesRemaining());
        assertEquals(PLAYING, game.getGameStatus());
    }
    @Test
    public void testSubmitInvalidLengthGuess() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);
        assertThrows(IllegalWordException.class, () -> game.submitGuess("INVALID"));

    }

    @Test
    public void testSubmitInvalidBlankGuess() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);
        assertThrows(IllegalWordException.class, () -> game.submitGuess(""));

    }



    @Test
    public void testSubmitInvalidWordGuess() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);
        assertThrows(IllegalWordException.class, () -> game.submitGuess("AAQAA"));

    }

    @Test
    public void testSubmitInvalidWordSpecialChar() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);
        assertThrows(IllegalWordException.class, () -> game.submitGuess("Te4st"));

    }

    @Test
    public void testSubmitGuessWhenGameWon() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 0, WIN);
        assertThrows(GameAlreadyOverException.class, () -> game.submitGuess("TREND"));
    }

    @Test
    public void testSubmitGuessWhenGameLost() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 0, LOSS);
        assertThrows(GameAlreadyOverException.class, () -> game.submitGuess("TREND"));
    }
    @Test
    public void testGameStatusUpdateToWin() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);
        game.submitGuess("TREND");
        assertEquals(WIN, game.getGameStatus());
    }
    @Test
    public void testGameStatusUpdateToWinMixedCasing() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 6, PLAYING);
        game.submitGuess("TreNd");
        assertEquals(WIN, game.getGameStatus());
    }

    @Test
    public void testGameStatusUpdateToLoss() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 1, PLAYING);
        game.submitGuess("party");
        assertEquals(LOSS, game.getGameStatus());
    }

    @Test
    public void testGameStatusUpdateOnCorrectLastGuess() {
        Game game = new Game(defaultGuessesDictionary, "TREND", 1, PLAYING);
        game.submitGuess("trend");
        assertEquals(WIN, game.getGameStatus());
    }

}