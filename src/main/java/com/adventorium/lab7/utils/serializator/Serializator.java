package com.adventorium.lab7.utils.serializator;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Андрей on 29.05.2016.
 */
public interface Serializator {
    void write(Object object, OutputStream outputStream);

    void write(Object[] object, OutputStream outputStream);

    MusicSerializableEntity[] read(InputStream inputStream);
}
