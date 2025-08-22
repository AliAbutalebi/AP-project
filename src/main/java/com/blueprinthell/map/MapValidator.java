package com.blueprinthell.map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class MapValidator {
    private static final MapValidator instance = new MapValidator();

    private static final String MASTER_KEY = "HailArianHemmati";
    private boolean active = true;

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

    public boolean isValidAutoSave(File[] files) {
        try {
            if (!files[1].exists() || files[1].length() == 0) {
                return false;
            }

            String vanilla = Files.readString(files[0].toPath()).trim();
            String autoSave = Files.readString(files[1].toPath()).trim();

            if (autoSave.isEmpty()) return false;

            else if (autoSave.equals(vanilla)) return true;

            JsonElement json = JsonParser.parseString(autoSave);

            if (json != null && json.isJsonObject()) {
                JsonObject autoSaveJson = json.getAsJsonObject();
                if (autoSaveJson.has("sessionKey") &&  autoSaveJson.has("data")) {
                    JsonObject vanillaJson = JsonParser.parseString(vanilla).getAsJsonObject();
                    return !decrypt(autoSaveJson).equals(decrypt(vanillaJson));
                }
                else return false;
            }
            else return false;

        } catch (IOException | JsonSyntaxException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
