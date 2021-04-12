package encryptdecrypt;

import java.util.function.*;

public class EncDecTemplate {

    private Supplier<String> inputSupplier;
    private Consumer<String> outputConsumer;
    private Integer shift;
    private BiFunction<String, Integer, String> endDecAlgorithmFn;

    public void work() {
        checkSpareParts();
        String before = inputSupplier.get();
        String after = endDecAlgorithmFn.apply(before, shift);
        outputConsumer.accept(after);
    }

    public EncDecTemplate setInputSupplier(Supplier<String> inputSupplier) {
        this.inputSupplier = inputSupplier;
        return this;
    }

    public EncDecTemplate setOutputConsumer(Consumer<String> outputConsumer) {
        this.outputConsumer = outputConsumer;
        return this;
    }

    public EncDecTemplate setShift(boolean encoding, int shift) {
        this.shift = encoding ? shift : -shift;
        return this;
    }

    public EncDecTemplate setEndDecAlgorithmFn(BiFunction<String, Integer, String> endDecAlgorithmFn) {
        this.endDecAlgorithmFn = endDecAlgorithmFn;
        return this;
    }

    private void checkSpareParts() {
        if (inputSupplier == null || outputConsumer == null || shift == null || endDecAlgorithmFn == null) {
            throw new IllegalArgumentException("Not all parts are set for the algorithm to work");
        }
    }
}
