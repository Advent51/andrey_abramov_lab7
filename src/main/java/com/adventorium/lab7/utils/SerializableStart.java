package com.adventorium.lab7.utils;

import com.adventorium.lab7.music.Author;
import com.adventorium.lab7.music.Album;
import com.adventorium.lab7.music.Song;

import java.io.File;
import java.util.*;

/**
 * Created by Андрей on 25.05.2016.
 */
public class SerializableStart {
    public SerializableStart() {
        System.out.println(System.getProperty("user.home") + "\\Downloads\\OutTestForSerializable.txt");

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

        File fileOutForSerializable = new File(System.getProperty("user.home") + "/Downloads/OutTestForSerializable.txt");
        File fileOutForSerializableDeser = new File(System.getProperty("user.home") + "/Downloads/OutTestForSerializableDeser.txt");

        if (fileOutForSerializable.exists()) {
            fileOutForSerializable.delete();
        }

        if (fileOutForSerializableDeser.exists()) {
            fileOutForSerializableDeser.delete();
        }

        EntityForSerializator[] allData = createOneArray(authors, albums, songs);
        Serializator.write(allData, fileOutForSerializable);
        Deserializator.read(fileOutForSerializable);

        allData = createOneArray(Deserializator.authors, Deserializator.albums, Deserializator.songs);
        Serializator.write(allData, fileOutForSerializableDeser);
    }

    public EntityForSerializator[] createOneArray(Collection<Author> authors, Collection<Album> albums, Collection<Song> songs) {
        EntityForSerializator[] entities = new EntityForSerializator[authors.toArray().length + albums.toArray().length + songs.toArray().length];
        int entityIterator = 0;
        for (Author au : authors) {
            entities[entityIterator] = new EntityForSerializator(au);
            entityIterator++;
        }
        for (Album al : albums) {
            entities[entityIterator] = new EntityForSerializator(al);
            entityIterator++;
        }
        for (Song sg : songs) {
            entities[entityIterator] = new EntityForSerializator(sg);
            entityIterator++;
        }
        return entities;
    }
}
