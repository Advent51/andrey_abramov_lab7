package com.adventorium.lab7.utils;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by Андрей on 29.05.2016.
 */
public interface SerializatorInterface {
    void write(Object object, OutputStream outputStream);
    void write(Object[] object, OutputStream outputStream);
}
