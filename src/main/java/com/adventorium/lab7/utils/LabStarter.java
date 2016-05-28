package com.adventorium.lab7.utils;

import java.io.*;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Андрей on 25.05.2016.
 */
public class LabStarter {

    private String USER_HOME;
    private SerializatorInterface serializator;

    public LabStarter() {
        USER_HOME = System.getProperty("user.home");
    }

    public void startThisLab() {

        ModelCreator mc = new ModelCreator(10, 10, 100);
        serializator = new SerializatorIntoText();

        File fileOut = createFile("OutTest.txt");
        File fileOutDeser = createFile("OutTestDeser.txt");

        mc.getAuthors().forEach((object) -> startSerialization(object, fileOut, serializator));
        DeserializatorFromText.read(fileOut);
        DeserializatorFromText.getAuthors().forEach((object) -> startSerialization(object, fileOutDeser, serializator));

        File fileOutDamaged = new File(USER_HOME + "/Downloads/OutTestDamaged.txt");
        if (!fileOutDamaged.exists()) {
            try {
                Files.copy(fileOut.toPath(), fileOutDamaged.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File fileOutDamagedDeser = createFile("OutTestDamagedDeser.txt");

        DeserializatorFromText.read(fileOutDamaged);
        DeserializatorFromText.getAuthors().forEach((object) -> startSerialization(object, fileOutDamagedDeser, serializator));

        serializator = new Serializator();

        File fileOutForSerializable = createFile("OutTestForSerializable.txt");
        File fileOutForSerializableDeser = createFile("OutTestForSerializableDeser.txt");

        startSerialization(mc.getEntityForSerializator(), fileOutForSerializable, serializator);
        Deserializator.read(fileOutForSerializable);
        startSerialization(mc.getEntityForSerializator(), fileOutForSerializableDeser, serializator);
    }

    private File createFile(String fileName) {
        File file = new File(USER_HOME + "/Downloads/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    private FileOutputStream startSerialization(Object object, File file, SerializatorInterface serializator) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            serializator.write(object, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileOutputStream;
    }

    private FileOutputStream startSerialization(Object[] object, File file, SerializatorInterface serializator) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            serializator.write(object, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileOutputStream;
    }
}
