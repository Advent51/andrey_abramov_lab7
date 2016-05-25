package com.adventorium.lab7;

import com.adventorium.lab7.nonserializable.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Андрей on 25.05.2016.
 */
public class NonserializableStart {
    NonserializableStart(){
        System.out.println(System.getProperty("user.home") + "\\Downloads\\OutTest.txt");

        Random random = new Random();

        ArrayList<Author> authors = new ArrayList<>();
        ArrayList<Album> albums = new ArrayList<>();
        ArrayList<Song> songs = new ArrayList<>();

        ArrayList<String> genres = new ArrayList<>();
        genres.add("pop");
        genres.add("grindcore");
        genres.add("metal");
        genres.add("sludge");

        for (int i = 0; i < 10; i++) {
            authors.add(new Author("Author " + i));
        }

        for (int i = 0; i < 10; i++) {
            albums.add(new Album("Album " + i));
        }

        for (int i = 0; i < 100; i++) {
            songs.add(new Song("Song " + i, random.nextInt(1000)));
        }

        for (Album album : albums) {
            album.addAuthor(authors.get(random.nextInt(10)));
            int genreNums = random.nextInt(3) + 1;
            for (int i = 0; i < genreNums; i++) {
                album.addGenre(genres.get(random.nextInt(4)));
            }
        }

        for (Song song : songs) {
            song.addAlbum(albums.get(random.nextInt(10)));
        }

        File fileOut = new File(System.getProperty("user.home") + "/Downloads/OutTest.txt");
        File fileOutDeser = new File(System.getProperty("user.home") + "/Downloads/OutTestDeser.txt");

        if (fileOut.exists()) {
            fileOut.delete();
        }

        authors.forEach((object) -> Serializator.write(object, fileOut));
        Deserializator.read(fileOut);
        Deserializator.authors.forEach((object) -> Serializator.write(object, fileOutDeser));


        File fileOutDamaged = new File(System.getProperty("user.home") + "/Downloads/OutTestDamaged.txt");
        if(!fileOutDamaged.exists()) {
            try {
                Files.copy(fileOut.toPath(), fileOutDamaged.toPath(), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void damagedFileDeserialization(){
        File fileOutDamaged = new File(System.getProperty("user.home") + "/Downloads/OutTestDamaged.txt");
        File fileOutDamagedDeser = new File(System.getProperty("user.home") + "/Downloads/OutTestDamagedDeser.txt");
        if (fileOutDamagedDeser.exists()) {
            fileOutDamagedDeser.delete();
        }
        Deserializator.read(fileOutDamaged);
        Deserializator.authors.forEach((object) -> Serializator.write(object, fileOutDamagedDeser));
    }
}
