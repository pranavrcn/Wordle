package edu.virginia.sde.hw2.wordle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDictionariesTest {

    /**
     * Tests to ensure "default_dictionaries/wordle_guesses.txt" loads correctly
     */
    @Test
    void getGuessesDictionary() {
        var guessesDictionary = DefaultDictionaries.getGuessesDictionary();
        assertEquals(14855, guessesDictionary.size());
        assertTrue(guessesDictionary.contains("aahed"));
        assertTrue(guessesDictionary.contains("zymic"));
    }

    /**
     * Tests to ensure "default_dictionaries/wordle_answers.txt" loads correctly"
     */
    @Test
    void getAnswersDictionary() {
        var answersDictionary = DefaultDictionaries.getAnswersDictionary();
        assertEquals(2309, answersDictionary.size());
        assertTrue(answersDictionary.contains("aback"));
        assertTrue(answersDictionary.contains("zonal"));
    }

    /**
     * Ensures all words in the default answers dictionary would themselves be valid guesses.
     */
    @Test
    void ensureAllAnswersAreValidGuesses() {
        var answersDictionary = DefaultDictionaries.getAnswersDictionary();
        var guessesDictionary = DefaultDictionaries.getGuessesDictionary();
        for (String answer : answersDictionary.getWordSet()) {
            assertTrue(guessesDictionary.contains(answer));
        }
    }
}