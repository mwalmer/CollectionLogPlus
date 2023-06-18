package com.collectionlogplus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;

import java.io.*;
import java.util.HashMap;

@Slf4j
public class DataHandler {
    public static <K, V> void Serialize(HashMap<K, V> hashMap, long hash, String fileName)
    {
        String accountHash = String.valueOf(hash);
        CollectionLogPlusPlugin.COLLECTIONLOGPLUS_DIR.mkdir();
        File playerData = new File(CollectionLogPlusPlugin.COLLECTIONLOGPLUS_DIR, accountHash + "-" + fileName);
        try {
            //playerData.delete();
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

    public static <K, V> HashMap<K, V> Deserialize(long hash, String fileName)
    {
        HashMap<K, V> hashMap;
        String accountHash = String.valueOf(hash);
        File playerData = new File(CollectionLogPlusPlugin.COLLECTIONLOGPLUS_DIR, accountHash + "-" + fileName);
        if(playerData.exists())
        {
            try {
                FileInputStream fileInputStream = new FileInputStream(playerData);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                hashMap = (HashMap<K, V>) objectInputStream.readObject();
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
