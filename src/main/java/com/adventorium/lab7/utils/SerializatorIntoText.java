package com.adventorium.lab7.utils;

import java.io.*;

/**
 * Created by Андрей on 25.05.2016.
 */
public class SerializatorIntoText implements SerializatorInterface {
    public void write(Object object, OutputStream outputStream) {
        PrintStream out = new PrintStream(outputStream, false);
        out.print(object.toString());
        out.flush();
    }

    public void write(Object[] object, OutputStream outputStream) {
        for (Object obj : object) {
            write(obj, outputStream);
        }
    }
}
