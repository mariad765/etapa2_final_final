package app.user;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Album;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.strategy.ArtistPage;
import app.strategy.UserPage;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Artist extends User {

    private List<Album> albums;
    private List<Event> events;
    private List<Merch> merchs;
    private UserPage artistPage;

    /**
     * Constructor for Artist
     *
     * @param username username
     * @param age      age
     * @param city     city
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city, "artist");
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merchs = new ArrayList<>();
        artistPage = new ArtistPage(albums, events, username, merchs);

    }

    /**
     * Create a new album
     *
     * @param name  name of the album
     * @param songs songs in the album
     * @return the new album
     */
    public Album createAlbum(final String name,
                             final List<SongInput> songs) {
        return new Album(name, this.getUsername(), songs);
    }

    /**
     * Create a new event
     */
    public Event createEvent(final String name, final String date,
                             final String description) {
        return new Event(name, date, description);
    }

    /**
     * Create a new merch
     *
     * @param name        name of the merch
     * @param price       price of the merch
     * @param description description of the merch
     * @return the new merch
     */
    public Merch createMerch(final String name,
                             final int price,
                             final String description) {
        return new Merch(name, description, price);
    }

    /**
     * Add a new album
     *
     * @param album  album to be added
     * @param artist artist that adds the album
     * @return a message
     */
    public String addAlbum(final Album album, final String artist) {
        for (Album album1 : getAlbums()) {
            if (album1.getName().equals(album.getName())) {
                return getUsername() + " has another album with the same name.";
            }
        }
        Set<String> songNames = new HashSet<>();
        for (Song song : album.getSongsFull()) {
            if (!songNames.add(song.getName())) {
                return getUsername() + " has the same song at least twice in this album.";
            }
        }

        this.albums.add(album);
        return artist + " has added new album successfully.";
    }

    /**
     * Add a new event
     *
     * @param event event to be added
     * @return a message
     */
    public String addEvent(final Event event) {
        if (!event.getDate()
                .matches("^(0[1-9]|[12][0-9]|3[01])\\-(0[1-9]|1[012])\\-(19|20)\\d\\d$")) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }
        for (Event event1 : getEvents()) {
            if (event1.getName().equals(event.getName())) {
                return getUsername() + " has another event with the same name";
            }
        }
        events.add(event);
        return getUsername() + " has added new event successfully.";
    }

    /**
     * Add a new merch
     *
     * @param merch merch to be added
     * @return a message
     */
    public String addMerchant(final Merch merch) {

        if (merch.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }

        for (Merch merch1 : getMerchs()) {
            if (merch1.getName().equals(merch.getName())) {
                return getUsername() + " has merchandise with the same name.";
            }
        }

        merchs.add(merch);

        return getUsername() + " has added new merchandise successfully.";

    }

    /**
     * Removes an album and its associated songs from the
     * music library.
     * Additionally, removes songs from user playlists
     * and liked songs if present.
     *
     * @param name The name of the album to be removed.
     * @return A message indicating the success or failure
     * of the album removal operation.
     * - If the album is successfully deleted, a
     * success message is returned.
     * - If the album is not found or cannot be
     * deleted due to songs being in users' playlists
     * or liked songs,
     * an appropriate failure message is returned.
     */
    public String removeAlbum(final String name) {


        for (Album album : getAlbums()) {
            if (album.getName().equals(name)) {

                for (User user : Admin.getUsers()) {
                    PlayerStats stats = user.getPlayerStats();
                    // for songs in album check
                    for (Song song : album.getSongsFull()) {
                        if (stats.getName().contains(song.getName())) {
                            return getUsername() + " can't delete this album.";
                        }
                    }

                }
                for (Song song : album.getSongsFull()) {
                    for (Playlist playlist : Admin.getPlaylists()) {
                        playlist.getSongs().remove(song);
                    }
                }
                for (Song song : album.getSongsFull()) {
                    Admin.getSongs().remove(song);
                }
                for (Song song : album.getSongsFull()) {
                    for (User user : Admin.getUsers()) {
                        user.getLikedSongs().remove(song);
                    }
                }

                getAlbums().remove(album);
                return getUsername() + " deleted the album successfully.";
            }
        }
        return getUsername() + " doesn't have an album with the given name.";
    }

    /**
     * Remove a merch
     *
     * @param name name of the merch
     * @return a message
     */
    public String removeEvent(final String name) {
        for (Event event : getEvents()) {
            if (event.getName().equals(name)) {
                getEvents().remove(event);
                return getUsername() + " deleted the event successfully.";
            }
        }
        return getUsername() + " doesn't have an event with the given name.";
    }

    /**
     * Get total likes
     * @return total likes
     */
    public int getTotalNumberOfLikes() {
        int totalLikes = 0;
        for (Album a : getAlbums()) {
            for (Song s : a.getSongsFull()) {
                totalLikes += s.getLikes();
            }
        }
        return totalLikes;
    }
}
