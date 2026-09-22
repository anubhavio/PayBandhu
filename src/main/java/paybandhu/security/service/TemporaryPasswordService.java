package paybandhu.security.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TemporaryPasswordService {

    private static final String CHARACTERS =
            "ABCDEFGHJKLMNPQRSTUVWXYZ" +
                    "abcdefghijkmnopqrstuvwxyz" +
                    "23456789";

    private final SecureRandom random = new SecureRandom();

    public String generate() {

        int length = 10;

        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
