package com.adventorium.lab7.utils.serializator;

import com.adventorium.lab7.music.Album;
import com.adventorium.lab7.music.Author;
import com.adventorium.lab7.music.Song;
import com.adventorium.lab7.utils.ModelCreator;

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
            String readLine = in.readLine();
            readAuthor(readLine, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ModelCreator.getEntitiesArray(authors, albums, songs);
    }

    private void readAuthor(String readLine, BufferedReader in) throws IOException {
        while (readLine != null) {
            if (readLine.startsWith(AUTHOR_INTRO)) {
                Author author = new Author(readLine.substring(AUTHOR_INTRO_LENGTH));
                readLine = in.readLine();
                readAlbum(readLine, in, author);
                if (author.getAlbums().isEmpty()) {
                    authors.remove(author);
                } else {
                    authors.add(author);
                }
            } else {
                readLine = in.readLine();
            }
        }
    }

    private void readAlbum(String readLine, BufferedReader in, Author author) throws IOException {
        while (readLine != null && readLine.startsWith(ALBUM_INTRO)) {
            Album album = new Album(readLine.substring(ALBUM_INTRO_LENGTH));
            albums.add(album);
            readLine = in.readLine();
            if (readLine != null && readLine.startsWith(GENRE_INTRO)) {
                album.addGenre(readLine.substring(GENRE_INTRO_LENGTH));
                readLine = in.readLine();
            }
            readSong(readLine, in, album);
            if (album.getSongs().isEmpty()) {
                albums.remove(album);
            } else {
                author.addAlbum(album);
            }
        }
    }

    private void readSong(String readLine, BufferedReader in, Album album) throws IOException {
        while (readLine != null && readLine.startsWith(SONG_INTRO)) {
            Song song = new Song(readLine.substring(SONG_INTRO_LENGTH));
            songs.add(song);
            album.addSong(song);
            readLine = in.readLine();
            if (readLine != null && readLine.startsWith(DURATION_INTRO)) {
                song.addDuration(Integer.parseInt(readLine.substring(DURATION_INTRO_LENGTH)));
                readLine = in.readLine();
            } else {
                readLine = in.readLine();
            }
        }
    }

        public static Collection<Author> getAuthors () {
            return authors;
        }
    }
