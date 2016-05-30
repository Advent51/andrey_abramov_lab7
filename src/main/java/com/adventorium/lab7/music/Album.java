package com.adventorium.lab7.music;

import com.adventorium.lab7.utils.MusicSerializableEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Album implements MusicStuff, Serializable {
    private final String name;
    private Collection<String> genres;
    transient Collection<Author> authors;
    transient Collection<Song> songs;

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
        genres = new HashSet<>();
        authors = new HashSet<>();
        songs = new HashSet<>();
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void addGenre(Collection<String> genre) {
        this.genres.addAll(genre);
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.albums.add(this);
    }

    public void addAuthor(Collection<Author> authors) {
        this.authors.addAll(authors);
        for (Author author : authors) {
            author.albums.add(this);
        }
    }

    public void addSong(Song song) {
        this.songs.add(song);
        song.albums.add(this);
    }

    public void addSong(Collection<Song> songs) {
        this.songs.addAll(songs);
        for (Song song : songs) {
            song.albums.add(this);
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

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

    public Collection<String> getGenres() {
        return genres;
    }
}
