package ru.aglophotis.mirea.microservice.balance.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import ru.aglophotis.mirea.microservice.balance.entities.Payload;

import java.io.IOException;

public class TokenHelper {
    private static final String SECRET_KEY = "secret_key";

    public boolean checkToken(String token) {
        StringBuilder sb = new StringBuilder();
        String[] parsedToken = token.split("\\.");
        sb.append(parsedToken[0]);
        sb.append(".");
        sb.append(parsedToken[1]);
        String hash = Sha256Encoder.hashMac(sb.toString(), SECRET_KEY);
        System.out.println(token);
        System.out.println(sb.toString());
        System.out.println(hash);
        System.out.println(parsedToken[2]);
        return hash.equals(parsedToken[2]);
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

    public String base64Decode(String rawString) {
        byte[] encoded = Base64.decodeBase64(
                rawString.getBytes());
        return new String(encoded);
    }
}
