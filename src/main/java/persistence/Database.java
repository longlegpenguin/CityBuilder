package persistence;

import model.GameModel;

import java.io.*;

public class Database {

    /**
     * Writes game model to "data.ser" save file.
     *
     * @param gameModel the game model to be saved.
     */
    public static void save(GameModel gameModel) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("data.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameModel);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in data.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Writes game model to given save file.
     *
     * @param gameModel the game model to be saved.
     * @param filename  the save file name
     */
    public static void save(GameModel gameModel, String filename) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameModel);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + filename);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Read game model from "data.ser" save files
     *
     * @return the saved game model
     */
    public static GameModel read() {
        GameModel gameModel;
        try {
            FileInputStream fileIn = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameModel = (GameModel) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Game model class not found");
            c.printStackTrace();
            return null;
        }
        System.out.println("Serialized data is read from data.ser");
        return gameModel;
    }

    /**
     * Read game model from given save files
     *
     * @param filename the save file name
     * @return the saved game model
     */
    public static GameModel read(String filename) {
        GameModel gameModel;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameModel = (GameModel) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Game model class not found");
            c.printStackTrace();
            return null;
        }
        System.out.println("Serialized data is read from " + filename);
        return gameModel;
    }
}
