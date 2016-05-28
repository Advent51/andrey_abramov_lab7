package com.adventorium.lab7.music;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Album implements MusicStuffInterface {
    private static int nextID;
    private final int id;
    private final String name;
    private Collection<String> genres;
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
        HashSet[] links = new HashSet[2];
        links[0] = new HashSet<Integer>();
        for (Author author : authors) {
            links[0].add(author.getID());
        }
        links[1] = new HashSet<Integer>();
        for (Song song : songs) {
            links[1].add(song.getID());
        }
        return links;
    }

    @Override
    public String toString() {
        String string = "Album: " + name + "\n\t\tGenre: " + genres;
        for (Song song : songs) {
            string += "\n\t\t\t" + song.toString();
        }
        return string;
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

    public Collection<String> getGenres() {
        return genres;
    }
}
