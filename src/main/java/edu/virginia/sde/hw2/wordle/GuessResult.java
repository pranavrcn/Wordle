package edu.virginia.sde.hw2.wordle;

import java.util.Arrays;

import static edu.virginia.sde.hw2.wordle.LetterResult.*;

public class GuessResult {
    private final String guess;
    private final String answer;

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

    public String getGuess() {
        return guess;
    }


    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return guess.equalsIgnoreCase(answer);
    }


    public LetterResult[] getLetterResults() {
        LetterResult[] results = new LetterResult[WordValidator.WORDLE_WORD_LENGTH];
        boolean[] matched = new boolean[WordValidator.WORDLE_WORD_LENGTH];


        for (int i = 0; i < guess.length(); i++) {
            if (Character.toUpperCase(guess.charAt(i)) == Character.toUpperCase(answer.charAt(i))) { 
                results[i] = GREEN; 
                matched[i] = true; 
            }
        }

        for (int i = 0; i < guess.length(); i++) { 
            if (results[i] != GREEN) { 
                boolean found = false; 
                for (int j = 0; j < answer.length(); j++) { 
                    if (Character.toUpperCase(guess.charAt(i)) == Character.toUpperCase(answer.charAt(j)) && !matched[j]) { 
                        results[i] = YELLOW; 
                        matched[j] = true; 
                        found = true; 
                        break;
                    }
                }
                if (!found) { 
                    results[i] = GRAY; 
                }
            }
        }
        return results;
    }

}
