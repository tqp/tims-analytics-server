package com.timsanalytics.main.thisApp.services;

import com.timsanalytics.main.thisApp.beans.KeyValueLong;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WordCountService {
    public List<KeyValueLong> getWordCountList() {
        // Note: The Function.identity() returns a function that returns it's input. Same as "return i -> i".
        int filterWordsWithLengthGreaterThan = 1;
        List<String> ignoreList = this.getIgnoreList();

        List<String> stringList = this.populateStringList();
        return stringList.stream()
                .flatMap(stringListText -> Arrays.stream(stringListText.split(" ")))
                .map(String::toLowerCase)
                .filter(word -> !ignoreList.contains(word))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(wordCountMap -> wordCountMap.getValue() > filterWordsWithLengthGreaterThan)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(wordCountMap -> new KeyValueLong(wordCountMap.getKey(), wordCountMap.getValue()))
                .collect(Collectors.toList());
    }

    private List<String> populateStringList() {
        List<String> stringList = new ArrayList<>();
        stringList.add(getMargaritavilleLyrics().replace("\n", ""));
        return stringList;
    }

    private List<String> getIgnoreList() {
        List<String> wordIgnoreList = new ArrayList<>();
        wordIgnoreList.add("a");
        wordIgnoreList.add("i");
        wordIgnoreList.add("and");
        wordIgnoreList.add("but");
        return wordIgnoreList;
    }

    private String getMargaritavilleLyrics() {
        return "" +
                "Nibblin' on sponge cake\n" +
                "Watchin' the sun bake\n" +
                "All of those tourists covered with oil\n" +
                "Strummin' my six string\n" +
                "On my front porch swing\n" +
                "Smell those shrimp, they're beginnin' to boil\n" +
                "Wastin' away again in Margaritaville\n" +
                "Searchin' for my lost shaker of salt\n" +
                "Some people claim that there's a woman to blame\n" +
                "But I know, it's nobody's fault\n" +
                "Don't know the reason\n" +
                "Stayed here all season\n" +
                "Nothing to show but this brand new tattoo\n" +
                "But it's a real beauty\n" +
                "A Mexican cutie\n" +
                "How it got here, I haven't a clue\n" +
                "Wastin' away again in Margaritaville\n" +
                "Searchin' for my lost shaker of salt\n" +
                "Some people claim that there's a woman to blame\n" +
                "Now I think, hell, it could be my fault\n" +
                "I blew out my flip flop\n" +
                "Stepped on a pop top\n" +
                "Cut my heel, had to cruise on back home\n" +
                "But there's booze in the blender\n" +
                "And soon it will render\n" +
                "That frozen concoction that helps me hang on\n" +
                "Wastin' away again in Margaritaville\n" +
                "Searchin' for my lost shaker of salt\n" +
                "Some people claim that there's a woman to blame\n" +
                "But I know, it's my own damn fault\n" +
                "Yes, and some people claim\n" +
                "That there's a woman to blame\n" +
                "And I know, it's my own damn fault\n" +
                "";
    }
}
