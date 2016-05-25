package com.adventorium.lab7.serializable;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Deserializator {

    public static HashSet<Author> authors;
    public static HashSet<Album> albums;
    public static HashSet<Song> songs;

    public static void read(File filePath) {

        authors = new HashSet<>();
        albums = new HashSet<>();
        songs = new HashSet<>();

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {

            int numberOfObjects = in.readInt();
            for (int i = 0; i < numberOfObjects; i++) {
                Object object = in.readObject();

                if (object instanceof Author) {
                    if (!((Author) object).albumsID.isEmpty()) {
                        authors.add((Author) object);
                    }
                } else if (object instanceof Album) {
                    if (!((Album) object).songsID.isEmpty() && !((Album) object).authorsID.isEmpty()) {
                        albums.add((Album) object);
                    }
                } else if (object instanceof Song) {
                    if (!((Song) object).albumsID.isEmpty()) {
                        songs.add((Song) object);
                    }
                } else {
                    System.out.println("Wrong class");
                }
            }

            for(Author author : authors){
                Iterator<Integer> albumsID =  author.albumsID.iterator();
                while(albumsID.hasNext()){
                    int varID = albumsID.next();
                    boolean exists=false;
                    for(Album al : albums){
                        if(varID==al.ID){
                            exists=true;
                        }
                    }
                    if (!exists){
                        System.out.println("Removed empty albumsID from author: "+varID);
                        albumsID.remove();
                    }
                }
            }

            for(Song song : songs){
                Iterator<Integer> albumsID =  song.albumsID.iterator();
                while(albumsID.hasNext()){
                    int varID = albumsID.next();
                    boolean exists=false;
                    for(Album al : albums){
                        if(varID==al.ID){
                            exists=true;
                        }
                    }
                    if (!exists){
                        System.out.println("Removed empty albumsID from song: "+varID);
                        albumsID.remove();
                    }
                }
            }

            for(Album album : albums){
                Iterator<Integer> authorsID =  album.authorsID.iterator();
                while(authorsID.hasNext()){
                    int varID = authorsID.next();
                    boolean exists=false;
                    for(Author au : authors){
                        if(varID==au.ID){
                            exists=true;
                        }
                    }
                    if (!exists){
                        System.out.println("Removed empty authorsID: "+varID);
                        authorsID.remove();
                    }
                }
                Iterator<Integer> songsID =  album.songsID.iterator();
                while(songsID.hasNext()){
                    int varID = songsID.next();
                    boolean exists=false;
                    for(Song sn : songs){
                        if(varID==sn.ID){
                            exists=true;
                        }
                    }
                    if (!exists){
                        System.out.println("Removed empty songsID: "+varID);
                        songsID.remove();
                    }
                }
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
