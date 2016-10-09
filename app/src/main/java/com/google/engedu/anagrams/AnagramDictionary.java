package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


//Extensions Applied:
  //->Two-letter mode: switch to allowing the user to add two letters to form anagrams

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static int wordLength = DEFAULT_WORD_LENGTH;

    private Random random = new Random();


    ArrayList<String> wordList = new ArrayList<>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordList.add(word);

            ArrayList<String> sortedList = new ArrayList<>();
            String sortedWord = sortLetters(word);

            // Populate the lettersToWord HashMap
            if ( !(lettersToWord.containsKey(sortedWord)) ) {
                sortedList.add(word);
                lettersToWord.put(sortedWord, sortedList);
            } else {
                sortedList = lettersToWord.get(sortedWord);
                sortedList.add(word);
                lettersToWord.put(sortedWord, sortedList);
            }
        }
    }


    public boolean isGoodWord(String word, String base) {
            return (wordList.contains(word) && !(base.contains(word)));
    }

    public ArrayList<String> getAnagrams(String word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<>();

               // String anagram = word;
                String sortedAnagram = sortLetters(word);

                if (lettersToWord.containsKey(sortedAnagram)) {
                    tempList = lettersToWord.get(sortedAnagram);

                    for (int i = 0; i < tempList.size(); i++) {
                        if (!(tempList.get(i).contains(word))) {
                            resultList.add(tempList.get(i));
                        }
                    }
                }


        return resultList;
    }
    public String sortLetters(String word) {
        char[] characters = word.toCharArray();
        Arrays.sort(characters);
        String sortedWord = new String(characters);
        return sortedWord;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> tempList;
        for(char alphabet = 'a';alphabet <= 'z'; alphabet++) {
            String anagram = word + alphabet;
            String sortedAnagram = sortLetters(anagram);
            if (lettersToWord.containsKey(sortedAnagram)) {
                tempList = lettersToWord.get(sortedAnagram);

                for (int i = 0; i < tempList.size(); i++) {
                    if (!(tempList.get(i).contains(word))) {
                        result.add(tempList.get(i));
                    }
                }
            }


        }
        return result;
    }

    public ArrayList<String> getAnagramsWithTwoMoreLetters(String word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<>();

        for (char firstAlphabet = 'a'; firstAlphabet <= 'z'; firstAlphabet++) {
            for (char secondAlphabet = 'a'; secondAlphabet <= 'z'; secondAlphabet++) {
                String anagram = word + firstAlphabet + secondAlphabet;
                String sortedAnagram = sortLetters(anagram);

                if (lettersToWord.containsKey(sortedAnagram)) {
                    tempList = lettersToWord.get(sortedAnagram);

                    for (int i = 0; i < tempList.size(); i++) {
                        if (!(tempList.get(i).contains(word))) {
                            resultList.add(tempList.get(i));
                        }
                    }
                }
            }
        }

        return resultList;
    }

    public String pickGoodStarterWord() {

        int randomNumber;
        String starterWord;

        do {
            randomNumber = random.nextInt(wordList.size());
            starterWord = wordList.get(randomNumber);
        } while (getAnagrams(starterWord).size() < MIN_NUM_ANAGRAMS);

        if (wordLength < MAX_WORD_LENGTH) {
            wordLength++;
        }

        return starterWord;
    }
}
