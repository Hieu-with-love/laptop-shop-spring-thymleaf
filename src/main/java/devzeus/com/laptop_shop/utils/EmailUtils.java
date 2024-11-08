package devzeus.com.laptop_shop.utils;

import java.security.SecureRandom;

public class EmailUtils {

    public static String verifyAccount(String name, String host, String token) {
        // we have port listening for verify
        return "Hello " + name
                + ", \n\n Your account has been created. Please click on the this link to my system verify account for you "
                + getVerifiedUrl(host, token)
                + "\n\n Best Regards" +
                "\nSupport by [Devzeus]";
    }

    public static String getVerifiedUrl(String host, String token) {
        return host + "/verify-account?token=" + token;
    }

    public static String generateRandomPassword() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        SecureRandom RANDOM = new SecureRandom();
        int length = 8;
        StringBuilder passwordBuilder = new StringBuilder(length);
        for (int i = 0; i < 8; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            passwordBuilder.append(CHARACTERS.charAt(index));
        }
        return passwordBuilder.toString();
    }

}
