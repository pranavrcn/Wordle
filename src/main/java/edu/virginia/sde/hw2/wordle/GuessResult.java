package edu.virginia.sde.hw2.wordle;

import java.util.Arrays;

import static edu.virginia.sde.hw2.wordle.LetterResult.*;

/**
 * This class handles getting the result from a guess in a Wordle Game. This class is used by
 * {@link Game#submitGuess(String)}.
 */
public class GuessResult {
    private final String guess;
    private final String answer;

    /**
     * Constructor for GuessResult
     * @param guess - the Wordle player's guessed word
     * @param answer - the word the player is trying to guess
     * @throws IllegalArgumentException if either word is not 5-characters long.
     */
    public GuessResult(String guess, String answer) {
        validateWordLengths(guess, answer);
        this.guess = guess;
        this.answer = answer;
    }

    private static void validateWordLengths(String guess, String answer) {
        if (guess.length() != WordValidator.WORDLE_WORD_LENGTH || answer.length() != WordValidator.WORDLE_WORD_LENGTH) {
            throw new IllegalArgumentException(String.format("""
                            Invalid GuessResult initialization:
                                guess: %s
                                answer: %s
                            Words must be %d letters long.""",
                    guess, answer, WordValidator.WORDLE_WORD_LENGTH));
        }
    }

    /**
     * Returns the guess submitted by the player.
     */
    public String getGuess() {
        return guess;
    }

    /**
     * Returns the answer the player is trying to guess.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns true if the player's guess matches the answer (case-insensitive)
     */
    public boolean isCorrect() {
        return guess.equalsIgnoreCase(answer);
    }

    /**
     * Returns the {@link LetterResult} array of GREEN, YELLOW, and GRAY based on how well the player's guess. This
     * function is case-insensitive.
     */
    public LetterResult[] getLetterResults() {
        LetterResult[] results = new LetterResult[WordValidator.WORDLE_WORD_LENGTH];
        boolean[] matched = new boolean[WordValidator.WORDLE_WORD_LENGTH];


        for (int i = 0; i < guess.length(); i++) { // For each letter in the five wordle guess letters
            if (Character.toUpperCase(guess.charAt(i)) == Character.toUpperCase(answer.charAt(i))) { // If the guess character is equivalent and in the same position as answer character
                results[i] = GREEN; // Set that letters result to green
                matched[i] = true; // Set the matched state of that letter to true
            }
        }

        for (int i = 0; i < guess.length(); i++) { // For each letter in the five wordle guess letters
            if (results[i] != GREEN) { // If the result isn't already set as green
                boolean found = false; // Initialize a found variable setting it to false
                for (int j = 0; j < answer.length(); j++) { // Iterate through the answer
                    if (Character.toUpperCase(guess.charAt(i)) == Character.toUpperCase(answer.charAt(j)) && !matched[j]) { // If the guess letter is equivalent to any letter in the answer that isn't already matched/found
                        results[i] = YELLOW; // Set that letters result to yellow
                        matched[j] = true; // Set that it has been partially matched
                        found = true; // Set that it has been found
                        break;
                    }
                }
                if (!found) { // If any character isn't found in the answer
                    results[i] = GRAY; // Set that letters result to gray
                }
            }
        }
        return results;
    }

}
