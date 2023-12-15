package app.audio.Files;

import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioFile extends LibraryEntry {
    private final Integer duration;

    /**
     * Constructor for AudioFile
     * @param name name of the audio file
     * @param duration duration of the audio file
     */
    public AudioFile(final String name, final Integer duration) {
        super(name);
        this.duration = duration;
    }
}
