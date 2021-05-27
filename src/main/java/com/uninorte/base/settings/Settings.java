package com.uninorte.base.settings;

import java.io.*;

public class Settings {

    public static final String USER_DATA_FILENAME = "userinformation.spaceinvaders";

    private String path;

    public Settings(String folderName) {
        String homeDir = System.getProperty("user.home");
        path = homeDir + File.separator + folderName;

        createDirectory(path);
    }

    public String getPath() {
        return path;
    }

    public String getPath(String filename) {
        return path + File.separator + filename;
    }

    public String getGameDirectoryPath() {
        return path;
    }

    public void createDirectory(String path) {
        File directory = new File(path);
        if (! directory.exists()){
            directory.mkdirs();
        }
    }

    public boolean fileExists(String filename) {
        File file = new File(getPath(filename));
        return file.exists();
    }

    public void saveData(String filename, String json) throws Exception {
        FileWriter fileWriter = new FileWriter(getPath(filename));
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();
    }

    public String getData(String filename) {
        if (!fileExists(filename))
            return null;

        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(getPath(filename)));

            String line;
            while((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public void deleteDate(String filename) {
        File file = new File(getPath(filename));
        file.delete();
    }
}
