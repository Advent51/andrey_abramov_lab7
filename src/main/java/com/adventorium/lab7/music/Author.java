package com.adventorium.lab7.music;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Author implements MusicStuff {
    private static int nextID;
    private final int id;
    private final String name;
    transient Collection<Album> albums;

    @Override
    public int hashCode() {
        return id;
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
        id = ++nextID;
        albums = new HashSet<>();
    }

    public Author(String name, int id) {
        this.name = name;
        this.id = id;
        albums = new HashSet<>();
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
        album.authors.add(this);
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public Collection[] getLinks() {
        HashSet[] ll = new HashSet[1];
        ll[0] = new HashSet<Integer>();
        for (Album al : albums) {
            ll[0].add(al.getID());
        }
        return ll;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Collection<Album> getAlbums() {
        return albums;
    }
}