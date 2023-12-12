package app.strategy;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.Collections;
import java.util.List;

public class LikedContent implements UserPage{
    private List<Song> likedSongs;
    private List<Playlist> followedPlaylists;
    private String userN;

    public LikedContent(List<Song> likedSongs, List<Playlist> followedPlaylists,String userN) {
        this.likedSongs = likedSongs;
        this.followedPlaylists = followedPlaylists;
        this.userN = userN;
    }
    @Override
    public String displayPage() {
        StringBuilder page = new StringBuilder("Liked songs:\n");

        if (likedSongs.isEmpty()) {
            page.append("\t").append(Collections.emptyList()).append("\n");
        } else {

            for (Song song : likedSongs) {
                page.append("\t[").append(song.getName()).append(" - ").append(song.getArtist()).append("]\n");
            }

        }

        page.append("\nFollowed playlists:\n");

        if (followedPlaylists.isEmpty()) {
            page.append("\t").append(Collections.emptyList()).append("\n");
        } else {
            for (Playlist playlist : followedPlaylists) {
                page.append("\t").append(Collections.singletonList(playlist.getName() + " - " + playlist.getOwner())).append("");
            }
        }

        return page.toString();
    }

    @Override
    public void updatePage(String userName) {
        User user = Admin.getUser(userName);
        assert user != null;
        this.likedSongs = user.getLikedSongs();
        this.followedPlaylists = user.getFollowedPlaylists();

    }

    @Override
    public String userName() {
        return null;
    }
}
