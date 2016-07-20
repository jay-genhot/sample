package no.trinnvis.dabih.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static boolean checkPassword(String candidate, String hashedPassword) {
        boolean matched = verifyPassword(candidate, hashedPassword);

        return matched;
    }

    private static boolean verifyPassword(String candidate, String hashedPassword) {
        if (candidate == null) {
            return false;
        }
        if (candidate.trim().equals("")) {
            return false;
        }

        String normalizedCandidate = candidate.trim();
        return BCrypt.checkpw(normalizedCandidate, hashedPassword);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(8));
    }
}
