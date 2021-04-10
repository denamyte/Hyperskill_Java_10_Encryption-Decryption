package encryptdecrypt;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class EncDecTemplate {

    private Supplier<String> inputSupplier;
    private Consumer<String> outputConsumer;
    protected Integer shift;

    public void work() {
        checkSpareParts();
        String before = inputSupplier.get();
        String after = encDecAlgorithm(before);
        outputConsumer.accept(after);
    }

    public void setInputSupplier(Supplier<String> inputSupplier) {
        this.inputSupplier = inputSupplier;
    }

    public void setOutputConsumer(Consumer<String> outputConsumer) {
        this.outputConsumer = outputConsumer;
    }

    public void setShift(boolean encoding, int shift) {
        this.shift = encoding ? shift : -shift;
    }

    private void checkSpareParts() {
        if (inputSupplier == null || outputConsumer == null || shift == null) {
            throw new IllegalArgumentException("Not all parts are set for the algorithm to work");
        }
    }

    protected abstract String encDecAlgorithm(String s);

}
