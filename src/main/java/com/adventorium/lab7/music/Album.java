package com.adventorium.lab7.music;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Album implements MusicStuff {
    private static int nextID;
    private final int id;
    private final String name;
    Collection<String> genres;
    transient Collection<Author> authors;
    transient Collection<Song> songs;

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
        id = ++nextID;
        genres = new HashSet<>();
        authors = new HashSet<>();
        songs = new HashSet<>();
    }

    public Album(String name, int id) {
        this.name = name;
        this.id = id;
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
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public Collection[] getLinks() {
        HashSet[] ll = new HashSet[2];
        ll[0] = new HashSet<Integer>();
        for (Author au : authors) {
            ll[0].add(au.getID());
        }
        ll[1] = new HashSet<Integer>();
        for (Song sg : songs) {
            ll[1].add(sg.getID());
        }
        return ll;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public Collection<Song> getSongs() {
        return songs;
    }
}
