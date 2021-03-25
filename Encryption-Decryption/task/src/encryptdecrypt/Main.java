package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        String s = "we found a treasure!";
        System.out.println(doEncrypt(simpleEncryptor, s));
    }

    public static String doEncrypt(Encryptor encryptor, String s) {
        return encryptor.encrypt(s);
    }

    interface Encryptor {
        String encrypt(String s);
    }

    public static final Encryptor simpleEncryptor = str -> {
        char low = 'a';
        char hi = 'z';
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            char c1 = c >= low && c <= hi ? (char) (low + hi - c) : c;
            sb.append(c1);
        }
        return sb.toString();
    };

}
