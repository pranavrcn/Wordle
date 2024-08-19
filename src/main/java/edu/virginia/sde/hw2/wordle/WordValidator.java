package edu.virginia.sde.hw2.wordle;


public class WordValidator {

    public static final int WORDLE_WORD_LENGTH = 5;


    public boolean isValidWord(String word) {
        return isCorrectLength(word) && isAllLetters(word);
    }

    protected boolean isCorrectLength(String word) {
        return word.length() == WORDLE_WORD_LENGTH;
    }

    protected boolean isAllLetters(String word) {
        word = word.toLowerCase();
        for (int index = 0; index < word.length(); index++) {
            var character = word.charAt(index);
            if (character < 'a' || character > 'z') {
                return false;
            }
        }
        return true;
    }
}
