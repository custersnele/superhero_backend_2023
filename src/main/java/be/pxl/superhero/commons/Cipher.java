package be.pxl.superhero.commons;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Cipher {

    private Cipher() {
    }

    public static String skipALetter(String sentence) {
        return Arrays.stream(sentence.split(" ")).map(Cipher::skipALetterWord).collect(Collectors.joining(" "));
    }

    private static String skipALetterWord(String word) {
        StringBuilder builder = new StringBuilder();
        int middle = word.length() / 2;
        if (word.length() % 2 == 1) {
            middle += 1;
        }
        for (int i = 0; i < middle; i++) {
            builder.append(word.charAt(i));
            if (middle + i < word.length()) {
                builder.append(word.charAt(middle + i));
            }
        }
        return builder.toString();
    }
}
