package encryptdecrypt;

import encryptdecrypt.io.InputSupplierFactory;
import encryptdecrypt.io.OutputConsumerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                System.out.println("Error!");
                return null;
            }
            final String value = args[taken++];
            if (keys.contains(value)) {
                System.out.println("Error!");
                return null;
            }
            params.put(key, value);
        }
        return params;
    }

    public void doWork() {
        new EncDecTemplate()
                .setInputSupplier(InputSupplierFactory.get(getParam(DATA), () -> getParam(IN)))
                .setOutputConsumer(OutputConsumerFactory.get(getParam(OUT)))
                .setShift(getParam(MODE).equals(ENC), Integer.parseInt(getParam(KEY)))
                .setEndDecAlgorithmFn(EncodingDecodingFactory.get(getParam(ALG)))

                .work();
    }

    private String getParam(String key) {
        return params.getOrDefault(key, defaultParams.get(key));
    }
}
