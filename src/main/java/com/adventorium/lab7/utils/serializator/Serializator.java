package com.adventorium.lab7.utils.serializator;

import com.adventorium.lab7.music.Author;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Created by Андрей on 29.05.2016.
 */
public interface Serializator {
    void write(Object object, OutputStream outputStream);

    void write(Object[] object, OutputStream outputStream);

    Collection<Author> read(InputStream inputStream);
}
