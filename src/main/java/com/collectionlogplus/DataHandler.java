package com.collectionlogplus;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;

@Slf4j
public class DataHandler {
    public static void Serialize(HashMap<Integer, Integer> hashMap, long hash, String fileName)
    {
        String accountHash = String.valueOf(hash);
        File playerData = new File(CollectionLogPlusPlugin.COLLECTIONLOGPLUS_DIR, accountHash + "-" + fileName);
        try {
            playerData.delete();
            playerData.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(playerData);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(hashMap);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static HashMap<Integer, Integer> Deserialize(long hash, String fileName)
    {
        HashMap<Integer, Integer> hashMap;
        String accountHash = String.valueOf(hash);
        File playerData = new File(CollectionLogPlusPlugin.COLLECTIONLOGPLUS_DIR, accountHash + "-" + fileName);
        if(playerData.exists())
        {
            try {
                FileInputStream fileInputStream = new FileInputStream(playerData);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                hashMap = (HashMap<Integer, Integer>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                log.error("Something went wrong loading the collection log hash map!");
                return null;
            }

            return hashMap;
        }

        return null;
    }
}
