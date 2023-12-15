package fileio.input;

import java.util.ArrayList;

public final class LibraryInput {
    private ArrayList<SongInput> songs;
    private ArrayList<PodcastInput> podcasts;
    private ArrayList<UserInput> users;

    /**
     * Constructor used for setting the fields of an LibraryInput object.
     */
    public LibraryInput() {
    }

    /**
     * Getter for songs
     *
     * @return the songs
     */
    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    /**
     * Setter for songs
     *
     * @param songs the songs
     */
    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    /**
     * Getter for podcasts
     *
     * @return the podcasts
     */
    public ArrayList<PodcastInput> getPodcasts() {
        return podcasts;
    }

    /**
     * Setter for podcasts
     *
     * @param podcasts the podcasts
     */
    public void setPodcasts(final ArrayList<PodcastInput> podcasts) {
        this.podcasts = podcasts;
    }

    /**
     * Getter for users
     *
     * @return the users
     */
    public ArrayList<UserInput> getUsers() {
        return users;
    }

    /**
     * Setter for users
     *
     * @param users the users
     */
    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    /**
     * toString method
     *
     * @return the string representation of the class
     */
    @Override
    public String toString() {
        return "LibraryInput{"
                +
                "songs="
                + songs
                +
                ", podcasts="
                + podcasts
                +
                ", users="
                + users
                +
                '}';
    }
}
