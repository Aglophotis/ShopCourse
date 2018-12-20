package ru.aglophotis.mirea.microservice.authorization.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

public class TokenGenerator {
    private static final String SECRET_KEY = "secret_key";

    public String getToken(String username, String roleWithId) {
        String header = getHeader("HmacSHA256");
        String payload = getPayload(username, roleWithId);
        StringBuilder result = new StringBuilder();
        result.append(base64Encode(header));
        result.append(".");
        result.append(base64Encode(payload));
        System.out.println(result.toString());
        String hash = Sha256Encoder.hashMac(result.toString(), SECRET_KEY);
        result.append(".");
        result.append(hash);
        return result.toString();
    }

    private String getHeader(String alg) {
        StringBuilder header = new StringBuilder();
        header.append("{\"alg\" : \"");
        header.append(alg);
        header.append("\"}");
        return header.toString();
    }

    private String getPayload(String username, String roleWithId) {
        StringBuilder payload = new StringBuilder();
        String[] userInfo = roleWithId.split(":");
        payload.append("{\"sub\" : \"");
        payload.append(userInfo[0]);
        payload.append("\", \"name\" : \"");
        payload.append(username);
        payload.append("\", \"role\" : \"");
        payload.append(userInfo[1]);
        payload.append("\", \"iat\" : \"");
        payload.append(new Date().getTime());
        payload.append("\", \"exp\" : \"");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        payload.append(calendar.getTime().getTime());
        payload.append("\"}");
        return payload.toString();
    }

    private String base64Encode(String rawString) {
        byte[] encoded = Base64.encodeBase64(
                rawString.getBytes(Charset.forName("US-ASCII")));
        return new String(encoded);
    }
}
