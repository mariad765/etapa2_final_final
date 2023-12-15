package app.strategy;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LikedContentPage implements UserPage {
    private List<String> likedSongsInfo;
    private List<String> followedPlaylistsInfo;
    @Getter
    private String userN;

    /**
     * Constructor for LikedContentPage
     *
     * @param likedSongsInfo
     * @param followedPlaylistsInfo
     * @param userN
     */
    LikedContentPage(final List<String> likedSongsInfo,
                     final List<String> followedPlaylistsInfo,
                     final String userN) {
        this.likedSongsInfo = likedSongsInfo;
        this.followedPlaylistsInfo = followedPlaylistsInfo;
        this.userN = userN;
    }

    /**
     * Display page
     * @return page content
     */
    @Override
    public String displayPage() {
        StringBuilder page = new StringBuilder();
        page.append("LikedContentPage:\n");
        page.append("Liked Songs:\n\t").append(likedSongsInfo).append("\n");
        page.append("Followed Playlists:").append(followedPlaylistsInfo);

        // extract the \n from the last element
        page.deleteCharAt(page.length() - 2);

        return page.toString();

    }

    /**
     * Update page
     * @param userName user name
     */
    @Override
    public void updatePage(final String userName) {
        // update method
        /// update the followers
        // update the liked songs
        User u = Admin.getUser(userName);

        // Update the liked songs names
        List<String> songs = new ArrayList<String>();
        assert u != null;
        for (Song s : u.getLikedSongs()) {
            songs.add(s.getName());
        }


        this.likedSongsInfo = songs;
        List<String> playlists = new ArrayList<String>();

        for (Playlist p : u.getFollowedPlaylists()) {
            playlists.add(p.getName());
        }
        // Update the followed playlists
        this.followedPlaylistsInfo = playlists;


    }

    /**
     * Retrieve the username associated with the current user instance.
     *
     * @return The username of the user.
     * Returns the username string associated with this
     * user instance.
     */
    @Override
    public String userName() {
        return getUserN();
    }

}
