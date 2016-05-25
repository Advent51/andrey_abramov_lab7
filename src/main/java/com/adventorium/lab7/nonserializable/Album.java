package com.adventorium.lab7.nonserializable;

import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Album {
    static int nextID;
    final int ID;
    String name;
    HashSet<String> genres;
    HashSet<Author> authors;
    HashSet<Song> songs;

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
        if (!authors.equals(other.authors))
            return false;
        if (!songs.equals(other.songs))
            return false;
        return true;
    }

    public Album(String name) {
        this.name = name;
        ID = ++nextID;
        genres = new HashSet<>();
        authors = new HashSet<>();
        songs = new HashSet<>();
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.albums.add(this);
    }

    public void addSong(Song song) {
        this.songs.add(song);
        song.albums.add(this);
        song.authors.addAll(this.authors);
    }
}
