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

public class CommandRunner {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode search(CommandInput commandInput) {
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

    public static ObjectNode select(CommandInput commandInput) {
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

    public static ObjectNode load(CommandInput commandInput) {
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

    public static ObjectNode playPause(CommandInput commandInput) {
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

    public static ObjectNode repeat(CommandInput commandInput) {
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

    public static ObjectNode shuffle(CommandInput commandInput) {
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

    public static ObjectNode forward(CommandInput commandInput) {
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

    public static ObjectNode backward(CommandInput commandInput) {
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

    public static ObjectNode like(CommandInput commandInput) {
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

    public static ObjectNode next(CommandInput commandInput) {
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

    public static ObjectNode prev(CommandInput commandInput) {
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

    public static ObjectNode createPlaylist(CommandInput commandInput) {
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

    public static ObjectNode addRemoveInPlaylist(CommandInput commandInput) {
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

    public static ObjectNode switchVisibility(CommandInput commandInput) {
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

    public static ObjectNode showPlaylists(CommandInput commandInput) {
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

    public static ObjectNode follow(CommandInput commandInput) {
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

    public static ObjectNode status(CommandInput commandInput) {
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

    public static ObjectNode showLikedSongs(CommandInput commandInput) {
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

    public static ObjectNode getPreferredGenre(CommandInput commandInput) {
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

    public static ObjectNode getTop5Songs(CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getTop5Playlists(CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    public static ObjectNode switchConnectionStatus(CommandInput command) {
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

    public static ObjectNode getOnlineUsers(CommandInput command) {
        List<String> onlineUsers = Admin.getOnlineUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));
        return objectNode;

    }

    public static ObjectNode addUser(CommandInput command) {
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

    public static ObjectNode addAlbum(CommandInput command) {
        User user = Admin.getUser(command.getUsername());

        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
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

    private static ObjectNode getJsonNodesArtistPrime(CommandInput command, Artist artist) {
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

    public static ObjectNode showAlbums(CommandInput command) {
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

    private static ObjectNode getJsonNodesArtist(CommandInput command, Artist artist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        List<Album> albums = artist.getAlbums();
        objectNode.put("result", objectMapper.valueToTree(albums));
        return objectNode;
    }

    public static ObjectNode printCurrentPage(CommandInput command) {
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

    public static ObjectNode addEvent(CommandInput command) {
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

    public static ObjectNode addMerchant(CommandInput command) {
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

    public static ObjectNode getAllUsers(CommandInput command) {
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

    public static ObjectNode deleteUser(CommandInput command) {
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

    public static ObjectNode addPodcast(CommandInput command) {
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

    public static ObjectNode addAnnouncement(CommandInput command) {
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

    public static ObjectNode removeAnnouncement(CommandInput command) {
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

    public static ObjectNode showPodcasts(CommandInput command) {
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

    private static ObjectNode getJsonNodesHost(CommandInput command, Host host) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());

        objectNode.put("result", host.showPodcasts());
        return objectNode;
    }

    public static ObjectNode changePage(CommandInput command) {
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
        objectNode.put("message", user.changePage());
        return objectNode;
    }

    public static ObjectNode removeAlbum(CommandInput command) {
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

    private static ObjectNode getJsonNodesArtistRemoveAlbum(CommandInput command, Artist artist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", artist.removeAlbum(command.getName()));
        return objectNode;
    }

    public static ObjectNode getTop5Albums(CommandInput command) {
        List<String> albums = Admin.getTop5Albums();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));
        return objectNode;
    }

    public static ObjectNode removePodcast(CommandInput command) {
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

    private static ObjectNode getJsonNodesHostRemovePodcast(CommandInput command, Host host) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", host.removePodcast(command.getName()));
        return objectNode;
    }

    public static ObjectNode removeEvent(CommandInput command) {
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

    private static ObjectNode getJsonNodesArtistRemoveEvent(CommandInput command, Artist artist) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", artist.removeEvent(command.getName()));
        return objectNode;
    }
}


