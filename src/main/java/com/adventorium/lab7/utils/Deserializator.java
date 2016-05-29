package com.adventorium.lab7.utils;

import com.adventorium.lab7.music.Album;
import com.adventorium.lab7.music.Author;
import com.adventorium.lab7.music.Song;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Андрей on 27.05.2016.
 */
public class Deserializator {
    public static Collection<Author> authors;
    public static Collection<Album> albums;
    public static Collection<Song> songs;
    public static Collection<EntityForSerializator> entities;

    public static void read(File filePath) {

        authors = new HashSet<>();
        albums = new HashSet<>();
        songs = new HashSet<>();
        entities = new HashSet<>();

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {

            int numberOfObjects = in.readInt();
            for (int i = 0; i < numberOfObjects; i++) {
                Object object = in.readObject();

                if (object instanceof EntityForSerializator) {
                    entities.add(((EntityForSerializator) object));
                } else {
                    System.out.println("Wrong class");
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        divideEntities();

        turnIDsIntoLinks();

        removeEmpties();

    }

    private static void turnIDsIntoLinks() {
        for (EntityForSerializator ent : entities) {
            if (ent.getType() == Author.class) {
                Collection[] links = ent.getLinks();
                HashSet<Integer> albumsID = new HashSet<>(links[0]);
                for (Integer iterator : albumsID) {
                    for (Album album : albums) {
                        if (iterator == album.getID()) {
                            for (Author author : authors) {
                                if (author.getID() == ent.getId()) {
                                    author.addAlbum(album);
                                }
                            }
                        }
                    }
                }
            } else if (ent.getType() == Album.class) {
                Collection[] links = ent.getLinks();
                HashSet<Integer> authorsID = new HashSet<>(links[0]);
                HashSet<Integer> songsID = new HashSet<>(links[1]);
                for (Integer iterator : authorsID) {
                    for (Author author : authors) {
                        if (iterator == author.getID()) {
                            for (Album album : albums) {
                                if (album.getID() == ent.getId()) {
                                    album.addAuthor(author);
                                }
                            }
                        }
                    }
                }
                for (Integer iterator : songsID) {
                    for (Song song : songs) {
                        if (iterator == song.getID()) {
                            for (Album album : albums) {
                                if (album.getID() == ent.getId()) {
                                    album.addSong(song);
                                }
                            }
                        }
                    }
                }
            } else if (ent.getType() == Song.class) {
                Collection[] links = ent.getLinks();
                HashSet<Integer> albumsID = new HashSet<>(links[0]);
                for (Integer iterator : albumsID) {
                    for (Album album : albums) {
                        if (iterator == album.getID()) {
                            for (Song song : songs) {
                                if (song.getID() == ent.getId()) {
                                    song.addAlbum(album);
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Wrong class");
            }
        }
    }

    private static void removeEmpties() {
        Iterator<Author> iteratorAuthors = authors.iterator();
        while (iteratorAuthors.hasNext()) {
            Author author = iteratorAuthors.next();
            if (author.getAlbums().isEmpty()) {
                iteratorAuthors.remove();
            }
        }

        Iterator<Album> iteratorAlbums = albums.iterator();
        while (iteratorAlbums.hasNext()) {
            Album album = iteratorAlbums.next();
            if (album.getAuthors().isEmpty() || album.getSongs().isEmpty()) {
                iteratorAlbums.remove();
            }
        }

        Iterator<Song> iteratorSongs = songs.iterator();
        while (iteratorSongs.hasNext()) {
            Song song = iteratorSongs.next();
            if (song.getAlbums().isEmpty()) {
                iteratorSongs.remove();
            }
        }
    }

    private static void divideEntities() {
        for (EntityForSerializator ent : entities) {
            if (ent.getType() == Author.class) {
                authors.add(new Author(ent.getName(), ent.getId()));
            } else if (ent.getType() == Album.class) {
                albums.add(new Album(ent.getName(), ent.getId()));
            } else if (ent.getType() == Song.class) {
                songs.add(new Song(ent.getName(), ent.getId(), ent.getDuration()));
            } else {
                System.out.println("Wrong class");
            }
        }
    }
}
