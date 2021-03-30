package encryptdecrypt;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        String s = scanner.nextLine();
        int shift = Integer.parseInt(scanner.nextLine());
        String processed = new EncDec3().performOperation(type, s, shift);
        System.out.println(processed);
    }
}

class EncDec3 {

    public String performOperation(String type, String s, int shift) {
        return encDec(s, "enc".equals(type) ? shift : -shift);
    }

    private String encDec(String s, int shift) {
        return s.chars().map(i -> i + shift)
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }
}
