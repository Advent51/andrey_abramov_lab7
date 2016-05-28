package com.adventorium.lab7.utils;

import java.io.*;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Serializator implements SerializatorInterface {
    public void write(Object[] object, OutputStream outputStream) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {

            out.writeInt(object.length);
            for (Object obj : object) {
                out.writeObject(obj);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Object object, OutputStream outputStream) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {
            out.writeInt(1);
            out.writeObject(object);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
