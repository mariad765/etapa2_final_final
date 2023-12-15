package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Playlist extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private Integer followers;
    private int timestamp;

    /**
     * Constructor for Playlist
     * @param name name of playlist
     * @param owner owner of playlist
     */

    public Playlist(final String name, final String owner) {
        this(name, owner, 0);
    }

    /**
     * Constructor for Playlist
     * @param name name of playlist
     * @param owner owner of playlist
     * @param timestamp timestamp of playlist
     */
    public Playlist(final String name, final  String owner, final int timestamp) {
        super(name, owner);
        this.songs = new ArrayList<>();
        this.visibility = Enums.Visibility.PUBLIC;
        this.followers = 0;
        this.timestamp = timestamp;
    }

    /**
     * Checks if playlist contains song
     * @param song song to check
     * @return true if playlist contains song, false otherwise
     */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    /**
     * Adds song to playlist
     * @param song song to add
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Removes song from playlist
     * @param song song to remove
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }
    /**
     * Removes song by index
     * @param index index of song to remove
     */
    public void removeSong(final int index) {
        songs.remove(index);
    }

    /**
     * Switches visibility of playlist
     */
    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }

    /**
     * Increases followers of playlist
     */
    public void increaseFollowers() {
        followers++;
    }

    /**
     * Decreases followers of playlist
     */
    public void decreaseFollowers() {
        followers--;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                || (this.getVisibility() == Enums.Visibility.PRIVATE
                && this.getOwner().equals(user));
    }

    @Override
    public boolean matchesFollowers(final String followersQuery) {
        return filterByFollowersCount(this.getFollowers(), followersQuery);
    }
    /**
     * Filters by followers count
     * @param count count to filter by
     * @param query query to filter by
     * @return true if count matches query, false otherwise
     */
    private static boolean filterByFollowersCount(final int count, final String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }
}
