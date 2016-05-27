package com.adventorium.lab7;

import com.adventorium.lab7.utils.NonserializableStart;
import com.adventorium.lab7.utils.SerializableStart;

/**
 * This is a music catalog model
 * Created by Андрей on 25.05.2016.
 */
public class Main {
    public static void main(String[] args) {

        NonserializableStart nonserializableStart = new NonserializableStart();
        nonserializableStart.damagedFileDeserialization();
        SerializableStart serializableStart = new SerializableStart();
    }
}
