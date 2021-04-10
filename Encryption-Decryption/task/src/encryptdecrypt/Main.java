package encryptdecrypt;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        new EncDec6(args).doWork();
    }
}

class EncDec6 {

    static final String ALG = "-alg";
    static final String MODE = "-mode";
    static final String KEY = "-key";
    static final String DATA = "-data";
    static final String IN = "-in";
    static final String OUT = "-out";
    static final List<String> keys = List.of(MODE, KEY, DATA, IN, OUT);

    static final String ENC = "enc";
    static final String SHIFT = "shift";
    static final String UNICODE = "unicode";

    static final Map<String, String> defaultParams = Map.of(
            ALG, SHIFT,
            MODE, ENC,
            KEY, "0",
            DATA, "",
            IN, "",
            OUT, ""
    );

    private final Map<String, String> params;
    private boolean error;
    private String result;

    public EncDec6(String[] args) {
        params = parseCLIParams(args);
    }

    private Map<String, String> parseCLIParams(String[] args) {
        int length = args.length;
        int taken = 0;
        Map<String, String> params = new HashMap<>();
        while (taken < length) {
            final String key = args[taken++];
            if (taken == length) {
                error = true;
                break;
            }
            final String value = args[taken++];
            if (keys.contains(value)) {
                error = true;
                break;
            }
            params.put(key, value);
        }
        return params;
    }

    public void doWork() {
        String inputText = getInputText();
        if (someError()) {
            return;
        }
        final String mode = getParam(MODE);
        final int key = Integer.parseInt(getParam(KEY));
        performOperation(mode, inputText, key);
        outputResult();
    }

    private boolean someError() {
        if (error) {
            System.out.println("Error!");
        }
        return error;
    }

    private String getParam(String key) {
        return params.getOrDefault(key, defaultParams.get(key));
    }

    // TODO: 4/10/21 make an InputSupplier from this method
    private String getInputText() {
        String data = getParam(DATA);
        if (data.length() > 0) {
            return data;
        }
        String inputFileName = getParam(IN);
        if (inputFileName.length() > 0) {
            try(BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
                return reader.readLine();
            } catch (Exception e) {
                error = true;
                return "";
            }
        }
        return "";
    }

    private void performOperation(String mode, String s, int key) {
        result = encDec(s, ENC.equals(mode) ? key : -key);
    }

    private static String encDec(String s, int shift) {
        return s.chars().map(i -> i + shift)
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }

    // TODO: 4/10/21 Make and OutputConsumer from this method
    private void outputResult() {
        String outFileName = getParam(OUT);
        if (outFileName.length() > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName))) {
                writer.write(result);
            } catch (IOException e) {
                error = true;
                someError();
            }
        } else {
            System.out.println(result);
        }
    }
}
