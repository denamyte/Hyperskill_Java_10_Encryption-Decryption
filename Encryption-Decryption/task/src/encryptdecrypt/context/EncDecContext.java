package encryptdecrypt.context;

public class EncDecContext {

    private EncDecStrategy strategy;

    String invoke() {
        return strategy.execute();
    }

    public void setStrategy(EncDecStrategy strategy) {
        this.strategy = strategy;
    }
}
