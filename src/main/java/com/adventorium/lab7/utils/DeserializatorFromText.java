package com.adventorium.lab7.utils;

import com.adventorium.lab7.music.Album;
import com.adventorium.lab7.music.Author;
import com.adventorium.lab7.music.Song;

import java.io.*;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class DeserializatorFromText {
    private static HashSet<Author> authors;
    private static HashSet<Album> albums;
    private static HashSet<Song> songs;

    public static HashSet<Author> getAuthors() {
        return authors;
    }

    public static void read(File fileIn) {

        authors = new HashSet<>();
        albums = new HashSet<>();
        songs = new HashSet<>();

        int AUTHOR_INTRO_LENGTH = "Author: ".length();
        int ALBUM_INTRO_LENGTH = "\tAlbum: ".length();
        int GENRE_INTRO_LENGTH = "\t\tGenre: ".length();
        int SONG_INTRO_LENGTH = "\t\t\tSong: ".length();
        int DURATION_INTRO_LENGTH = "\t\t\t\tDuration: ".length();

        try (BufferedReader in = new BufferedReader(new FileReader(fileIn))) {
            String readedLine = in.readLine();
            while (readedLine != null) {
                if (readedLine.startsWith("Author: ")) {
                    Author author = new Author(readedLine.substring(AUTHOR_INTRO_LENGTH));
                    readedLine = in.readLine();
                    while (readedLine != null && readedLine.startsWith("\tAlbum: ")) {
                        Album album = new Album(readedLine.substring(ALBUM_INTRO_LENGTH));
                        albums.add(album);
                        readedLine = in.readLine();
                        if (readedLine != null && readedLine.startsWith("\t\tGenre: ")) {
                            album.addGenre(readedLine.substring(GENRE_INTRO_LENGTH));
                            readedLine = in.readLine();
                        }
                        while (readedLine != null && readedLine.startsWith("\t\t\tSong: ")) {
                            Song song = new Song(readedLine.substring(SONG_INTRO_LENGTH));
                            songs.add(song);
                            album.addSong(song);
                            readedLine = in.readLine();
                            if (readedLine != null && readedLine.startsWith("\t\t\t\tDuration: ")) {
                                song.addDuration(Integer.parseInt(readedLine.substring(DURATION_INTRO_LENGTH)));
                                readedLine = in.readLine();
                            } else {
                                readedLine = in.readLine();
                            }
                        }
                        if (album.getSongs().isEmpty()) {
                            albums.remove(album);
                        } else {
                            author.addAlbum(album);
                        }
                    }
                    if (author.getAlbums().isEmpty()) {
                        authors.remove(author);
                    } else {
                        authors.add(author);
                    }
                } else {
                    readedLine = in.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
