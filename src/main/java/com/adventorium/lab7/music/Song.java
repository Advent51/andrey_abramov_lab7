package com.adventorium.lab7.music;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Song implements MusicStuffInterface {
    private static int nextID;
    private final int id;
    private final String name;
    private long duration;
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
        Song other = (Song) obj;
        if (!name.equals(other.name))
            return false;
        if (duration != other.duration)
            return false;
        if (!albums.equals(other.albums))
            return false;
        return true;
    }

    public Song(String name) {
        this.name = name;
        id = ++nextID;
        albums = new HashSet<>();
    }

    public Song(String name, long duration) {
        this.name = name;
        id = ++nextID;
        this.duration = duration;
        albums = new HashSet<>();
    }

    public Song(String name, int id, long duration) {
        this.name = name;
        this.id = id;
        this.duration = duration;
        albums = new HashSet<>();
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
        album.songs.add(this);
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public Collection[] getLinks() {
        HashSet[] links = new HashSet[1];
        links[0] = new HashSet<Integer>();
        for (Album album : albums) {
            links[0].add(album.getID());
        }
        return links;
    }

    @Override
    public String toString() {
        String string = "Song: " + name + "\n\t\t\t\tDuration: " + duration;
        return string;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Collection<Album> getAlbums() {
        return albums;
    }

    public void addDuration(int duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return this.duration;
    }
}
