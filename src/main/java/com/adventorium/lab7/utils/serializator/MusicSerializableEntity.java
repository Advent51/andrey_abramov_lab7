package com.adventorium.lab7.utils.serializator;

import com.adventorium.lab7.music.*;
import com.adventorium.lab7.music.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Андрей on 27.05.2016.
 */
public class MusicSerializableEntity implements Serializable {
    private final String name;
    private final Class type;
    private Collection<SerializableAuthor> authors;
    private Collection<SerializableAlbum> albums;
    private Collection<SerializableSong> songs;
    private Collection<String> genres;
    private long duration;

    private class SerializableAuthor extends Author implements Serializable {
        String name;

        SerializableAuthor(String name) {
            super(name);
        }
    }

    private class SerializableAlbum extends Album implements Serializable {
        String name;

        SerializableAlbum(String name) {
            super(name);
        }
    }

    private class SerializableSong extends Song implements Serializable {
        String name;

        SerializableSong(String name) {
            super(name);
        }
    }

    public MusicSerializableEntity(MusicStuff object) {
        this.type = object.getClass();
        this.name = object.getName();
        if (object.getClass() == Author.class) {
            albums = new ArrayList<>();
            for (Album album : ((Author) object).getAlbums()) {
                SerializableAlbum serializableAlbum = new SerializableAlbum(album.getName());
                albums.add(serializableAlbum);
            }
        }
        if (object.getClass() == Album.class) {
            authors = new ArrayList<>();
            for (Author author : ((Album) object).getAuthors()) {
                SerializableAuthor serializableAuthor = new SerializableAuthor(author.getName());
                authors.add(serializableAuthor);
            }
            songs = new ArrayList<>();
            for (Song song : ((Album) object).getSongs()) {
                SerializableSong serializableSong = new SerializableSong(song.getName());
                songs.add(serializableSong);
            }
            genres = new ArrayList<>(((Album) object).getGenres());
        }
        if (object.getClass() == Song.class) {
            albums = new ArrayList<>();
            for (Album album : ((Song) object).getAlbums()) {
                SerializableAlbum serializableAlbum = new SerializableAlbum(album.getName());
                albums.add(serializableAlbum);
            }
            this.duration = ((Song) object).getDuration();
        }
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public long getDuration() {
        return duration;
    }

    public Collection<Author> getAuthors() {
        ArrayList<Author> returnedAuthors = new ArrayList<>();
        returnedAuthors.addAll(authors);
        return returnedAuthors;
    }

    public Collection<Album> getAlbums() {
        ArrayList<Album> returnedAlbums = new ArrayList<>();
        returnedAlbums.addAll(albums);
        return returnedAlbums;
    }

    public Collection<Song> getSongs() {
        ArrayList<Song> returnedSongs = new ArrayList<>();
        returnedSongs.addAll(songs);
        return returnedSongs;
    }

    public Collection<String> getGenres() {
        return genres;
    }
}
