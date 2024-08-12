package edu.virginia.sde.hw2.wordle;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class represents a dictionary of valid words in Wordle. All words in this dictionary are 5 letters long (see The
 * {@link WordValidator#WORDLE_WORD_LENGTH}). The dictionary is case-insensitive. This is handled by storing all words
 * in the dictionary as lowercase only, and normalizing all method inputs to lowercase.
 */
public class Dictionary {

    /**
     * The set of words in the dictionary
     */
    private final Set<String> wordSet;

    /**
     * Determines what words are allowed in the dictionary. See {@link WordValidator#isValidWord(String)}
     */
    private final WordValidator wordValidator;

    /**
     * Creates a new empty Dictionary
     */
    public Dictionary() {
        this(new HashSet<>(), new WordValidator());
    }

    /**
     * This constructor is used for injection testing. Be aware that when invoking with a non-empty set, you should
     * ensure that all members of the set are lower-case, and that the set object is mutable.
     */

    protected Dictionary(Set<String> wordSet, WordValidator wordValidator) {
        validateWordSet(wordSet, wordValidator);
        this.wordSet = wordSet;
        this.wordValidator = wordValidator;
    }

    private void validateWordSet(Set<String> wordSet, WordValidator wordValidator) {
        for (String word: wordSet) {
            if (!wordValidator.isValidWord(word)) {
                throw new IllegalArgumentException(
                        String.format("Invalid word in dictionary: %s", word));
            }
            var lowerCaseWord = word.toLowerCase();
            if (!word.equals(lowerCaseWord)) {
                throw new IllegalArgumentException(
                        String.format("All words in a dictionary must be lowercase. Invalid word: %s", word));
            }
        }
    }


    /**
     * Returns an immutable set of words in the dictionary
     */
    public Set<String> getWordSet() {
        return Collections.unmodifiableSet(wordSet);
    }

    /**
     * Returns the number of words in the dictionary
     */
    public int size() {
        return wordSet.size();
    }

    /**
     * Returns true if the dictionary has no words yet.
     */
    public boolean isEmpty() {
        return wordSet.isEmpty();
    }

    /**
     * Returns true if the word exists in the dictionary, otherwise false. This function is case-insensitive
     */
    public boolean contains(String word) {
        return wordSet.contains(word.toLowerCase());
    }

    /**
     * Add a word to the dictionary.
     *
     * @throws IllegalArgumentException if the word is not valid (see {@link WordValidator#isValidWord(String)}
     */
    public void addWord(String word) {
        word = word.toLowerCase();
        if (!wordValidator.isValidWord(word)) {
            throw new IllegalArgumentException(
                    String.format("Cannot add %s to dictionary, as it is invalid for Wordle", word));
        }
        wordSet.add(word);
    }


    /**
     * Add words from a plain text file where each line contains one word. Example: <br>
     * <TT>&nbsp;&nbsp;a <br>
     * &nbsp;&nbsp;aardvark <br>
     * &nbsp;&nbsp;abacus <br>
     * &nbsp;&nbsp;abash <br>
     * &nbsp;&nbsp;about <br> </TT>
     * etc. <br>
     * Only valid words are added (see {@link WordValidator#isValidWord(String)}). Invalid words are skipped.
     *
     * @throws FileNotFoundException if file is null or doesn't exist
     * @throws IOException           for other IO errors when reading the file
     */
    public void addWordsFromInputStream(InputStream inputStream) throws IOException {
        try (var inputStreamReader = new InputStreamReader(inputStream);
             var bufferedReader = new BufferedReader(inputStreamReader)) {
            for (var line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                var word = line.strip();
                if (wordValidator.isValidWord(word)) {
                    addWord(word);
                }
            }
        }
    }

    /**
     * Returns a random word from the dictionary
     */
    public String getRandomWord(Random random) {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get random word from empty dictionary.");
        }
        var wordNumber = random.nextInt(0, wordSet.size());
        var wordSetIterator = wordSet.iterator();
        for (int i = 0; i < wordNumber; i++) {
            wordSetIterator.next();
        }
        return wordSetIterator.next();
    }

    /**
     * Returns true if both dictionaries have the same set of words.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dictionary otherDictionary)) {
            return false;
        }
        return wordSet.equals(otherDictionary.wordSet);
    }


}
