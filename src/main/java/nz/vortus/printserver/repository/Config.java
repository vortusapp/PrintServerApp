package nz.vortus.printserver.repository;

import nz.vortus.printserver.printers.PrintSettings;

import java.io.*;


public class Config {
    private static String filePath = "config.properties";




    public static PrintSettings load(){
        File configFile = new File(filePath);
        if (!configFile.exists() || configFile.length() == 0) {
            // handle empty or non-existent file
            return null;
        }
        try (FileInputStream fileIn = new FileInputStream(configFile);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (PrintSettings) in.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

        public static void save (PrintSettings printSettings) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(printSettings);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();

        }}}

