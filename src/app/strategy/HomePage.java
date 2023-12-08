package app.strategy;

import app.Admin;
import app.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class HomePage implements UserPage {
    private List<String> likedSongs;
    private List<String> followedPlaylists;

    public HomePage(List<String> likedSongs, List<String> followedPlaylists) {

        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;


    }

    @Override
    public String displayPage() {
      //  System.out.println(getLikedSongs());
        String page = "Liked songs:\n\t" + likedSongs + "\n\n" +
                "Followed playlists:\n\t" + followedPlaylists;
        return page;

    }

    @Override
    public void updatePage(String userName) {
        User user = Admin.getUser(userName);
        assert user != null;
        this.likedSongs = Admin.getTop5SongsForUser(user.getLikedSongs());
        this.followedPlaylists = Admin.getTop5PlaylistsForUser(user.getFollowedPlaylists());

    }
}