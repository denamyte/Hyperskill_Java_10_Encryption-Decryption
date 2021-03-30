package encryptdecrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println(new EncDec4(args).getResult());
    }
}

class EncDec4 {

    static final String MODE = "-mode";
    static final String KEY = "-key";
    static final String DATA = "-data";
    static final String ENC = "enc";

    static final Map<String, String> defaultParams = Map.of(
            MODE, ENC,
            KEY, "0",
            DATA, ""
    );

    private final Map<String, String> params;

    public EncDec4(String[] args) {
        params = parseCLIParams(args);
    }

    public static Map<String, String> parseCLIParams(String[] args) {
        int length = args.length;
        int taken = 0;
        Map<String, String> params = new HashMap<>();
        while (taken < length) {
            params.put(args[taken++], args[taken++]);
        }
        return params;
    }

    private String getParam(String key) {
        return params.getOrDefault(key, defaultParams.get(key));
    }

    public String getResult() {
        return performOperation(getParam(MODE), getParam(DATA), Integer.parseInt(getParam(KEY)));
    }

    public static String performOperation(String mode, String s, int shift) {
        return encDec(s, ENC.equals(mode) ? shift : -shift);
    }

    private static String encDec(String s, int shift) {
        return s.chars().map(i -> i + shift)
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }
}
