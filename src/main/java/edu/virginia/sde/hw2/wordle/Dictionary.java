package edu.virginia.sde.hw2.wordle;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Dictionary {

    private final Set<String> wordSet;
    private final WordValidator wordValidator;


    public Dictionary() {
        this(new HashSet<>(), new WordValidator());
    }
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



    public Set<String> getWordSet() {
        return Collections.unmodifiableSet(wordSet);
    }

    public int size() {
        return wordSet.size();
    }

 
    public boolean isEmpty() {
        return wordSet.isEmpty();
    }

 
    public boolean contains(String word) {
        return wordSet.contains(word.toLowerCase());
    }

 
    public void addWord(String word) {
        word = word.toLowerCase();
        if (!wordValidator.isValidWord(word)) {
            throw new IllegalArgumentException(
                    String.format("Cannot add %s to dictionary, as it is invalid for Wordle", word));
        }
        wordSet.add(word);
    }


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

  
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dictionary otherDictionary)) {
            return false;
        }
        return wordSet.equals(otherDictionary.wordSet);
    }


}
