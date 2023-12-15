package app.strategy;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.Collections;
import java.util.List;

public class LikedContent implements UserPage {
    public static final int MAX_CH = 3;
    private List<Song> likedSongs;
    private List<Playlist> followedPlaylists;
    private String userN;

    /**
     * Constructor for LikedContent
     *
     * @param likedSongs        liked songs
     * @param followedPlaylists followed playlists
     * @param userN             user name
     */
    public LikedContent(final List<Song> likedSongs, final List<Playlist> followedPlaylists,
                        final String userN) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
        this.userN = userN;
    }

    /**
     * Display page
     */
    @Override
    public String displayPage() {
        StringBuilder page = new StringBuilder("Liked songs:\n");

        if (likedSongs.isEmpty()) {
            page.append("\t").append(Collections.emptyList()).append("\n");
        } else {
            if (likedSongs.size() == 1) {

                page.append("\t[");
                for (Song song : likedSongs) {
                    page.append("").append(song.getName()).append(" - ").append(song.getArtist())
                            .append("]\n");
                }
            }
            if (likedSongs.size() > 1) {
                page.append("\t[");
                for (Song song : likedSongs) {
                    page.append("").append(song.getName()).append(" - ").append(song.getArtist())
                            .append(", ");
                }
                page.append("]\n");
                page.deleteCharAt(page.length() - MAX_CH);
                page.deleteCharAt(page.length() - MAX_CH);
            }

        }

        page.append("\nFollowed playlists:\n");

        if (followedPlaylists.isEmpty()) {
            page.append("\t").append(Collections.emptyList()).append("");
        } else {
            for (Playlist playlist : followedPlaylists) {
                page.append("\t").append(Collections.singletonList(
                        playlist.getName() + " - " + playlist.getOwner())).append("");
            }
        }

        return page.toString();
    }

    /**
     * Update page
     *
     * @param userName user name
     */
    @Override
    public void updatePage(final String userName) {
        User user = Admin.getUser(userName);
        assert user != null;
        this.likedSongs = user.getLikedSongs();
        this.followedPlaylists = user.getFollowedPlaylists();

    }

    /**
     * Get username
     *
     * @return user name
     */
    @Override
    public String userName() {
        return null;
    }

}

