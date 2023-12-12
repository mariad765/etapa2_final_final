package app.user;

import app.Admin;
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

    public Artist(String username, int age, String city) {
        super(username, age, city, "artist");
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merchs = new ArrayList<>();
        artistPage = new ArtistPage(albums, events, username, merchs);

    }

    public Album createAlbum(String name, List<SongInput> songs) {
        return new Album(name, this.getUsername(), songs);
    }

    public Event createEvent(String name, String date, String description) {
        return new Event(name, date, description);
    }

    public Merch createMerch(String name, int price, String description) {
        return new Merch(name, description, price);
    }

    public String addAlbum(Album album, String artist) {
        // check if the albume already exists for this user
        for (Album album1 : getAlbums()) {
            if (album1.getName().equals(album.getName())) {
                return getUsername() + " has another album with the same name.";
            }
        }
        // check if the albume has the same songFULL twice
        Set<String> songNames = new HashSet<>();
        for (Song song : album.getSongsFull()) {
            if (!songNames.add(song.getName())) {
                return getUsername() + " has the same song at least twice in this album.";
            }
        }

        this.albums.add(album);
        return artist + " has added new album successfully.";
    }

    public String addEvent(Event event) {
        //check if date is valid
        if (!event.getDate()
                .matches("^(0[1-9]|[12][0-9]|3[01])\\-(0[1-9]|1[012])\\-(19|20)\\d\\d$")) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }
        //check if event is already in event list
        for (Event event1 : getEvents()) {
            if (event1.getName().equals(event.getName())) {
                return getUsername() + " has another event with the same name";
            }
        }

        events.add(event);

        return getUsername() + " has added new event successfully.";
    }

    public String addMerchant(Merch merch) {
//price can't be negative
        if (merch.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }
        //check if merch is already in merch list
        for (Merch merch1 : getMerchs()) {
            if (merch1.getName().equals(merch.getName())) {
                return getUsername() + " has merchandise with the same name.";
            }
        }

        merchs.add(merch);

        return getUsername() + " has added new merchandise successfully.";

    }

    public String removeAlbum(String name) {


        for (Album album : getAlbums()) {
            if (album.getName().equals(name)) {
                // the album can't be deleted if any user has it loaded in their player
                // check if any user has a song from that albume loaded
                for (User user : Admin.getUsers()) {
                    PlayerStats stats = user.getPlayerStats();
                    // for songs in album check
                    for (Song song : album.getSongsFull()) {
                        if (stats.getName().contains(song.getName())) {
                            return getUsername() + " can't delete this album.";
                        }
                    }

                }

                getAlbums().remove(album);
                return getUsername() + " has removed the album " + name + " successfully.";
            }
        }
        return getUsername() + " doesn't have an album with the name " + name + ".";
    }
}
