package ru.solomka.business.registration.utils;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.core.message.Messages;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Checker {

    public String readResponse(@NotNull HttpURLConnection httpURLConnection) throws IOException {
        InputStream responseStream = new BufferedInputStream(httpURLConnection.getInputStream());

        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();

        return stringBuilder.toString().replaceAll("<.*>", "");
    }

    public boolean ip(String ip) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("https://pastebin.com/VWEJsNdz").openConnection();

        if (!readResponse(connection).contains(ip)) {
            Messages.logger("Access denied!", "Your ip is not in the allowed list!", "If this is an error, contact the administration");
            Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
            connection.disconnect();
            return false;
        }
        connection.disconnect();
        return true;
    }

    @SneakyThrows
    public String getIp() {
        URL url = new URL("http://api.ipify.org/");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        return reader.readLine();
    }
}