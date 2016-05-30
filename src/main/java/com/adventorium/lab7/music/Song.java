package com.adventorium.lab7.music;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Song implements MusicStuff, Serializable {
    private final String name;
    private long duration;
    transient Collection<Album> albums;

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
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
        albums = new HashSet<>();
    }

    public Song(String name, long duration) {
        this.name = name;
        this.duration = duration;
        albums = new HashSet<>();
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
        album.songs.add(this);
    }

    public void addAlbum(Collection<Album> albums) {
        this.albums.addAll(albums);
        for (Album album : albums) {
            album.songs.add(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Collection<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Collection<Album> albums) {
        this.albums = albums;
    }

    public void addDuration(int duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return this.duration;
    }
}
