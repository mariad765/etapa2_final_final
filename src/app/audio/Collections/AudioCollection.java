package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioCollection extends LibraryEntry {
    private final String owner;

    /**
     * Constructor for the AudioCollection class
     *
     * @param name  the name of the collection
     * @param owner the owner of the collection
     */

    public AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * Get number of tracks
     *
     * @return the number of tracks
     */

    public abstract int getNumberOfTracks();

    /**
     * Get track by index
     *
     * @param index inde of the track
     * @return the track
     */

    public abstract AudioFile getTrackByIndex(int index);

    /**
     * Checks if the owner matches the given criteria.
     * This method can be overridden in subclasses. When overriding this method,
     * make sure to call super.matchesOwner() to ensure the owner matching logic
     * in AudioCollection is still applied.
     *
     * @param user the owner to check
     * @return true if the owner matches, false otherwise
     */
    @Override
    public boolean matchesOwner(final String user) {
        return this.getOwner().equals(user);
    }
}
