package com.adventorium.lab7.serializable;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Song implements Serializable {
    static int nextID;
    final int ID;
    String name;
    long duration;
    HashSet<Integer> authorsID;
    HashSet<Integer> albumsID;

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
        if (!authorsID.equals(other.authorsID))
            return false;
        if (!albumsID.equals(other.albumsID))
            return false;
        return true;
    }

    public Song(String name, long duration){
        this.name = name;
        ID=++nextID;
        this.duration = duration;
        authorsID = new HashSet<>();
        albumsID = new HashSet<>();
    }

    public void addAuthor(Author author){
        this.authorsID.add(author.ID);
    }

    public void addAlbum(Album album){
        this.albumsID.add(album.ID);
        album.songsID.add(this.ID);
    }
}
