package com.adventorium.lab7.serializable;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Album implements Serializable {
    static int nextID;
    final int ID;
    String name;
    HashSet<String> genres;
    HashSet<Integer> authorsID;
    HashSet<Integer> songsID;

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
        Album other = (Album) obj;
        if (!name.equals(other.name))
            return false;
        if (!genres.equals(other.genres))
            return false;
        if (!authorsID.equals(other.authorsID))
            return false;
        if (!songsID.equals(other.songsID))
            return false;
        return true;
    }

    public Album(String name){
        this.name = name;
        ID=++nextID;
        genres = new HashSet<>();
        authorsID = new HashSet<>();
        songsID = new HashSet<>();
    }

    public void addGenre(String genre){
        this.genres.add(genre);
    }

    public void addAuthor(Author author){
        this.authorsID.add(author.ID);
        author.albumsID.add(this.ID);
    }

    public void addSong(Song song){
        this.songsID.add(song.ID);
        song.albumsID.add(this.ID);
        song.authorsID.addAll(this.authorsID);
    }
}
