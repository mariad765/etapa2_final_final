package app.user;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.strategy.ArtistPage;
import app.strategy.HomePage;
import app.strategy.HostPage;
import app.strategy.LikedContent;
import app.strategy.LikedContentPage;
import app.strategy.UserPage;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class User extends LibraryEntry {
    @Getter
    private final Player player;
    private final SearchBar searchBar;
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private boolean lastSearched;
    @Getter
    @Setter
    private Boolean connectionStatus;
    @Getter
    @Setter
    private UserPage userPage; //current page
    @Getter
    private UserPage userHomePage; //home page
    @Getter
    private UserPage userLikedContentPage;
    @Getter
    @Setter
    private String type;

    /**
     * Constructor for User
     *
     * @param username username
     * @param age      age
     * @param city     city
     * @param type     type
     */
    public User(final String username, final int age,
                final String city, final String type) {
        super(username);
        this.username = username;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        this.connectionStatus = true;
        setType(type);
        this.userPage = new HomePage(Admin.getTop5SongsForUser(getLikedSongs()),
                Admin.getTop5PlaylistsForUser(getFollowedPlaylists()), username);
        this.userHomePage = new HomePage(Admin.getTop5SongsForUser(getLikedSongs()),
                Admin.getTop5PlaylistsForUser(getFollowedPlaylists()), username);
        this.userLikedContentPage =
                new LikedContent(getLikedSongs(), getFollowedPlaylists(), username);
    }

    /**
     * Set the user page
     *
     * @param userPage user page
     */
    void setUserPage(final UserPage userPage) {

        this.userPage = userPage;
    }

    /**
     * Display the user page
     *
     * @return a message
     */
    public String displayPage() {

        return userPage.displayPage();
    }

    /**
     * Update the user page
     *
     * @param filters filters
     * @param type1   type
     * @return a message
     */
    public ArrayList<String> search(
            final Filters filters, final String type1) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type1);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    /**
     * Select an item
     *
     * @param itemNumber item number
     * @return a message
     */
    public String select(final int itemNumber) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }

        if (selected.getClass().equals(Artist.class)) {
            Artist artist = (Artist) selected;
            setUserPage(artist.getArtistPage());
            return "Successfully selected " + (selected.getName() + "'s page.");
        }
        if (selected.getClass().equals(Host.class)) {
            Host host = (Host) selected;
            setUserPage(host.getHostPage()); //set the user page to the artist page
            return "Successfully selected " + (selected.getName() + "'s page.");
        }


        return "Successfully selected %s.".formatted(selected.getName());
    }

    /**
     * Load a source
     *
     * @return a message
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType()
                .equals("song") && ((AudioCollection) searchBar.
                getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play the source or pauze
     *
     * @return a message
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Set the volume
     *
     * @return a message
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();

        return "Repeat mode changed to %s.".formatted(getRepeatStatus(repeatMode));
    }

    /**
     * Set the volume
     *
     * @param repeatMode repeat mode
     * @return a message
     */
    private String getRepeatStatus(final Enums.RepeatMode repeatMode) {
        return switch (repeatMode) {
            case NO_REPEAT -> "no repeat";
            case REPEAT_ONCE -> "repeat once";
            case REPEAT_ALL -> "repeat all";
            case REPEAT_INFINITE -> "repeat infinite";
            case REPEAT_CURRENT_SONG -> "repeat current song";
            default -> "";
        };
    }

    /**
     * Set the volume
     *
     * @param seed seed
     * @return a message
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist")) {
            if (!player.getType().equals("album")) {
                return "The loaded source is not a playlist or an album.";
                //return "The loaded source is not a playlist.";
            }
        }


        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Set the volume
     *
     * @return a message
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Set the volume
     *
     * @return a message
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Set the volume
     *
     * @return a message
     */
    public String like() {
        if (!getConnectionStatus()) {
            return getUsername() + " is offline.";
        }
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType()
                .equals("playlist") && !player.getType().equals("host") && !player.getType()
                .equals("artist") && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Set the volume
     *
     * @return a message
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }
        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }
        return "Skipped to next track successfully. The current track is %s.".formatted(
                player.getCurrentAudioFile().getName());
    }

    /**
     * Set the volume
     *
     * @return a message
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s.".formatted(
                player.getCurrentAudioFile().getName());
    }

    /**
     * Make playlist
     *
     * @param name      name
     * @param timestamp timestamp
     * @return a message
     */
    public String createPlaylist(final String name,
                                 final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add or remove playlist
     *
     * @param iId id
     * @return a message
     */
    public String addRemoveInPlaylist(final int iId) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (iId > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(iId - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switches the visibility status of a
     * playlist identified by its ID.
     * This method toggles the visibility of
     * the specified playlist between public and private.
     *
     * @param playlistId The ID of the playlist
     *                   to switch its visibility.
     * @return A message indicating the success of
     * the visibility status update.
     * - If the playlist ID is too high (not within
     * the range of available playlists), an error message is returned.
     * - If the playlist's visibility is successfully
     * updated to public or private, a corresponding
     * success message is returned.
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlist
     *
     * @return a message
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow
     *
     * @return a message
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type1 = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type1.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Get stats
     *
     * @return a message
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Retrieves a list of names of the user's preferred songs.
     * This method gathers the names of audio files (songs) that the user has liked.
     *
     * @return An ArrayList containing names of the user's preferred songs.
     * The list consists of names extracted from the audio files stored
     * in the user's liked songs.
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Preferences
     *
     * @return a message
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * RSimulate time
     *
     * @param time time
     */
    public void simulateTime(final int time) {
        if (getConnectionStatus()) {
            player.simulatePlayer(time);
        } else {
            //stats must not be updated
            return;
        }
    }

    /**
     * Toggles the connection status of the user.
     * This method changes the online/offline status of the user account.
     *
     * @return A message indicating the success or failure of the
     * connection status change operation.
     * - If the user type is "user":
     * - If the current connection status is online, it
     * sets the status to offline, updates the player status accordingly,
     * and displays a message indicating the user is offline.
     * - If the current connection status is offline, it sets
     * the status to online, updates the player status accordingly,
     * and displays a message indicating the user is online.
     * - Returns a success message indicating the status change.
     * - If the user type is not "user", indicating a non-normal
     * user type, returns an error message.
     */
    public String switchConnectionStatus() {
        if (getType().equals("user")) {


            if (getConnectionStatus()) {
                setConnectionStatus(false);
                getPlayer().setUserIsOn(false);
                System.out.println(getUsername() + " is offline.");

            } else {

                setConnectionStatus(true);
                getPlayer().setUserIsOn(true);
                //show username
                System.out.println(getUsername() + " is online.");


            }

            return getUsername() + " has changed status successfully.";
        } else {
            return getUsername() + " is not a normal user.";
        }
    }

    /**
     * Print current page
     *
     * @return a message
     */
    public String printCurrentPage() {
        if (!getConnectionStatus()) {
            return getUsername() + " is offline.";
        }
        userPage.updatePage(getUsername());
        return displayPage();

    }

    /**
     * Changes the user's currently viewed page within
     * the application.
     * This method allows the user to navigate between different
     * pages within the application interface.
     *
     * @return A message indicating the success or failure of
     * the page change operation.
     * - If the user is offline, an offline message is returned.
     * - If the current page is the HomePage, the method
     * switches to the LikedContent page and returns a success message.
     * - If the current page is the LikedContent page, the
     * method switches to the Home page and returns a success message.
     * - If the current page is an ArtistPage or HostPage, the
     * method switches to the Home page and returns a success message.
     * - If the current page is not recognized (an unknown page),
     * an error message is returned.
     */
    public String changePage(final String nextPage) {
        if (!getConnectionStatus()) {
            return getUsername() + " is offline.";
        }
        if (nextPage.equals("Home")) {
            setUserPage(userHomePage);
            return getUsername() + " accessed Home successfully.";
        }

        if (userPage instanceof HomePage) {
            setUserPage(this.userLikedContentPage);
            return getUsername() + " accessed LikedContent successfully.";
        }
        if (userPage instanceof LikedContent) {
            setUserPage(userHomePage);
            return getUsername() + " accessed Home successfully.";
        }
        if (userPage instanceof LikedContentPage) {
            setUserPage(userHomePage);
            return getUsername() + " accessed Home successfully.";
        }
        if (userPage instanceof ArtistPage) {
            setUserPage(userHomePage);
            return getUsername() + " accessed Home successfully.";
        }
        if (userPage instanceof HostPage) {
            setUserPage(userHomePage);
            return getUsername() + " accessed Home successfully.";
        }


        return getUsername() + " is trying to access a non-existent page.";
    }
}
