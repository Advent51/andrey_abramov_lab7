package com.adventorium.lab7.nonserializable;

import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Author {
    static int nextID;
    final int ID;
    String name;
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
        Author other = (Author) obj;
        if (!name.equals(other.name))
            return false;
        if (!albums.equals(other.albums))
            return false;
        return true;
    }

    public Author(String name) {
        this.name = name;
        ID = ++nextID;
        albums = new HashSet<Album>();
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
        album.authors.add(this);
    }
}
