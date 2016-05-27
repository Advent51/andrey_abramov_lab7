package com.adventorium.lab7.utils;

import java.io.*;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Serializator {
    public static void write(Object[] object, File fileOut) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)))) {

            out.writeInt(object.length);
            for (Object obj : object) {
                out.writeObject(obj);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(Object object, File fileOut) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)))) {
            out.writeInt(1);
            out.writeObject(object);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
