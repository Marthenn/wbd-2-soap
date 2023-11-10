package webwbd.util;

import java.security.SecureRandom;

public class PasswordUtil {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*_=+-/";

    public static String generateRandomPassword(int length){
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        String characters = LOWERCASE + UPPERCASE + DIGITS + SYMBOLS;
        for (int i = 0 ; i < length ; i++){
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }
}
