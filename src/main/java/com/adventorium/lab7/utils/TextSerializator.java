package com.adventorium.lab7.utils;

import com.adventorium.lab7.music.Album;
import com.adventorium.lab7.music.Author;
import com.adventorium.lab7.music.Song;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class TextSerializator implements Serializator {

    private static HashSet<Author> authors = new HashSet<>();
    private static HashSet<Album> albums = new HashSet<>();
    private static HashSet<Song> songs = new HashSet<>();

    private static final String AUTHOR_INTRO = "Author: ";
    private static final String ALBUM_INTRO = "\tAlbum: ";
    private static final String GENRE_INTRO = "\t\tGenre: ";
    private static final String SONG_INTRO = "\t\t\tSong: ";
    private static final String DURATION_INTRO = "\t\t\t\tDuration: ";

    private static final int AUTHOR_INTRO_LENGTH = AUTHOR_INTRO.length();
    private static final int ALBUM_INTRO_LENGTH = ALBUM_INTRO.length();
    private static final int GENRE_INTRO_LENGTH = GENRE_INTRO.length();
    private static final int SONG_INTRO_LENGTH = SONG_INTRO.length();
    private static final int DURATION_INTRO_LENGTH = DURATION_INTRO.length();

    public void write(Object object, OutputStream outputStream) {
        PrintStream out = new PrintStream(outputStream, false);

        if (object.getClass() == Author.class) {
            out.println(AUTHOR_INTRO + ((Author) object).toString());
            for (Album album : ((Author) object).getAlbums()) {
                out.println(ALBUM_INTRO + album.toString());
                out.println(GENRE_INTRO + album.getGenres());
                for (Song song : album.getSongs()) {
                    out.println(SONG_INTRO + song.toString());
                    out.println(DURATION_INTRO + song.getDuration());
                }
            }
        } else if (object.getClass() == Album.class) {
            out.println(ALBUM_INTRO + ((Album) object).toString());
            out.println(GENRE_INTRO + ((Album) object).getGenres());
            for (Song song : ((Album) object).getSongs()) {
                out.println(SONG_INTRO + song.toString());
                out.println(DURATION_INTRO + song.getDuration());
            }
        } else if (object.getClass() == Song.class) {
            out.println(SONG_INTRO + ((Song) object).toString());
        }
        out.flush();
    }

    public void write(Object[] object, OutputStream outputStream) {
        for (Object obj : object) {
            write(obj, outputStream);
        }
    }

    public MusicSerializableEntity[] read(InputStream inputStream) {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String readedLine = in.readLine();
            while (readedLine != null) {
                if (readedLine.startsWith(AUTHOR_INTRO)) {
                    Author author = new Author(readedLine.substring(AUTHOR_INTRO_LENGTH));
                    readedLine = in.readLine();
                    while (readedLine != null && readedLine.startsWith(ALBUM_INTRO)) {
                        Album album = new Album(readedLine.substring(ALBUM_INTRO_LENGTH));
                        albums.add(album);
                        readedLine = in.readLine();
                        if (readedLine != null && readedLine.startsWith(GENRE_INTRO)) {
                            album.addGenre(readedLine.substring(GENRE_INTRO_LENGTH));
                            readedLine = in.readLine();
                        }
                        while (readedLine != null && readedLine.startsWith(SONG_INTRO)) {
                            Song song = new Song(readedLine.substring(SONG_INTRO_LENGTH));
                            songs.add(song);
                            album.addSong(song);
                            readedLine = in.readLine();
                            if (readedLine != null && readedLine.startsWith(DURATION_INTRO)) {
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

        return ModelCreator.getEntitiesArray(authors, albums, songs);
    }

    public static Collection<Author> getAuthors() {
        return authors;
    }
}
