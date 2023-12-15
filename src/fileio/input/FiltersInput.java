package fileio.input;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class FiltersInput {
    private String name;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;
    private String followers;

    /**
     * Constructor used for setting the fields of an FiltersInput object.
     */
    public FiltersInput() {
    }

    /**
     * Setter for name
     *
     * @param name the name of the song
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * Setter for album
     *
     * @param album the album of the song
     */
    public void setAlbum(final String album) {

        this.album = album;
    }

    /**
     * Setter for tags
     *
     * @param tags the tags of the song
     */
    public void setTags(final ArrayList<String> tags) {

        this.tags = tags;
    }

    /**
     * Setter for lyrics
     *
     * @param lyrics the lyrics of the song
     */
    public void setLyrics(final String lyrics) {

        this.lyrics = lyrics;
    }

    /**
     * Setter for genre
     *
     * @param genre the genre of the song
     */
    public void setGenre(final String genre) {

        this.genre = genre;
    }

    /**
     * Setter for releaseYear
     */
    public void setReleaseYear(final String releaseYear) {

        this.releaseYear = releaseYear;
    }

    /**
     * Setter for artist
     *
     * @param artist the artist of the song
     */
    public void setArtist(final String artist) {

        this.artist = artist;
    }

    /**
     * Setter for owner
     *
     * @param owner the owner of the song
     */
    public void setOwner(final String owner) {

        this.owner = owner;
    }

    /**
     * Setter for followers
     *
     * @param followers the followers of the song
     */
    public void setFollowers(final String followers) {

        this.followers = followers;
    }

    /**
     * Turn the object into a string
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "FilterInput{"
                +
                ", name='"
                + name
                + '\''
                +
                ", album='"
                + album
                + '\''
                +
                ", tags="
                + tags
                +
                ", lyrics='"
                + lyrics
                + '\''
                +
                ", genre='"
                + genre
                + '\''
                +
                ", releaseYear='"
                + releaseYear
                + '\''
                +
                ", artist='"
                + artist
                + '\''
                +
                ", owner='"
                + owner
                + '\''
                +
                ", followers='"
                + followers
                + '\''
                +
                '}';
    }
}
