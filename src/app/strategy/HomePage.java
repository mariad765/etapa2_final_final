package app.strategy;

import app.Admin;
import app.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class HomePage implements UserPage {
    private List<String> likedSongs;
    private List<String> followedPlaylists;
    private String userN;

    /**
     * Constructor for HomePage
     *
     * @param likedSongs        liked songs
     * @param followedPlaylists followed playlists
     * @param userN             user name
     */
    public HomePage(final List<String> likedSongs,
                    final List<String> followedPlaylists,
                    final String userN) {

        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
        this.userN = userN;


    }

    /**
     * Display page
     *
     * @return page content
     */
    @Override
    public String displayPage() {
        //  System.out.println(getLikedSongs());
        String page = "Liked songs:\n\t"
                + likedSongs
                + "\n\n"
                +
                "Followed playlists:\n\t"
                + followedPlaylists;
        // extract the \n from the last element
        return page;

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
        this.likedSongs = Admin.getTop5SongsForUser(user.getLikedSongs());
        this.followedPlaylists = Admin.getTop5PlaylistsForUser(user.getFollowedPlaylists());

    }

    /**
     * Get username
     *
     * @return user name
     */
    @Override
    public String userName() {
        return getUserN();
    }

}
