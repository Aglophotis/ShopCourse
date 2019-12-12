package ru.aglophotis.mirea.microservice.item.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.aglophotis.mirea.microservice.item.entities.Payload;

import java.io.IOException;

@Component
public class TokenUtils {
    private static final String SECRET_KEY = "secret_key";

    public boolean validateToken(String token) {
        StringBuilder sb = new StringBuilder();
        String[] parsedToken = token.split("\\.");
        sb.append(parsedToken[0]);
        sb.append(".");
        sb.append(parsedToken[1]);
        String hash = Sha256Encoder.hashMac(sb.toString(), SECRET_KEY);
        System.out.println("hash:\n" + hash);
        System.out.println("sign:\n" + parsedToken[2]);
        if (hash.equals(parsedToken[2])) {
            return true;
        }
        return false;
    }

    public Payload getPayload(String token) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String[] parsedToken = token.split("\\.");
            String decodedPayload = base64Decode(parsedToken[1]);
            Payload payload = mapper.readValue(decodedPayload, Payload.class);
            return payload;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String base64Decode(String rawString) {
        byte[] encoded = Base64.decodeBase64(
                rawString.getBytes());
        return new String(encoded);
    }
}
