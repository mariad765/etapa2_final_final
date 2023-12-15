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

public final class Admin {
    public static final int FIVE = 5;
    // set instance
    private static Admin instance = null;
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

    /**
     * Private constructor for the Admin class.
     * This is part of the Singleton design pattern which ensures that
     * only one instance of the Admin class is created.
     */
    private Admin() {
    }

    /**
     * This method is used to get the single instance of the Admin class.
     * If the instance is null, a new Admin object is created and assigned
     * to the instance.
     *
     * @return the single instance of the Admin class.
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    /**
     * This method is used to get the lists of users.
     * It sets different type of users in different lists.
     *
     * @param userInputList the list of users.
     * @return void.
     */

    public static void setUsers(final List<UserInput> userInputList) {
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        users = new ArrayList<>();
        normalUsers = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity(),
                    "user"));
            //normalUsers.add(new User(userInput.getUsername(),
            //userInput.getAge(), userInput.getCity()));
            //normal users should have the references to the users.
            //wheat changes there shall be changed here too
            normalUsers.add(users.get(users.size() - 1));
        }
    }

    /**
     * This method is used to get the list of songs.
     *
     * @return a new array list of songs.
     */

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * This method is used to set the list of songs.
     *
     * @param songInputList input list of songs.
     * @return void.
     */

    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * This method is used to get the list of artists.
     *
     * @return artists
     */
    public static List<Artist> getArtistsManually() {
        return new ArrayList<>(artists);
    }

    /**
     * This method is used to get the list of podcasts.
     *
     * @return podcasts
     */

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * This method is used to set the list of podcasts.
     * It can also add podcasts to the list.
     *
     * @param podcastInputList from input files
     * @return void.
     */

    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
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

    /**
     * This method is used to get the list of playlists.
     *
     * @return playlists
     */

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * This method is used to get the list of users.
     *
     * @param username by username
     * @return user with name username
     */

    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * This method is used to update timestamp.
     *
     * @param newTimestamp by timestamp
     * @return void
     */

    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * This method is used to get top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= FIVE) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * This method is used to get top 5 playlists.
     *
     * @return top  playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers).reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= FIVE) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * It creates a copy of the input list, sorts it in descending
     * order of likes using a comparator, and then iterates through the
     * sorted list to retrieve the names of the top 5 songs.
     *
     * @param ssongss for user
     * @return name of songs
     */
    public static List<String> getTop5SongsForUser(final List<Song> ssongss) {
        List<Song> sortedSongs = new ArrayList<>(ssongss);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= FIVE) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Like the previous method, it gets a top for the user
     *
     * @param playlists playlists all of them
     * @return the names
     */
    public static List<String> getTop5PlaylistsForUser(final List<Playlist> playlists) {
        List<Playlist> sortedPlaylists = new ArrayList<>(playlists);
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers).reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= FIVE) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * This method is used to reset the lists.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        artists = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * This method essentially filters out and retrieves
     * the usernames of users who are currently marked as
     * online based on their connection status.
     *
     * @return names of online users
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : normalUsers) {
            if (user.getConnectionStatus()) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * This method is used to add a user.
     *
     * @param username username of user
     * @param age      age of user
     * @param city     of user
     * @param type     user/artist/host
     * @return the name of user
     */
    public static String addUser(final String username, final int age, final String city,
                                 final String type) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }
        User u = new User(username, age, city, type);
        users.add(u);
        // check if user is host
        if (type.equals("host")) {

            hosts.add(new Host(username, age, city, type));
        }
        //check if user is artist
        if (type.equals("artist")) {

            artists.add(new Artist(username, age, city));
        }
        //if user is normal
        if (type.equals("user")) {
            normalUsers.add(u);
        }
        return "The username " + username + " has been added successfully.";
    }

    /**
     * Adds an Artist to the collection if the Artist's username is unique.
     *
     * @param artist The Artist object to be added.
     *               It should not already exist in the collection.
     */
    public static void addArtist(final Artist artist) {
        for (Artist artist1 : artists) {
            if (artist1.getUsername().equals(artist.getUsername())) {
                return;
            }
        }
        artists.add(artist);

    }

    /**
     * Adds unique songs from an Album to the existing collection of songs.
     *
     * @param album The Album object containing songs to be added.
     *              Only songs not already existing in the songs collection will be added.
     */
    public static void addSong(final Album album) {
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

    /**
     * Retrieves a list of usernames of all users, including
     * normal users, artists, and hosts.
     *
     * @return A list containing usernames of all users
     * (normal users, artists, and hosts).
     */
    public static List<String> getAllUsers() {

        List<User> allUsers = new ArrayList<>();

        allUsers.addAll(normalUsers);
        // add artists
        allUsers.addAll(artists);

        allUsers.addAll(hosts);

        List<User> usersToRemove = new ArrayList<>();

        for (User user : allUsers) {
            if (Artist.class.isAssignableFrom(user.getClass())) {
                for (Host host : hosts) {
                    if (host.getUsername().equals(user.getUsername())) {
                        usersToRemove.add(user);
                        break;
                    }
                }


            }
        }


        allUsers.removeAll(usersToRemove);

        List<String> allUserNames = new ArrayList<>();
        for (User user : allUsers) {
            allUserNames.add(user.getUsername());
        }

        return allUserNames;
    }

    /**
     * Deletes a user from the system if the conditions are met.
     * <p>This method checks  scenarios specified on ocw before deleting a user:
     * <ul>
     *   <li>It verifies if the user can be deleted without affecting other
     *   users' interactions.</li>
     *   <li>A user cannot be deleted if another user is
     *   currently viewing their page.</li>
     *   <li>If the user is a host, it checks if anyone
     *   is listening to the host's podcast episodes or on their page.</li>
     *   <li>If the user is an artist, it ensures no users are currently
     *   on the artist's page or listening to their songs.</li>
     *   <li>For regular users, it checks if any playlists or songs they're
     *   associated with are currently in use by other users.</li>
     * </ul>
     * If any of these conditions are not met, the method prevents the deletion
     * and returns an appropriate message.
     *
     * @param user The User object to be deleted from the system.
     * @return A String indicating the result of the deletion process.
     * If the user was successfully deleted, it returns a success message.
     * If the user cannot be deleted due to ongoing interactions,
     * it returns a corresponding failure message.
     */

    public static String deleteUser(final User user) {
        // check if the user can be deleted.
        // a user can't be deleted if another user is listening
        // to its podcasts or is on his page or anything like thats
        // check if another user is on this user s page
        for (User u : users) {
            if (u.getUserPage().equals(user.getUsername())) {

                return "User " + user.getUsername() + " can't be deleted";
            }
        }

        // check if anyone is listening to the host s podcasts episode
        if (user.getType().equals("host")) {
            Host h = Admin.getHost(user.getUsername());
            for (Podcast podcast : h.getPodcasts()) {

                for (Episode episode : podcast.getEpisodes()) {
                    for (User u : users) {
                        PlayerStats stats = u.getPlayerStats();
                        if (stats.getName().equals(episode.getName())) {
                            return user.getUsername() + " can't be deleted.";
                        }
                    }
                }

            }
        }
        // check if any user is on host s page
        if (user.getType().equals("host")) {
            for (User u : users) {
                if (u.getUserPage().userName().equals(user.getUsername()) && !(u.getUsername()
                        .equals(user.getUsername()))) {

                    return user.getUsername() + " can't be deleted.";
                }
            }
        }
        // check if any user is on an artists page

        if (user.getType().equals("artist")) {
            for (User u : users) {
                // make sure page not null
                if (u.getUserPage() == null) {
                    continue;
                }
                // make sure name not null
                if (u.getUserPage().userName() == null) {
                    continue;
                }
                if (u.getUserPage().userName().equals(user.getUsername()) && !(u.getUserPage()
                        .equals(user.getUserPage()))) {
                    return user.getUsername() + " can't be deleted.";
                }
            }
        }
        // check if anyone is listeningto his songs
        if (user.getType().equals("artist")) {
            Artist art = Admin.getArtist(user.getUsername());
            assert art != null;

            for (Album a : art.getAlbums()) {
                for (Song song : a.getSongsFull()) {
                    for (User u : users) {
                        PlayerStats stats = u.getPlayerStats();
                        if (stats.getName().equals(song.getName())) {
                            return user.getUsername() + " can't be deleted.";
                        }
                    }
                }
            }
        }

        users.remove(user);
        if (user.getType().equals("host")) {
            for (Host host : hosts) {
                if (host.getUsername().equals(user.getUsername())) {
                    hosts.remove(host);
                    break;
                }
            }
        }

        if (user.getType().equals("artist")) {
            for (Artist artist : artists) {
                if (artist.getUsername().equals(user.getUsername())) {
                    for (Album album : artist.getAlbums()) {
                        for (Song song : album.getSongsFull()) {
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

            for (Playlist playlist : user.getPlaylists()) {
                for (User u : users) {
                    PlayerStats stats = u.getPlayerStats();

                    for (Song song : playlist.getSongs()) {
                        if (stats.getName().equals(song.getName())) {
                            users.add(user);
                            return user.getUsername() + " can't be deleted.";
                        }
                    }
                    if (stats.getName().equals(playlist.getName())) {
                        users.add(user);
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
            for (Playlist playlist : user.getPlaylists()) {
                Admin.getPlaylists().remove(playlist);
            }
            for (Playlist playlist : user.getFollowedPlaylists()) {
                playlist.decreaseFollowers();
            }
            normalUsers.remove(user);
        }
        return user.getUsername() + " was successfully deleted.";
    }

    /**
     * This method is used to get the list of artists.
     *
     * @return artists
     */

    private static Artist getArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * This method is used to get the list of albums.
     *
     * @return list of albums
     */

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

    /**
     * This method is used to add a host.
     *
     * @param host a user of type host
     */
    public static void addHost(final Host host) {
        for (Host h1 : hosts) {
            if (h1.getUsername().equals(host.getUsername())) {
                return;
            }
        }
        hosts.add(host);


    }

    /**
     * This adds a podcast to the list of podcasts.
     *
     * @param podcast a podcast
     */
    public static void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * This method is used to get the top 5 albums.
     *
     * @return list of top albums
     */
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::getTotalNumberOfLikes)
                .reversed().thenComparing(Album::getName));
        List<String> topAlbums = new ArrayList<>();
        int count = 0;

        for (Album album : sortedAlbums) {
            if (count >= FIVE) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }


        return topAlbums;

    }

    /**
     * This method is used to get the hosts.
     *
     * @param username of host
     * @return host object
     */
    public static Host getHost(final String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }

    /**
     * This method is used to get the top 5 artists.
     *
     * @return list of artists
     */
    public static List<String> getTop5Artists() {
        // create list
        List<Artist> sortedArtists = new ArrayList<>(artists);
        sortedArtists.sort(Comparator.comparingInt(Artist::getTotalNumberOfLikes)
                .reversed().thenComparing(Artist::getName));
        List<String> topArtists = new ArrayList<>();
        // add top 5
        int count = 0;
        for (Artist artist : sortedArtists) {
            if (count >= FIVE) {
                break;
            }
            topArtists.add(artist.getName());
            count++;
        }
        return topArtists;
    }
}
