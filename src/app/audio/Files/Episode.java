package app.audio.Files;

import lombok.Getter;

@Getter
public final class Episode extends AudioFile {
    private final String description;

    /**
     * Constructor for Episode
     * @param name name of the episode
     * @param duration  duration of the episode
     * @param description description of the episode
     */
    public Episode(final String name, final Integer duration, final String description) {
        super(name, duration);
        this.description = description;
    }
}
