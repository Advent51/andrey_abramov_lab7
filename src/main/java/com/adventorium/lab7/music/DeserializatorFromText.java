package com.adventorium.lab7.music;

import java.io.*;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class DeserializatorFromText {
    public static HashSet<Author> authors;
    public static HashSet<Album> albums;
    public static HashSet<Song> songs;

    public static void read(File fileIn) {

        authors = new HashSet<>();
        albums = new HashSet<>();
        songs = new HashSet<>();

        try (BufferedReader in = new BufferedReader(new FileReader(fileIn))) {
            String readedLine = in.readLine();
            while (readedLine != null) {
                if (readedLine.startsWith("Author: ")) {
                    Author author = new Author(readedLine.substring(8));
                    readedLine = in.readLine();
                    while (readedLine != null && readedLine.startsWith("\tAlbum: ")) {
                        Album album = new Album(readedLine.substring(8));
                        albums.add(album);
                        readedLine = in.readLine();
                        if (readedLine != null && readedLine.startsWith("\t\tGenre: ")) {
                            album.addGenre(readedLine.substring(9));
                            readedLine = in.readLine();
                            while (readedLine != null && readedLine.startsWith("\t\t\tSong: ")) {
                                Song song = new Song(readedLine.substring(9));
                                songs.add(song);
                                album.addSong(song);
                                readedLine = in.readLine();
                                if (readedLine != null && readedLine.startsWith("\t\t\t\tDuration: ")) {
                                    song.addDuration(Integer.parseInt(readedLine.substring(14)));
                                    readedLine = in.readLine();
                                } else {
                                    readedLine = in.readLine();
                                }
                            }
                        } else while (readedLine != null && readedLine.startsWith("\t\t\tSong: ")) {
                            Song song = new Song(readedLine.substring(12));
                            songs.add(song);
                            album.addSong(song);
                            readedLine = in.readLine();
                            if (readedLine != null && readedLine.startsWith("\t\t\t\tDuration: ")) {
                                song.addDuration(Integer.parseInt(readedLine.substring(14)));
                                readedLine = in.readLine();
                            } else {
                                readedLine = in.readLine();
                            }
                        }
                        if (album.songs.isEmpty()) {
                            albums.remove(album);
                        } else {
                            author.addAlbum(album);
                        }
                    }
                    if (author.albums.isEmpty()) {
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

    //Well, I've just forgot about it
    public static void readWithTokenizer(File fileIn) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileIn))) {
            StreamTokenizer st = new StreamTokenizer(in);
            boolean eof = false;
            do {
                int token = st.nextToken();
                switch (token) {
                    case StreamTokenizer.TT_EOF:
                        System.out.println("End of File encountered.");
                        eof = true;
                        break;
                    case StreamTokenizer.TT_EOL:
                        System.out.println("End of Line encountered.");
                        break;
                    case StreamTokenizer.TT_WORD:
                        System.out.println("Word: " + st.sval);
                        break;
                    case StreamTokenizer.TT_NUMBER:
                        System.out.println("Number: " + st.nval);
                        break;
                    default:
                        System.out.println((char) token + " encountered.");
                        if (token == '!') {
                            eof = true;
                        }
                }
            } while (!eof);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
