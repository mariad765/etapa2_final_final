package app.audio.Files;

import app.audio.Collections.AudioCollection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Album extends AudioCollection {
    private String name;
    @JsonIgnore
    private String owner;
    @JsonIgnore
    private List<Song> songsFull;

    private List<String> songs;

    /**
     * Constructor for Album
     * @param name  name of the album
     * @param owner owner of the album
     * @param songs songs in the album
     */
    public Album(final String name, final String owner, final List<SongInput> songs) {
        super(name, owner);
        this.name = name;
        this.owner = owner;
        this.songsFull = new ArrayList<>();
        this.songs = new ArrayList<>();
        for (SongInput song : songs) {
            this.songsFull.add(new Song(song.getName(),
                    song.getDuration(), this.name, song.getTags(),
                    song.getLyrics(), song.getGenre(), song.getReleaseYear(),
                    song.getArtist()));
            this.songs.add(song.getName());
        }

    }

    /**
     * Method to get the total duration of the album
     * @return total duration of the album
     */
    @JsonIgnore

    public Integer getTotalNumberOfLikes() {
        Integer total = 0;
        for (Song song : songsFull) {
            total += song.getLikes();
        }
        return total;
    }

    /**
     * Method to get the total duration of the album
     * @return total duration of the album
     */
    @JsonIgnore
    @Override
    public int getNumberOfTracks() {
        return songs.size();

    }

    /**
     * Method to get the track by index
     * @param index inde of the track
     * @return track at the given index
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songsFull.get(index);

    }
}
