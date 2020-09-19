package dataAccess.utils;

import model.exceptions.MinecraftDataRetrieverException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class MinecraftDataRetrievingUtils {
    private static final String USERID_LNK = "https://api.mojang.com/users/profiles/minecraft/";
    private static final String USERINFO_LNK = "https://sessionserver.mojang.com/session/minecraft/profile/";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder res = new StringBuilder();

        int charPointer;
        while((charPointer = rd.read()) != -1) {
            res.append((char)charPointer);
        }

        return res.toString();
    }

    public static String getUserUUID(String username) throws MinecraftDataRetrieverException {
        String uuid = null;

        try {
            URL userIdURL = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            HttpURLConnection userIdConnection = (HttpURLConnection)userIdURL.openConnection();

            userIdConnection.setRequestMethod("GET");
            userIdConnection.setRequestProperty("Content-Type", "application/json");
            userIdConnection.connect();

            if (userIdConnection.getResponseCode() == 200) {
                BufferedReader userIdResponse = new BufferedReader(new InputStreamReader(userIdConnection.getInputStream()));
                JSONObject json = new JSONObject(readAll(userIdResponse));

                if (!json.has("error"))
                    uuid = json.getString("id");
                else throw new MinecraftDataRetrieverException("L'utilisateur n'existe pas.");
            }

            userIdConnection.disconnect();
        } catch (IOException exception) {
            throw new MinecraftDataRetrieverException(exception.getMessage());
        }

        return uuid;
    }

    public static Image getSkinHeadViaUsername(String username) throws MinecraftDataRetrieverException {
        return getSkinHeadViaUUID(getUserUUID(username));
    }

    public static Image getSkinHeadViaUUID(String uuid) throws MinecraftDataRetrieverException {
        BufferedImage skin = null;

        try {
            URL userInfoURL = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
            HttpURLConnection userInfoConnection = (HttpURLConnection)userInfoURL.openConnection();

            userInfoConnection.setRequestMethod("GET");
            userInfoConnection.setRequestProperty("Content-Type", "application/json");
            userInfoConnection.connect();

            if (userInfoConnection.getResponseCode() == 200) {
                BufferedReader userInfoResponse = new BufferedReader(new InputStreamReader(userInfoConnection.getInputStream()));
                JSONObject json = new JSONObject(readAll(userInfoResponse));
                JSONObject decodedJSON = new JSONObject(new String(Base64.getDecoder().decode(json.getJSONArray("properties").getJSONObject(0).getString("value"))));

                skin = convertToBufferedImage(ImageIO.read(new URL(decodedJSON.getJSONObject("textures").getJSONObject("SKIN").getString("url"))).getScaledInstance(1024, 1024, 1)).getSubimage(128, 128, 128, 128);
            }

            userInfoConnection.disconnect();
        } catch (Exception exception) {
            throw new MinecraftDataRetrieverException(exception.getMessage());
        }

        return skin;
    }

    public static BufferedImage convertToBufferedImage(Image image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
        Graphics2D g = newImage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return newImage;
    }
}
