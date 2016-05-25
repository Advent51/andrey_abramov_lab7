package com.adventorium.lab7.nonserializable;

import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Song {
    static int nextID;
    final int ID;
    String name;
    long duration;
    HashSet<Author> authors;
    HashSet<Album> albums;

    @Override
    public int hashCode() {
        return ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Song other = (Song) obj;
        if (!name.equals(other.name))
            return false;
        if (duration != other.duration)
            return false;
        if (!authors.equals(other.authors))
            return false;
        if (!albums.equals(other.albums))
            return false;
        return true;
    }

    public Song(String name){
        this.name = name;
        ID=++nextID;
        authors = new HashSet<>();
        albums = new HashSet<>();
    }

    public Song(String name, long duration){
        this.name = name;
        ID=++nextID;
        this.duration = duration;
        authors = new HashSet<>();
        albums = new HashSet<>();
    }

    public void addAuthor(Author author){
        this.authors.add(author);
    }

    public void addAlbum(Album album){
        this.albums.add(album);
        album.songs.add(this);
    }

    public void addDuration(int duration){
        this.duration = duration;
    }
}
