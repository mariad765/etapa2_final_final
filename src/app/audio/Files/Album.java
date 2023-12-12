package app.audio.Files;

import app.audio.Collections.AudioCollection;
import app.audio.LibraryEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;

public class Album extends AudioCollection {
    private String name;

    //ignore the owner when putting in json file
    @JsonIgnore
    private String owner;
    @JsonIgnore
    private List<Song> songsFull;

    private List<String> songs;

    public Album(String name, String owner, List<SongInput> songs) {
        super(name, owner);
        this.name = name;
        this.owner = owner;
        this.songsFull = new ArrayList<>();
        this.songs = new ArrayList<>();
        //init songs
        for (SongInput song : songs) {
            this.songsFull.add(new Song(song.getName(), song.getDuration(), this.name, song.getTags(),
                    song.getLyrics(), song.getGenre(), song.getReleaseYear(), song.getArtist()));
            this.songs.add(song.getName());
        }

    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public List<Song> getSongsFull() {
        return songsFull;
    }
    public List<String> getSongs() {
        return songs;
    }
    @JsonIgnore

    public Integer getTotalNumberOfLikes() {
        Integer total = 0;
        for (Song song : songsFull) {
            total += song.getLikes();
        }
        return total;
    }
    @JsonIgnore
    @Override
    public int getNumberOfTracks() {
        //get songs in albume
        return songs.size();

    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        //get song by index
        return songsFull.get(index);

    }
}