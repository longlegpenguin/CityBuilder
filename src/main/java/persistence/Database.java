package persistence;

import model.GameModel;

import java.io.*;

public class Database {

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

}
