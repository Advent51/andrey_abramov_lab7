package com.adventorium.lab7.music;

import java.io.*;

/**
 * Created by Андрей on 25.05.2016.
 */
public class SerializatorIntoText {
    public static void write(Object object, File filePath) {

        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(filePath, true)), true)) {

            if (object instanceof Author) {
                out.println("Author: " + ((Author) object).getName());
                ((Author) object).albums.forEach((Album album) -> SerializatorIntoText.write(album, filePath));
            } else if (object instanceof Album) {
                out.println("\tAlbum: " + ((Album) object).getName());
                out.print("\t\tGenre: ");
                ((Album) object).genres.forEach((String genre) -> out.print(genre + " "));
                out.println();
                ((Album) object).songs.forEach((Song song) -> SerializatorIntoText.write(song, filePath));
            } else if (object instanceof Song) {
                out.println("\t\t\t" + "Song: " + ((Song) object).getName() + "\n\t\t\t\tDuration: " + ((Song) object).getDuration());
            } else {
                System.out.println("Wrong class");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
