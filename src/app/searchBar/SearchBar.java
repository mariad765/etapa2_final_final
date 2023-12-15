package app.searchBar;


import app.Admin;
import app.audio.LibraryEntry;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static app.searchBar.FilterUtils.filterByAlbum;
import static app.searchBar.FilterUtils.filterByArtist;
import static app.searchBar.FilterUtils.filterByFollowers;
import static app.searchBar.FilterUtils.filterByGenre;
import static app.searchBar.FilterUtils.filterByLyrics;
import static app.searchBar.FilterUtils.filterByName;
import static app.searchBar.FilterUtils.filterByOwner;
import static app.searchBar.FilterUtils.filterByPlaylistVisibility;
import static app.searchBar.FilterUtils.filterByReleaseYear;
import static app.searchBar.FilterUtils.filterByTags;

public class SearchBar {
    private static final Integer MAX_RESULTS = 5;
    private final String user;
    private List<LibraryEntry> results;
    @Getter
    private String lastSearchType;

    @Getter
    private LibraryEntry lastSelected;

    /**
     * Constructor for SearchBar
     * @param user user
     */
    public SearchBar(final String user) {
        this.results = new ArrayList<>();
        this.user = user;
    }

    /**
     * Clear selection
     */
    public void clearSelection() {
        lastSelected = null;
        lastSearchType = null;
    }

    /**
     * Searches the library for entries based on specified
     * filters and entry type.
     * Supports searching for different types such as songs,
     * playlists, podcasts, artists, hosts, and albums.
     * The method applies filters to refine the search results
     * according to the specified criteria.
     *
     * @param filters The Filters object containing criteria to
     *                apply during the search process.
     *                These criteria include name, album, tags,
     *                lyrics, genre, release year, artist,
     *                owner, followers, etc., based on the type
     *                of entry being searched.
     * @param type    The type of entries to search for (e.g.,
     *                "song", "playlist", "podcast", "artist", "host", "album").
     * @return A list of LibraryEntry objects that match the
     * specified criteria and entry type.
     *         The results are filtered based on the provided
     *         criteria and limited to a maximum number of results.
     */
    public List<LibraryEntry> search(final Filters filters,
                                     final String type) {
        List<LibraryEntry> entries;

        switch (type) {
            case "song":
                entries = new ArrayList<>(Admin.getSongs());

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getAlbum() != null) {
                    entries = filterByAlbum(entries, filters.getAlbum());
                }

                if (filters.getTags() != null) {
                    entries = filterByTags(entries, filters.getTags());
                }

                if (filters.getLyrics() != null) {
                    entries = filterByLyrics(entries, filters.getLyrics());
                }

                if (filters.getGenre() != null) {
                    entries = filterByGenre(entries, filters.getGenre());
                }

                if (filters.getReleaseYear() != null) {
                    entries = filterByReleaseYear(entries,
                            filters.getReleaseYear());
                }

                if (filters.getArtist() != null) {
                    entries = filterByArtist(entries, filters.getArtist());
                }

                break;
            case "playlist":
                entries = new ArrayList<>(Admin.getPlaylists());

                entries = filterByPlaylistVisibility(entries, user);

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                if (filters.getFollowers() != null) {
                    entries = filterByFollowers(entries,
                            filters.getFollowers());
                }

                break;
            case "podcast":

                entries = new ArrayList<>(Admin.getPodcasts());

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }
                break;

            //added case artist
            case "artist":
                entries = new ArrayList<>(Admin.getArtistsManually());
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                break;

            case "host":
                entries = new ArrayList<>(Admin.getHosts());
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }
                break;

            case "album":
                entries = new ArrayList<>(Admin.getAlbums());
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }
                // filter by owner
                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                break;
            default:
                entries = new ArrayList<>();
        }

        while (entries.size() > MAX_RESULTS) {
            entries.remove(entries.size() - 1);
        }

        this.results = entries;
        this.lastSearchType = type;
        return this.results;
    }

    /**
     * Select
     * @param itemNumber item number
     * @return selected item
     */
    public LibraryEntry select(final Integer itemNumber) {
        if (this.results.size() < itemNumber) {
            results.clear();

            return null;
        } else {
            lastSelected = this.results.get(itemNumber - 1);
            results.clear();

            return lastSelected;
        }
    }
}
