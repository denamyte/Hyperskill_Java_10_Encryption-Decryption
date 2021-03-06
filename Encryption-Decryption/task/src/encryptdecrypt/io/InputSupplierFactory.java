package encryptdecrypt.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.function.Supplier;

public class InputSupplierFactory {

    private static Supplier<String> fileSupplier(String fileName) {
        return () -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                return reader.readLine();
            } catch (Exception e) {
                System.out.println("Error!");
                throw new RuntimeException("Error in I/O operations");
            }
        };
    }

    public static Supplier<String> get(String dataParam, Supplier<String> inputFileNameSupplier) {
        if (dataParam.length() > 0) {
            return () -> dataParam;
        }
        String fileName = inputFileNameSupplier.get();
        if (fileName.length() > 0) {
            return fileSupplier(fileName);
        }
        return () -> "";
    }
}
