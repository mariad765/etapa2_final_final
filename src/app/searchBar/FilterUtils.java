package app.searchBar;

import app.audio.LibraryEntry;

import java.util.ArrayList;
import java.util.List;

public final class FilterUtils {
    // private constructor
    private FilterUtils() {
    }
    /**
     * Filter by name
     * @param entries list of entries
     * @param name name of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByName(
            final List<LibraryEntry> entries, final String name) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (entry.matchesName(name)) {
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * Filter by album
     * @param entries list of entries
     * @param album album of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByAlbum(
            final List<LibraryEntry> entries, final String album) {
        return filter(entries, entry -> entry.matchesAlbum(album));
    }

    /**
     * Filter by tags
     * @param entries list of entries
     * @param tags  tags of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByTags(
            final List<LibraryEntry> entries,
            final ArrayList<String> tags) {
        return filter(entries, entry -> entry.matchesTags(tags));
    }

    /**
     * Filter by lyrics
     * @param entries list of entries
     * @param lyrics lyrics of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByLyrics(
            final List<LibraryEntry> entries, final String lyrics) {
        return filter(entries, entry -> entry.matchesLyrics(lyrics));
    }

    /**
     * Filter by genre
     * @param entries list of entries
     * @param genre genre of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByGenre(
            final List<LibraryEntry> entries, final String genre) {
        return filter(entries, entry -> entry.matchesGenre(genre));
    }

    /**
     * Filter by artist
     * @param entries list of entries
     * @param artist  artist of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByArtist(
            final List<LibraryEntry> entries,
            final String artist) {
        return filter(entries, entry -> entry.matchesArtist(artist));
    }

    /**
     * Filter by release year
     * @param entries list of entries
     * @param releaseYear release year of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByReleaseYear(
            final List<LibraryEntry> entries, final String releaseYear) {
        return filter(entries, entry -> entry.matchesReleaseYear(releaseYear));
    }

    /**
     * Filter by owner
     * @param entries list of entries
     * @param user user of the entry
     * @return list of entries
     */
    public static List<LibraryEntry> filterByOwner(
            final List<LibraryEntry> entries, final String user) {
        return filter(entries, entry -> entry.matchesOwner(user));
    }
/**
 * Filter by playlist visibility
 * @param entries list of entries
 *  @param user user of the entry
 *              @return list of entries
 */
    public static List<LibraryEntry> filterByPlaylistVisibility(
            final List<LibraryEntry> entries, final String user) {
        return filter(entries, entry -> entry.isVisibleToUser(user));
    }

    /**
     * Filter by followers
     * @param entries list of entries
     * @param followers followers of the entry
     * @return  list of entries
     */
    public static List<LibraryEntry> filterByFollowers(
            final List<LibraryEntry> entries, final String followers) {
        return filter(entries, entry -> entry.matchesFollowers(followers));
    }

    /**
     * Filter by podcast name
     * @param entries list of entries
     * @param criteria criteria of the entry
     * @return list of entries
     */
    private static List<LibraryEntry> filter(
            final List<LibraryEntry> entries,
            final FilterCriteria criteria) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (criteria.matches(entry)) {
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * Filter criteria
     */
    @FunctionalInterface
    private interface FilterCriteria {
        boolean matches(LibraryEntry entry);
    }
}
