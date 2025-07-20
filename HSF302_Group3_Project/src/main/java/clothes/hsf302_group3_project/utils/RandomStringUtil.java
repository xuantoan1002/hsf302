package clothes.hsf302_group3_project.utils;

import java.util.UUID;

public class RandomStringUtil {

    public static String getRandomString(int length) {
        if (length > 32 || length < 1) {
            throw new IllegalArgumentException("Invalid length!");
        }
        String randomString = UUID.randomUUID().toString();
        String cleanedString = randomString.replace("-", "");
        return cleanedString.substring(0, length);
    }
}
