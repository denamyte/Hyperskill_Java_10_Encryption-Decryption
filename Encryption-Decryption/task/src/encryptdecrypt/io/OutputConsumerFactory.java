package encryptdecrypt.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public class OutputConsumerFactory {

    public static Consumer<String> get(String fileName) {
        if (fileName.isEmpty()) {
            return System.out::println;
        }
        return s -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(s);
            } catch (IOException e) {
                System.out.println("Error!");
                throw new RuntimeException("Error in I/O operations");
            }
        };
    }
}
