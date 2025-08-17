package com.blueprinthell.map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class MapValidator {
    private static final MapValidator instance = new MapValidator();

    private static final String MASTER_KEY = "HailArianHemmati";
    private boolean active = false;

    public static MapValidator getInstance() {
        return instance;
    }

    private MapValidator() {}

    public JsonObject encrypt(JsonObject data) throws Exception {
        if (active) {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey sessionKey = keyGen.generateKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sessionKey);
            byte[] encryptedData = cipher.doFinal(data.toString().getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, masterKeySpec());
            byte[] encryptedSessionKey = cipher.doFinal(sessionKey.getEncoded());

            JsonObject encrypted = new JsonObject();
            encrypted.addProperty("sessionKey", Base64.getEncoder().encodeToString(encryptedSessionKey));
            encrypted.addProperty("data", Base64.getEncoder().encodeToString(encryptedData));
            return encrypted;
        }
        return data;
    }

    public JsonObject decrypt(JsonObject fileData) throws Exception {
        if (active) {
            String key = fileData.get("sessionKey").getAsString();
            String data = fileData.get("data").getAsString();

            Cipher cipher = Cipher.getInstance("AES");

            byte[] encryptedSessionKey = Base64.getDecoder().decode(key);
            cipher.init(Cipher.DECRYPT_MODE, masterKeySpec());
            byte[] sessionKeyBytes = cipher.doFinal(encryptedSessionKey);
            SecretKey sessionKey = new SecretKeySpec(sessionKeyBytes, "AES");

            byte[] encryptedData = Base64.getDecoder().decode(data);
            cipher.init(Cipher.DECRYPT_MODE, sessionKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedData);

            String jsonString = new String(decryptedBytes);
            return JsonParser.parseString(jsonString).getAsJsonObject();
        }
        return fileData;
    }

    private static SecretKey masterKeySpec() {
        return new SecretKeySpec(MASTER_KEY.getBytes(), "AES");
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }


}
