package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int SKIP_DURATION = 90;
    private Enums.RepeatMode repeatMode;
    private boolean shuffle;
    @Setter
    private boolean paused;
    private PlayerSource source;
    @Getter
    private String type;
    @Setter
    @Getter
    private Boolean userIsOn;

    private ArrayList<PodcastBookmark> bookmarks = new ArrayList<>();

    /**
     * Constructor for Player
     */

    public Player() {
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.paused = true;
        this.userIsOn = true;
    }

    /**
     * Constructor for Player
     *
     * @param type      type of the player
     * @param entry     entry of the player
     * @param bookmarks bookmarks of the player
     * @return a source
     */
    public static PlayerSource createSource(final String type, final LibraryEntry entry,
                                            final List<PodcastBookmark> bookmarks) {
        if ("song".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
        } else if ("playlist".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.PLAYLIST, (AudioCollection) entry);
        } else if ("podcast".equals(type)) {
            return createPodcastSource((AudioCollection) entry, bookmarks);
        } else if ("album".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.ALBUM, (AudioCollection) entry);
        }

        return null;
    }

    /**
     * Method to create a podcast source
     *
     * @param collection collection
     * @param bookmarks  bookmarks
     * @return a player source
     */
    private static PlayerSource createPodcastSource(final AudioCollection collection,
                                                    final List<PodcastBookmark> bookmarks) {
        for (PodcastBookmark bookmark : bookmarks) {
            if (bookmark.getName().equals(collection.getName())) {
                return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection, bookmark);
            }
        }
        return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection);
    }

    /**
     * Method to stop the player
     */
    public void stop() {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        repeatMode = Enums.RepeatMode.NO_REPEAT;
        paused = true;
        source = null;
        shuffle = false;
    }

    /**
     * Method to bookmark a podcast
     */
    private void bookmarkPodcast() {
        if (source != null && source.getAudioFile() != null) {
            PodcastBookmark currentBookmark =
                    new PodcastBookmark(source.getAudioCollection().getName(), source.getIndex(),
                            source.getDuration());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }

    /**
     * Method to set the source
     *
     * @param entry entry
     * @param typeQ type
     */
    public void setSource(final LibraryEntry entry, final String typeQ) {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        this.type = typeQ;
        this.source = createSource(typeQ, entry, bookmarks);
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }

    /**
     * Method to play the player
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * Method to shuffle the player
     *
     * @param seed seed
     */
    public void shuffle(final Integer seed) {
        if (seed != null) {
            source.generateShuffleOrder(seed);
        }

        if (source.getType() == Enums.PlayerSourceType.PLAYLIST) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
        if (source.getType() == Enums.PlayerSourceType.ALBUM) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
    }

    /**
     * Method to repeat the player
     *
     * @return repeat mode
     */
    public Enums.RepeatMode repeat() {
        if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                repeatMode = Enums.RepeatMode.REPEAT_ONCE;
            } else {
                repeatMode = Enums.RepeatMode.REPEAT_ALL;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
                repeatMode = Enums.RepeatMode.REPEAT_INFINITE;
            } else {
                if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                    repeatMode = Enums.RepeatMode.REPEAT_CURRENT_SONG;
                } else {
                    repeatMode = Enums.RepeatMode.NO_REPEAT;
                }
            }
        }

        return repeatMode;
    }

    /**
     * Method to simulate the player
     * NOTE CANNOT MAKE TIME FINAL BECAUSE IT IS MODIFIED IN THE METHOD
     * @param time time
     */
    public void simulatePlayer(int time) {

        if (!paused) {

            while (time >= source.getDuration()) {
                time -= source.getDuration();
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-time);
            }
        }
    }

    /**
     * Method to play the player
     */
    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }

    /**
     * Method to play the player
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    /**
     * Method to skip the player
     *
     * @param duration duration
     */
    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    /**
     * Method to skip the player
     */
    public void skipNext() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(-SKIP_DURATION);
        }
    }

    /**
     * Method to skip the player
     */
    public void skipPrev() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(SKIP_DURATION);
        }
    }

    /**
     * Method to get the current audio file
     *
     * @return current audio file
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    /**
     * Method to get the current audio collection
     *
     * @return current audio collection
     */
    public boolean getPaused() {
        return paused;
    }

    /**
     * Method to get the current audio collection
     *
     * @return current audio collection
     */
    public boolean getShuffle() {
        return shuffle;
    }

    /**
     * Method to get the current audio collection
     *
     * @return current audio collection
     */
    public PlayerStats getStats() {
        String filename = "";
        int duration = 0;
        if (source != null && source.getAudioFile() != null) {
            filename = source.getAudioFile().getName();
            duration = source.getDuration();
        } else {
            stop();
        }

        return new PlayerStats(filename, duration, repeatMode, shuffle, paused);
    }
}
