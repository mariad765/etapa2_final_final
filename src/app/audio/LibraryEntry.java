package app.audio;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class LibraryEntry {
    private final String name;

    /**
     * Constructor for LibraryEntry
     * @param name name of the library entry
     */
    public LibraryEntry(final String name) {
        this.name = name;
    }

    /**
     * Returns if the name is the same
     * @param nameQuerry name
     * @return true or false
     */
    public boolean matchesName(final String nameQuerry) {
        return getName().toLowerCase().startsWith(nameQuerry.toLowerCase());
    }

    /**
     * Returns if the album is the same
     * @param album album
     * @return true or false
     */
    public boolean matchesAlbum(final String album) {
        return false; }

    /**
     * Returns if the tags are the same
     * @param tags tags
     * @return true or false
     */
    public boolean matchesTags(final ArrayList<String> tags) {
        return false; }

    /**
     * Returns if the lyrics are the same
     * @param lyrics lyrics
     * @return true or false
     */
    public boolean matchesLyrics(final String lyrics) {
        return false; }

    /**
     * Returns if the genre is the same
     * @param genre  genre
     * @return true or false
     */
    public boolean matchesGenre(final String genre) {
        return false; }

    /**
     * Returns if the artist is the same
     * @param artist artist
     * @return  true or false
     */
    public boolean matchesArtist(final String artist) {
        return false; }

    /**
     * Returns if the release year is the same
     * @param releaseYear release year
     * @return true or false
     */
    public boolean matchesReleaseYear(final String releaseYear) {
        return false; }

    /**
     * Returns if the owner is the same
     * @param user user
     * @return true or false
     */
    public boolean matchesOwner(final String user) {
        return false; }

    /**
     * Returns if the user is the same
     * @param user user
     * @return true or false
     */
    public boolean isVisibleToUser(final String user) {
        return false; }

    /**
     * Returns if the followers are the same
     * @param followers followers
     * @return true or false
     */
    public boolean matchesFollowers(final String followers) {
        return false; }
}
