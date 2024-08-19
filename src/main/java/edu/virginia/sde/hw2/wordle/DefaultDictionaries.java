package edu.virginia.sde.hw2.wordle;

import java.io.IOException;
import java.io.InputStream;
public class DefaultDictionaries {

    public static final String GUESSES_DICTIONARY_FILENAME = "default_dictionaries/wordle_guesses.txt";

    public static final String ANSWERS_DICTIONARY_FILENAME = "default_dictionaries/wordle_answers.txt";

    private static Dictionary guessesDictionaryMemo;
    private static Dictionary answersDictionaryMemo;

    public static Dictionary getGuessesDictionary() {
        if (dictionaryNotInitialized(guessesDictionaryMemo)) {
            guessesDictionaryMemo = retrieveDictionaryFromFilename(GUESSES_DICTIONARY_FILENAME);
        }
        return guessesDictionaryMemo;
    }

   
    public static Dictionary getAnswersDictionary() {
        if (dictionaryNotInitialized(answersDictionaryMemo)) {
            answersDictionaryMemo = retrieveDictionaryFromFilename(ANSWERS_DICTIONARY_FILENAME);
        }
        return answersDictionaryMemo;
    }

    private static boolean dictionaryNotInitialized(Dictionary dictionaryMemo) {
        return dictionaryMemo == null;
    }

    private static Dictionary retrieveDictionaryFromFilename(String guessesDictionaryFilename) {
        try (var inputStream = getInputStreamForResource(guessesDictionaryFilename)) {
            var dictionary = new Dictionary();
            dictionary.addWordsFromInputStream(inputStream);
            return dictionary;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getInputStreamForResource(String filename) {
        var classLoader = DefaultDictionaries.class.getClassLoader();
        return classLoader.getResourceAsStream(filename);
    }


}
