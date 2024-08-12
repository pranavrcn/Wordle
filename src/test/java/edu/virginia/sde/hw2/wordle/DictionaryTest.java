package edu.virginia.sde.hw2.wordle;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    @Test
    void getWordSet() {
        var startingWordSet = new HashSet<>(Set.of("apple", "black", "camel"));
        var dictionary = new Dictionary(startingWordSet, new WordValidator());

        var wordSet = dictionary.getWordSet();
        assertEquals(3, wordSet.size());
        assertTrue(wordSet.contains("apple"));
        assertTrue(wordSet.contains("black"));
        assertTrue(wordSet.contains("camel"));
    }

    @Test
    void invalidWordSet() {
        var startingWordSet = new HashSet<>(Set.of("apples", "black", "camel"));
        assertThrows(IllegalArgumentException.class, () -> {new Dictionary(startingWordSet, new WordValidator());});
    }
    @Test
    void invalidWordSet_case() {
        var startingWordSet = new HashSet<>(Set.of("aPPle", "black", "camel"));
        assertThrows(IllegalArgumentException.class, () -> {new Dictionary(startingWordSet, new WordValidator());});
    }

    @Test
    void getWordSet_initiallyEmpty() {
        var dictionary = new Dictionary();

        var wordSet = dictionary.getWordSet();
        assertTrue(wordSet.isEmpty());
    }

    @Test
    void addWord_validWord() {
        var dictionary = new Dictionary();
        dictionary.addWord("party");
        assertTrue(dictionary.contains("party"));
    }

    @Test
    void addWord_invalidLength() {
        var dictionary = new Dictionary();
        assertThrows(IllegalArgumentException.class, () -> dictionary.addWord("invalidword"));
    }
    @Test
    void addWord_invalidCharacter() {
        var dictionary = new Dictionary();
        assertThrows(IllegalArgumentException.class, () -> dictionary.addWord("p6rty"));
    }
    @Test
    void contains_caseInsensitive() {
        var dictionary = new Dictionary();
        dictionary.addWord("Party");
        assertTrue(dictionary.contains("party"));
    }
    @Test
    void contains_NonExistingWord() {
        var dictionary = new Dictionary();
        assertFalse(dictionary.contains("nonexisting"));
    }

    @Test
    void size_emptyDictionary() {
        var dictionary = new Dictionary();
        assertEquals(0, dictionary.size());
    }
    @Test
    void size_afterAddingWords() {
        var dictionary = new Dictionary();
        dictionary.addWord("party");
        dictionary.addWord("green");
        assertEquals(2, dictionary.size());
    }
    @Test
    void isEmpty_emptyDictionary() {
        var dictionary = new Dictionary();
        assertTrue(dictionary.isEmpty());
    }
    @Test
    void isEmpty_afterAddingWords() {
        var dictionary = new Dictionary();
        dictionary.addWord("party");
        dictionary.addWord("green");
        assertFalse(dictionary.isEmpty());
    }
    @Test
    void equals_sameWords() {
        var dict1 = new Dictionary();
        dict1.addWord("party");
        var dict2 = new Dictionary();
        dict2.addWord("party");
        assertEquals(dict1, dict2);
    }
    @Test
    void equals_differentWords() {
        var dict1 = new Dictionary();
        dict1.addWord("party");
        var dict2 = new Dictionary();
        dict2.addWord("green");
        assertNotEquals(dict1, dict2);
    }
    @Test
    void equals_differentObjects() {
        var dictionary = new Dictionary();
        var Chair = new Object();
        assertNotEquals(dictionary, Chair);
    }
}

