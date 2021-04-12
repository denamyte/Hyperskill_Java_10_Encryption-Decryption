package encryptdecrypt;

import java.util.function.*;
import java.util.stream.Collectors;

public class EncodingDecodingFactory {

    //                            left border ->      right border ->     shift -> char -> shifted char
    private static final Function<Character, Function<Character, Function<Integer, IntUnaryOperator>>> GENERAL_SHIFT_ALG =
            left -> right -> shift -> ch ->
                    ch >= left && ch <= right
                            ? (ch - left + shift) % (right - left + 1) + left
                            : ch;

    private static int getPositiveShift(Integer shift, int codePageLength) {
        while (shift < 0) {
            shift += codePageLength;
        }
        return shift;
    }

    private static final BiFunction<String, Integer, String> SHIFT_ALG = (s, shift) -> {

        shift = getPositiveShift(shift, 26);
        final var shiftOperators = GENERAL_SHIFT_ALG.apply('A').apply('Z').apply(shift)
                .andThen(GENERAL_SHIFT_ALG.apply('a').apply('z').apply(shift));

        return s.chars()
                .map(shiftOperators)
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    };

    private static final BiFunction<String, Integer, String> UNICODE_ALG = (s, shift) ->
            s.chars().map(i -> i + shift)
                    .mapToObj(i -> (char) i)
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));

    public static BiFunction<String, Integer, String> get(String type) {
        switch (type) {
            case "shift":
                return SHIFT_ALG;
            case "unicode":
                return UNICODE_ALG;
            default:
                return (s, shift) -> s;
        }
    }
}
