package edu.virginia.sde.hw2.wordle;
import org.junit.jupiter.api.Test;

import static edu.virginia.sde.hw2.wordle.LetterResult.GREEN;
import static edu.virginia.sde.hw2.wordle.LetterResult.YELLOW;
import static edu.virginia.sde.hw2.wordle.LetterResult.GRAY;
import static org.junit.jupiter.api.Assertions.*;

public class GuessResultTest {
    @Test
    public void testCorrectGuess() {
        GuessResult result = new GuessResult("apple", "apple");
        assertTrue(result.isCorrect());
    }
    @Test
    public void testWrongGuess() {
        GuessResult result = new GuessResult("party", "apple");
        assertFalse(result.isCorrect());
    }
    @Test
    void testGetLetterResults_AllCorrect() {
        GuessResult result = new GuessResult("apple", "apple");
        LetterResult[] expected = {GREEN, GREEN, GREEN, GREEN, GREEN};
        assertArrayEquals(expected, result.getLetterResults());
    }
    @Test
    void testGetLetterResults_AllWrong() {
        GuessResult result = new GuessResult("party", "slice");
        LetterResult[] expected = {GRAY, GRAY, GRAY, GRAY, GRAY};
        assertArrayEquals(expected, result.getLetterResults());
    }
    @Test
    void testGetLetterResults_Mixed() {
        GuessResult result = new GuessResult("brain", "basic");
        LetterResult[] expected = {GREEN, GRAY, YELLOW, GREEN, GRAY};
        assertArrayEquals(expected, result.getLetterResults());
    }

    @Test
    void testGetLetterResults_MixedRepeating() {
        GuessResult result = new GuessResult("green", "prone");
        LetterResult[] expected = {GRAY, GREEN, YELLOW, GRAY, YELLOW};
        assertArrayEquals(expected, result.getLetterResults());
    }
    @Test
    void testGetLetterResults_MixedRepeatingFlipped() {
        GuessResult result = new GuessResult("prone", "green");
        LetterResult[] expected = {GRAY, GREEN, GRAY, YELLOW, YELLOW};
        assertArrayEquals(expected, result.getLetterResults());
    }
    @Test
    void testGetLetterResults_caseInsensitive() {
        GuessResult result = new GuessResult("gReeN", "green");
        LetterResult[] expected = {GREEN, GREEN, GREEN, GREEN, GREEN};
        assertArrayEquals(expected, result.getLetterResults());
    }
    @Test
    public void testGetLetterResults_badArgumentLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GuessResult("greeen", "green");
        });
    }
    @Test
    public void testGetLetterResults_emptyGuess() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GuessResult("", "green");
        });
    }
    @Test
    void testGetLetterResults_doubleCharDiffWord() {
        GuessResult result = new GuessResult("spree", "feels");
        LetterResult[] expected = {YELLOW, GRAY, GRAY, YELLOW, YELLOW};
        assertArrayEquals(expected, result.getLetterResults());
    }

}
