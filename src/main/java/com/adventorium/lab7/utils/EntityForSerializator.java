package com.adventorium.lab7.utils;

import com.adventorium.lab7.music.*;
import com.adventorium.lab7.music.Song;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Андрей on 27.05.2016.
 */
public class EntityForSerializator implements Serializable {
    private final String name;
    private final int id;
    private final Class type;
    private Collection[] links;
    private long duration;

    public EntityForSerializator(MusicStuff object) {
        this.type = object.getClass();
        this.id = object.getID();
        this.name = object.getName();
        this.links = object.getLinks().clone();
        if (object instanceof com.adventorium.lab7.music.Song) {
            this.duration = ((Song) object).getDuration();
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Class getType() {
        return type;
    }

    public Collection[] getLinks() {
        return links;
    }

    public long getDuration() {
        return duration;
    }
}
