package com.adventorium.lab7.serializable;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by Андрей on 25.05.2016.
 */
public class Author implements Serializable {
    static int nextID;
    final int ID;
    String name;
    HashSet<Integer> albumsID;
    //HashSet<Integer> songsID;

    @Override
    public int hashCode(){
        return ID;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Author other = (Author) obj;
        if (!name.equals(other.name))
            return false;
        if (!albumsID.equals(other.albumsID))
            return false;
        return true;
    }

    public Author(String name){
        this.name = name;
        ID=++nextID;
        albumsID = new HashSet<Integer>();
    }

    public void addAlbum(Album album){
        this.albumsID.add(album.ID);
        album.authorsID.add(this.ID);
        //this.songsID.addAll(album.songsID);
    }
}
