package encryptdecrypt;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int shift = Integer.parseInt(scanner.nextLine());
        System.out.println(new Encryptor2(shift).encrypt(s));
    }
}

class Encryptor2 {

    static final String abc = "abcdefghijklmnopqrstuvwxyz";
    private final int shift;

    public Encryptor2(int shift) {
        this.shift = shift;
    }

    public String encrypt(String s) {
        return s.chars()
                .map(this::tryShift)
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }

    private int tryShift(int i) {
        return i >= 'a' && i <= 'z' ? makeShift(i) : i;
    }

    private int makeShift(int i) {
        return (i - 'a' + shift) % abc.length() + 'a';
    }
}
