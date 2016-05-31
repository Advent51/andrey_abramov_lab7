package com.adventorium.lab7.utils.serializator;

import com.adventorium.lab7.music.Album;
import com.adventorium.lab7.music.Author;
import com.adventorium.lab7.music.Song;
import com.adventorium.lab7.utils.ModelCreator;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Андрей on 25.05.2016.
 */
public class BinarySerializator implements Serializator {
    public void write(Object[] object, OutputStream outputStream) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {

            out.writeInt(object.length);
            for (Object obj : object) {
                out.writeObject(obj);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Object object, OutputStream outputStream) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {
            out.writeInt(1);
            out.writeObject(object);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Author> read(InputStream inputStream) {

        Collection<Author> authors = new HashSet<>();
        Collection<Album> albums = new HashSet<>();
        Collection<Song> songs = new HashSet<>();
        Collection<MusicSerializableEntity> entities = new HashSet<>();

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(inputStream))) {

            int numberOfObjects = in.readInt();
            for (int i = 0; i < numberOfObjects; i++) {
                Object object = in.readObject();

                if (object instanceof MusicSerializableEntity) {
                    entities.add(((MusicSerializableEntity) object));
                } else {
                    System.out.println("Wrong class");
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        divideEntities(authors, albums, songs, entities);

        removeEmptySong(songs);
        removeEmptyAlbum(albums);
        removeEmptyAuthor(authors);

        return authors;

    }

    private static void removeEmptyAuthor(Collection<Author> authors) {
        Iterator<Author> iteratorAuthors = authors.iterator();
        while (iteratorAuthors.hasNext()) {
            Author author = iteratorAuthors.next();
            if (author.getAlbums().isEmpty()) {
                iteratorAuthors.remove();
            }
        }
    }

    private static void removeEmptyAlbum(Collection<Album> albums) {
        Iterator<Album> iteratorAlbums = albums.iterator();
        while (iteratorAlbums.hasNext()) {
            Album album = iteratorAlbums.next();
            if (album.getAuthors().isEmpty() || album.getSongs().isEmpty()) {
                iteratorAlbums.remove();
            }
        }
    }

    private static void removeEmptySong(Collection<Song> songs) {
        Iterator<Song> iteratorSongs = songs.iterator();
        while (iteratorSongs.hasNext()) {
            Song song = iteratorSongs.next();
            if (song.getAlbums().isEmpty()) {
                iteratorSongs.remove();
            }
        }
    }

    private static void divideEntities(Collection<Author> authors, Collection<Album> albums,
                                       Collection<Song> songs, Collection<MusicSerializableEntity> entities) {
        for (MusicSerializableEntity ent : entities) {
            if (ent.getType() == Author.class) {
                Author author = new Author(ent.getName());
                author.setAlbums(ent.getAlbums());
                authors.add(author);
            } else if (ent.getType() == Album.class) {
                Album album = new Album(ent.getName());
                album.setAuthors(ent.getAuthors());
                album.setSongs(ent.getSongs());
                album.addGenre(ent.getGenres());
                albums.add(album);
            } else if (ent.getType() == Song.class) {
                Song song = new Song(ent.getName(), ent.getDuration());
                song.setAlbums(ent.getAlbums());
                songs.add(song);
            } else {
                System.out.println("Wrong class");
            }
        }
    }
}
