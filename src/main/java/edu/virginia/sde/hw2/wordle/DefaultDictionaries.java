package edu.virginia.sde.hw2.wordle;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides access to two default dictionaries for 5-letter Wordle. The guesses dictionary
 * {@link DefaultDictionaries#getGuessesDictionary()} provides the list of words that are acceptable guesses. The
 * answers dictionary {@link DefaultDictionaries#getAnswersDictionary()} provides a previously used list of words that
 * were intended to be Wordle answers through 2027. It's worth noting that as of 2022, the New York Times curates the
 * Wordle answer daily, and so it's possible that the New York Times will use a word not on this list. However, this was
 * the last publicly available list of answers (both past and present). <br>
 * <br>
 * These lists were provided by <a href="https://gist.github.com/cfreshman">cfreshman's gist page</a> on GitHub.
 */
public class DefaultDictionaries {
    /**
     * The resource location of the list of Guesses used by the NYTimes Wordle game
     */
    public static final String GUESSES_DICTIONARY_FILENAME = "default_dictionaries/wordle_guesses.txt";

    /**
     * The resource location of the last list of answers using by the NYTimes Wordle game
     */
    public static final String ANSWERS_DICTIONARY_FILENAME = "default_dictionaries/wordle_answers.txt";

    private static Dictionary guessesDictionaryMemo;
    private static Dictionary answersDictionaryMemo;

    /**
     * Returns a {@link Dictionary} containing the default list of allowed guesses in Wordle.
     */
    public static Dictionary getGuessesDictionary() {
        if (dictionaryNotInitialized(guessesDictionaryMemo)) {
            guessesDictionaryMemo = retrieveDictionaryFromFilename(GUESSES_DICTIONARY_FILENAME);
        }
        return guessesDictionaryMemo;
    }

    /**
     * Returns a {@link Dictionary} containing the default list of known answers in Wordle. Be aware that this list
     * is no longer used by the NYTimes, who curate their answers each day. This is, however, the last known set of
     * answers for Wordle before the change in November 2022.
     */
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
