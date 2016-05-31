package com.adventorium.lab7.utils;

import com.adventorium.lab7.music.Author;
import com.adventorium.lab7.utils.serializator.BinarySerializator;
import com.adventorium.lab7.utils.serializator.Serializator;
import com.adventorium.lab7.utils.serializator.TextSerializator;

import java.io.*;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Андрей on 25.05.2016.
 */
public class LabStarter {

    private static final String USER_HOME = System.getProperty("user.home");
    private Serializator serializator;

    public void startThisLab() {

        try {

            ModelCreator mc = new ModelCreator(10, 10, 100);
            serializator = new TextSerializator();

            File fileOut;
            FileOutputStream fileOutputStream;
            FileInputStream fileInputStream;

            fileOut = createFileWithExistsRemoving("OutTest.txt");
            fileOutputStream = new FileOutputStream(fileOut, true);
            for (Author author : mc.getAuthors()) {
                serializator.write(author, fileOutputStream);
            }

            fileInputStream = new FileInputStream(fileOut);
            serializator.read(fileInputStream);

            fileOut = createFileWithExistsRemoving("OutTestDeser.txt");
            fileOutputStream = new FileOutputStream(fileOut, true);
            for (Author author : TextSerializator.getAuthors()) {
                serializator.write(author, fileOutputStream);
            }

            File fileOutDamaged = new File(USER_HOME + "/Downloads/OutTestDamaged.txt");
            if (!fileOutDamaged.exists()) {
                try {
                    Files.copy(fileOut.toPath(), fileOutDamaged.toPath(), REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            fileInputStream = new FileInputStream(fileOutDamaged);
            serializator.read(fileInputStream);

            fileOut = createFileWithExistsRemoving("OutTestDamagedDeser.txt");
            fileOutputStream = new FileOutputStream(fileOut, true);
            for (Author author : TextSerializator.getAuthors()) {
                serializator.write(author, fileOutputStream);
            }

            serializator = new BinarySerializator();

            fileOut = createFileWithExistsRemoving("OutTestForSerializable.txt");
            fileOutputStream = new FileOutputStream(fileOut, true);
            serializator.write(ModelCreator.getEntitiesArray(mc.getAuthors(), mc.getAlbums(), mc.getSongs()), fileOutputStream);

            File fileOutForSerializableDeser = createFileWithExistsRemoving("OutTestForSerializableDeser.txt");
            fileOutputStream = new FileOutputStream(fileOutForSerializableDeser, true);
            fileInputStream = new FileInputStream(fileOut);
            serializator.write(serializator.read(fileInputStream), fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private File createFileWithExistsRemoving(String fileName) {
        File file = new File(USER_HOME + "/Downloads/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }
}
