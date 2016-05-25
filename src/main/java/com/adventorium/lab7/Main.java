package com.adventorium.lab7;

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
