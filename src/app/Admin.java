package app;

import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Album;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Admin {
    @Getter
    private static List<User> users = new ArrayList<>();
    private static List<User> normalUsers = new ArrayList<>();
    @Getter
    private static List<Artist> artists = new ArrayList<>();
    @Getter
    private static List<Host> hosts = new ArrayList<>();

    private static List<Song> songs = new ArrayList<>();

    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        normalUsers = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity(),
                    "user"));
            //normalUsers.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
            //normal users should have the references to the users. wheat changes there shall be changed here too
            normalUsers.add(users.get(users.size() - 1));
        }
    }

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static List<Artist> getArtistsManually() {
        return new ArrayList<>(artists);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    public static List<String> getTop5SongsForUser(List<Song> songs) {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5PlaylistsForUser(List<Playlist> playlists) {
        List<Playlist> sortedPlaylists = new ArrayList<>(playlists);
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        artists = new ArrayList<>();
        timestamp = 0;
    }

    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : normalUsers) {
            if (user.getConnectionStatus()) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    public static String addUser(String username, int age, String city, String type) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        User u = new User(username, age, city, type);
        users.add(u);
        //check if user is artist
        if (type.equals("artist")) {
            //a reference to the user is added to the artists list so what changes in that array will be changes here too
            artists.add(new Artist(username, age, city));
        }
        //if user is normal
        if (type.equals("user")) {
            normalUsers.add(u);
        }
        return "The username " + username + " has been added successfully.";
    }

    public static void addArtist(Artist artist) {
        for (Artist artist1 : artists) {
            if (artist1.getUsername().equals(artist.getUsername())) {
                return;
            }
        }
        artists.add(artist);

    }

    //addsong method
    public static void addSong(Album album) {
        // check if song is already in songs list
        List<Song> newSongs = new ArrayList<>();
        for (Song song : album.getSongsFull()) {
            boolean songExists = false;
            for (Song s : songs) {
                if (song.getName().equals(s.getName())) {
                    songExists = true;
                    break;
                }
            }
            if (!songExists) {
                newSongs.add(song);
            }
        }
        songs.addAll(newSongs);
    }

    public static List<String> getAllUsers() {
        // first create a new list
        List<User> allUsers = new ArrayList<>();
        // add only users that are not hosts nor artists
        allUsers.addAll(normalUsers);
        // add artists
        allUsers.addAll(artists);
        // create another list of string that contains the names
        // of all users
        List<String> allUserNames = new ArrayList<>();
        for (User user : allUsers) {
            allUserNames.add(user.getUsername());
        }
        return allUserNames;
    }

    public static String deleteUser(User user) {
        // check if the user can be deleted.
        // a user can't be deleted if another user is listening to its podcasts or is on his page or anything like thats
        // check if another user is on this user s page
        for (User u : users) {
            if (u.getUserPage().equals(user.getUsername())) {

                return "User " + user.getUsername() + " can't be deleted";
            }
        }
        // check if anyone is listening to his podcasts
//        for (Podcast podcast : podcasts) {
//            if (podcast.getOwner().equals(user.getUsername())) {
//                for (User u : users) {
//                    PlayerStats stats=u.getPlayerStats();
//                    // make a list of names will all podcast episodes
//                    List<String> podcastEpisodes = new ArrayList<>();
//                    for (Episode episode : podcast.getEpisodes()) {
//                        podcastEpisodes.add(episode.getName());
//                    }
//                    // check if any user is listening to any of the episodes
//                    for (String episode : podcastEpisodes) {
//                        if (stats.getName().equals(episode)) {
//                            return "User " + user.getUsername() + " can't be deleted";
//                        }
//                    }
//
//                }
//            }
//        }
        // check if anyone is listeningto his songs
        if (user.getType().equals("artist")) {
            for (Song song : songs) {
                for (User u : users) {
                    PlayerStats stats = u.getPlayerStats();
                    if (stats.getName().equals(song.getName())) {
                        System.out.println("aici intrs");
                        return user.getUsername() + " can't be deleted.";
                    }
                }
            }
        }

        // delete user from users list
        users.remove(user);
        // delete user from the specific type list
        if (user.getType().equals("artist")) {
            // extract him from artists list where it is another object not a reference
            // itereta in list
            for (Artist artist : artists) {
                // if the artist is the one we are looking for
                if (artist.getUsername().equals(user.getUsername())) {

                    for (Album album : artist.getAlbums()) {
                        for (Song song : album.getSongsFull()) {
                            // alsoe remove the song from anyone's liked list
                            for (User u : users) {
                                u.getLikedSongs().remove(song);
                            }


                            songs.remove(song);
                        }
                    }
                    artists.remove(artist);
                    break;
                }
            }


        }
        if (user.getType().equals("user")) {
            // check if nobody listens to that playlist
            for (Playlist playlist : user.getPlaylists()) {
                for (User u : users) {
                    PlayerStats stats = u.getPlayerStats();
                    if (stats.getName().equals(playlist.getName())) {
                        return user.getUsername() + " can't be deleted.";
                    }
                }
            }
            // delete its playlists
            for (Playlist playlist : user.getPlaylists()) {
                for (User u : users) {
                    u.getFollowedPlaylists().remove(playlist);
                }
            }
            // also delete them from admin list
            for (Playlist playlist : user.getPlaylists()) {
                Admin.getPlaylists().remove(playlist);
            }


            normalUsers.remove(user);
        }
        return user.getUsername() + " was successfully deleted.";
    }

    public static List<Album> getAlbums() {
        // create list
        List<Album> albums = new ArrayList<>();
        // for each artist, add the albumes
        for (Artist artist : artists) {
            albums.addAll(artist.getAlbums());
        }
        //return the new list
        return albums;

    }

    public static void addHost(Host host) {
        for (Host h1 : hosts) {
            if (h1.getUsername().equals(host.getUsername())) {
                return;
            }
        }
        hosts.add(host);


    }

    public static void addPodcast(Podcast podcast) {
        podcasts.add(podcast);
    }

    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::getTotalNumberOfLikes).reversed());
        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= 5) break;
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;

    }
}
