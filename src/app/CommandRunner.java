package app;

import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.Podcast;
import app.audio.Files.Album;
import app.audio.Files.Episode;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Announcement;
import app.user.Artist;
import app.user.Event;
import app.user.Host;
import app.user.Merch;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;

import java.util.ArrayList;
import java.util.List;

// this will be singleton
public final class CommandRunner {
    private static ObjectMapper objectMapper = new ObjectMapper();
    // set instance
    private static CommandRunner instance = null;

    /**
     * Constructor private
     */
    private CommandRunner() {
    }

    /**
     * Get instance
     */
    public static CommandRunner getInstance() {
        if (instance == null) {
            instance = new CommandRunner();
        }
        return instance;
    }

    /**
     * Method to  run command search
     *
     * @param commandInput for search
     * @return object node
     */

    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        //check if user is offline
        if (!user.getConnectionStatus()) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            String message = user.getUsername() + " is offline.";
            objectNode.put("message", message);

            ArrayList<String> results = new ArrayList<>();
            objectNode.put("results", objectMapper.valueToTree(results));
            return objectNode;
        }
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        ArrayList<String> results = user.search(filters, type);
        String message = "Search returned " + results.size() + " results";

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * Method to run command select
     *
     * @param commandInput select
     * @return the object node
     */

    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run load command
     *
     * @param commandInput load
     * @return obj node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run playpause command
     *
     * @param commandInput playpause
     * @return obj node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run repeat command
     *
     * @param commandInput repeat
     * @return obj node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run shuffle command
     *
     * @param commandInput shuffle
     * @return obj node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run forward command
     *
     * @param commandInput forward
     * @return obj node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.forward();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run backward command
     *
     * @param commandInput backward
     * @return obj node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run like command
     *
     * @param commandInput like
     * @return obj node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.like();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run next command
     *
     * @param commandInput next
     * @return obj node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run prev command
     *
     * @param commandInput prev
     * @return obj node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to create playlist
     *
     * @param commandInput create playlist
     * @return obj node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message =
                user.createPlaylist(commandInput.getPlaylistName(), commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to add remove  playlist
     *
     * @param commandInput add remove playlist
     * @return obj node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to show user visibility
     *
     * @param commandInput show user visibility
     * @return obj node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to show
     *
     * @param commandInput show
     * @return obj node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Method to follow
     *
     * @param commandInput follow
     * @return obj node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to run status
     *
     * @param commandInput status
     * @return obj node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Method to show
     *
     * @param commandInput show
     * @return obj node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Method for statistics
     *
     * @param commandInput statistics command
     * @return obj node
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Method for statistics
     *
     * @param commandInput statistics command
     * @return obj node
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Method for statistics
     *
     * @param commandInput statistics command
     * @return obj node
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Method for connection sta t
     *
     * @param command connection status
     * @return obj node
     */
    public static ObjectNode switchConnectionStatus(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        String message = user.switchConnectionStatus();
        objectNode.put("message", message);
        return objectNode;

    }

    /**
     * Method to get online users
     *
     * @param command online users
     * @return obj node
     */
    public static ObjectNode getOnlineUsers(final CommandInput command) {
        List<String> onlineUsers = Admin.getOnlineUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));
        return objectNode;

    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    public static ObjectNode addUser(final CommandInput command) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        String message = Admin.addUser(command.getUsername(), command.getAge(), command.getCity(),
                command.getType());
        User user = Admin.getUser(command.getUsername());
        assert user != null;
        objectNode.put("message", message);
        return objectNode;

    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    public static ObjectNode addAlbum(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            return objectNode;
        }
        // check if user is an artist
        if (!user.getType().equals("artist")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            objectNode.put("message", command.getUsername() + " is not an artist.");
            return objectNode;
        }
        // if artist doesnt already ezxist create it
        for (Artist artist : Admin.getArtists()) {
            if (artist.getUsername().equals(user.getUsername())) {
                return getJsonNodesArtistPrime(command, artist);
            }
        }
        Artist artist = new Artist(user.getUsername(), user.getAge(), user.getCity());
        Admin.addArtist(artist);
        return getJsonNodesArtistPrime(command, artist);

    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    private static ObjectNode getJsonNodesArtistPrime(final CommandInput command,
                                                      final Artist artist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        Album newAlbum = artist.createAlbum(command.getName(), command.getSongs());
        String message = artist.addAlbum(newAlbum, command.getUsername());
        Admin.addSong(newAlbum);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Method to show
     *
     * @param command show
     * @return obj node
     */
    public static ObjectNode showAlbums(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            return objectNode;
        }
        //before adding an artist check if artist exists
        for (Artist artist : Admin.getArtists()) {
            if (artist.getUsername().equals(user.getUsername())) {
                return getJsonNodesArtist(command, artist);
            }
        }
        Artist artist = new Artist(user.getUsername(), user.getAge(), user.getCity());
        Admin.addArtist(artist);
        return getJsonNodesArtist(command, artist);
    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    private static ObjectNode getJsonNodesArtist(final CommandInput command,
                                                 final Artist artist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        List<Album> albums = artist.getAlbums();
        objectNode.put("result", objectMapper.valueToTree(albums));
        return objectNode;
    }

    /**
     * Method to print page
     *
     * @param command print page
     * @return obj node
     */
    public static ObjectNode printCurrentPage(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        //System.out.println(Admin.getTop5SongsForUser(user.getLikedSongs()));
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", command.getUsername());
        objectNode.put("command", command.getCommand());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", user.printCurrentPage());

        return objectNode;
    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    public static ObjectNode addEvent(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            // message for non-existent user
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");
            return objectNode;
        }
        // if artist doesnt already ezxist create it
        for (Artist artist : Admin.getArtists()) {
            if (artist.getUsername().equals(user.getUsername())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("command", command.getCommand());
                objectNode.put("user", command.getUsername());
                objectNode.put("timestamp", command.getTimestamp());
                Event event = artist.createEvent(command.getName(), command.getDate(),
                        command.getDescription());
                objectNode.put("message", artist.addEvent(event));

                return objectNode;
            }
        }
        Artist artist = new Artist(user.getUsername(), user.getAge(), user.getCity());
        Admin.addArtist(artist);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", command.getUsername() + " is not an artist.");

        return objectNode;

    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    public static ObjectNode addMerchant(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            // message for non-existent user
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");
            return objectNode;
        }
        // if artist doesnt already ezxist create it
        for (Artist artist : Admin.getArtists()) {
            if (artist.getUsername().equals(user.getUsername())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("command", command.getCommand());
                objectNode.put("user", command.getUsername());
                objectNode.put("timestamp", command.getTimestamp());
                Merch merch = artist.createMerch(command.getName(), command.getPrice(),
                        command.getDescription());
                objectNode.put("message", artist.addMerchant(merch));

                return objectNode;
            }
        }
        Artist artist = new Artist(user.getUsername(), user.getAge(), user.getCity());
        Admin.addArtist(artist);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", command.getUsername() + " is not an artist.");

        return objectNode;
    }

    /**
     * Method to get users
     *
     * @param command get users
     * @return obj node
     */
    public static ObjectNode getAllUsers(final CommandInput command) {
        List<User> users = Admin.getUsers();
        if (users == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(Admin.getAllUsers()));
        return objectNode;
    }

    /**
     * Method to remove
     *
     * @param command remove
     * @return obj node
     */
    public static ObjectNode deleteUser(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            // message for non-existent user
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", Admin.deleteUser(user));
        return objectNode;
    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    public static ObjectNode addPodcast(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            // message for non-existent user
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");
            return objectNode;
        }
        // if artist doesnt already ezxist create it
        for (Host host : Admin.getHosts()) {
            if (host.getUsername().equals(user.getUsername())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("command", command.getCommand());
                objectNode.put("user", command.getUsername());
                objectNode.put("timestamp", command.getTimestamp());
                List<Episode> episodes = new ArrayList<>();
                for (EpisodeInput episodeInput : command.getEpisodes()) {
                    episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                            episodeInput.getDescription()));
                }
                Podcast podcast = host.createPodcast(command.getName(), command.getDescription(),
                        episodes);
                objectNode.put("message", host.addPodcast(podcast));
                return objectNode;
            }
        }
        // check if user is host
        if (!user.getType().equals("host")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            objectNode.put("message", command.getUsername() + " is not a host.");
            return objectNode;
        }
        Host host = new Host(user.getUsername(), user.getAge(), user.getCity(), "host");
        Admin.addHost(host);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        List<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : command.getEpisodes()) {
            episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription()));
        }
        Podcast podcast = host.createPodcast(command.getName(), command.getDescription(),
                episodes);
        objectNode.put("message", host.addPodcast(podcast));

        return objectNode;
    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    public static ObjectNode addAnnouncement(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            // message for non-existent user
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");
            return objectNode;
        }
        // if artist doesnt already ezxist create it
        for (Host host : Admin.getHosts()) {
            if (host.getUsername().equals(user.getUsername())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("command", command.getCommand());
                objectNode.put("user", command.getUsername());
                objectNode.put("timestamp", command.getTimestamp());
                Announcement a =
                        host.createAnnouncement(command.getName(), command.getDescription());
                objectNode.put("message", host.addAnnouncement(a));
                return objectNode;
            }
        }
        // check if user is host
        if (!user.getType().equals("host")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            objectNode.put("message", command.getUsername() + " is not a host.");
            return objectNode;
        }

        return null;
    }

    /**
     * Method to remove
     *
     * @param command remove
     * @return obj node
     */
    public static ObjectNode removeAnnouncement(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            // message for non-existent user
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");
            return objectNode;
        }
        // if artist doesnt already ezxist create it
        for (Host host : Admin.getHosts()) {
            if (host.getUsername().equals(user.getUsername())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("command", command.getCommand());
                objectNode.put("user", command.getUsername());
                objectNode.put("timestamp", command.getTimestamp());
                objectNode.put("message", host.removeAnnouncement(command.getName()));
                return objectNode;
            }
        }
        // check if user is host
        if (!user.getType().equals("host")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            objectNode.put("message", command.getUsername() + " is not a host.");
            return objectNode;
        }

        return null;
    }

    /**
     * Method to show
     *
     * @param command show
     * @return obj node
     */
    public static ObjectNode showPodcasts(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            return objectNode;
        }
        //before adding an host check if artist exists
        for (Host host : Admin.getHosts()) {
            if (host.getUsername().equals(user.getUsername())) {
                return getJsonNodesHost(command, host);
            }
        }
        Host host = new Host(user.getUsername(), user.getAge(), user.getCity(), "host");
        Admin.addHost(host);
        return getJsonNodesHost(command, host);
    }

    /**
     * Method to add object
     *
     * @param command add
     * @return obj node
     */
    private static ObjectNode getJsonNodesHost(final CommandInput command,
                                               final Host host) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());

        objectNode.put("result", host.showPodcasts());
        return objectNode;
    }

    /**
     * Method to change page
     *
     * @param command change
     * @return obj node
     */
    public static ObjectNode changePage(final CommandInput command) {
        // get the user
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            return objectNode;
        }
        // print the output
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", user.changePage(command.getNextPage()));
        return objectNode;
    }

    /**
     * Method to remove
     *
     * @param command remove
     * @return obj node
     */
    public static ObjectNode removeAlbum(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            //write that user doesnt uxist
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            objectNode.put("message", "The username " + command.getUsername() + " doesn't exist.");


            return objectNode;
        }
        if (user.getType().equals("host")) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", command.getCommand());
            objectNode.put("user", command.getUsername());
            objectNode.put("timestamp", command.getTimestamp());
            objectNode.put("message", command.getUsername() + " is not an artist.");


            return objectNode;

        }
        //before adding an artist check if artist exists
        for (Artist artist : Admin.getArtists()) {
            if (artist.getUsername().equals(user.getUsername())) {
                return getJsonNodesArtistRemoveAlbum(command, artist);
            }
        }
        Artist artist = new Artist(user.getUsername(), user.getAge(), user.getCity());
        Admin.addArtist(artist);
        return getJsonNodesArtistRemoveAlbum(command, artist);
    }

    /**
     * Method to remove
     *
     * @param command remove
     * @return obj node
     */
    private static ObjectNode getJsonNodesArtistRemoveAlbum(final CommandInput command,
                                                            final Artist artist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", artist.removeAlbum(command.getName()));
        return objectNode;
    }

    /**
     * Method to statistics
     *
     * @param command statistics
     * @return obj node
     */
    public static ObjectNode getTop5Albums(final CommandInput command) {
        List<String> albums = Admin.getTop5Albums();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));
        return objectNode;
    }

    /**
     * Method to remove
     *
     * @param command remove
     * @return obj node
     */
    public static ObjectNode removePodcast(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            return objectMapper.createObjectNode();
        }
        //before adding an host check if hostt exists
        for (Host host : Admin.getHosts()) {
            if (host.getUsername().equals(user.getUsername())) {
                return getJsonNodesHostRemovePodcast(command, host);
            }
        }
        Host host = new Host(user.getUsername(), user.getAge(), user.getCity(), "host");
        Admin.addHost(host);
        return getJsonNodesHostRemovePodcast(command, host);
    }

    /**
     * Method to get json nodes for remove podcast
     *
     * @param command command
     * @param host    host
     * @return obj node
     */
    private static ObjectNode getJsonNodesHostRemovePodcast(final CommandInput command,
                                                            final Host host) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", host.removePodcast(command.getName()));
        return objectNode;
    }

    /**
     * Method to show
     *
     * @param command show
     * @return obj node
     */
    public static ObjectNode removeEvent(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            return objectMapper.createObjectNode();
        }
        //before adding an host check if hostt exists
        for (Artist artist : Admin.getArtists()) {
            if (artist.getUsername().equals(user.getUsername())) {
                return getJsonNodesArtistRemoveEvent(command, artist);
            }
        }
        Artist a = new Artist(user.getUsername(), user.getAge(), user.getCity());
        Admin.addArtist(a);
        return getJsonNodesArtistRemoveEvent(command, a);
    }

    /**
     * Method to get json nodes for remove event
     *
     * @param command command
     * @param artist  artist
     * @return obj node
     */
    private static ObjectNode getJsonNodesArtistRemoveEvent(final CommandInput command,
                                                            final Artist artist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", artist.removeEvent(command.getName()));
        return objectNode;
    }

    /**
     * Method to show
     *
     * @param command get top artists
     * @return obj node
     */
    public static ObjectNode getTop5Artists(final CommandInput command) {
        List<String> artists = Admin.getTop5Artists();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(artists));
        return objectNode;
    }
}


