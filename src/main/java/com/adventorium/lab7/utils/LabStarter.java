package com.adventorium.lab7.utils;

import com.adventorium.lab7.music.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Андрей on 25.05.2016.
 */
public class LabStarter {
    public LabStarter() {
        ModelCreator mc = new ModelCreator();

        File fileOut = new File(System.getProperty("user.home") + "/Downloads/OutTest.txt");
        File fileOutDeser = new File(System.getProperty("user.home") + "/Downloads/OutTestDeser.txt");

        if (fileOut.exists()) {
            fileOut.delete();
        }

        if (fileOutDeser.exists()) {
            fileOutDeser.delete();
        }

        mc.getAuthors().forEach((object) -> SerializatorIntoText.write(object, fileOut));
        DeserializatorFromText.read(fileOut);
        DeserializatorFromText.getAuthors().forEach((object) -> SerializatorIntoText.write(object, fileOutDeser));

        File fileOutDamaged = new File(System.getProperty("user.home") + "/Downloads/OutTestDamaged.txt");
        if (!fileOutDamaged.exists()) {
            try {
                Files.copy(fileOut.toPath(), fileOutDamaged.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        damagedFileDeserialization();

        File fileOutForSerializable = new File(System.getProperty("user.home") + "/Downloads/OutTestForSerializable.txt");
        File fileOutForSerializableDeser = new File(System.getProperty("user.home") + "/Downloads/OutTestForSerializableDeser.txt");

        if (fileOutForSerializable.exists()) {
            fileOutForSerializable.delete();
        }

        if (fileOutForSerializableDeser.exists()) {
            fileOutForSerializableDeser.delete();
        }

        Serializator.write(mc.getEntityForSerializator(), fileOutForSerializable);
        Deserializator.read(fileOutForSerializable);
        Serializator.write(mc.getEntityForSerializator(), fileOutForSerializableDeser);
    }

    private void damagedFileDeserialization() {
        File fileOutDamaged = new File(System.getProperty("user.home") + "/Downloads/OutTestDamaged.txt");
        File fileOutDamagedDeser = new File(System.getProperty("user.home") + "/Downloads/OutTestDamagedDeser.txt");
        if (fileOutDamagedDeser.exists()) {
            fileOutDamagedDeser.delete();
        }
        DeserializatorFromText.read(fileOutDamaged);
        DeserializatorFromText.getAuthors().forEach((object) -> SerializatorIntoText.write(object, fileOutDamagedDeser));
    }
}
