package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import lombok.Getter;

import java.util.List;

@Getter
public final class Podcast extends AudioCollection {
    private final List<Episode> episodes;

    /**
     * Constructor for Podcast
     * @param name name of the podcast
     * @param owner owner of the podcast
     * @param episodes episodes of the podcast
     */
    public Podcast(final String name,
                   final String owner, final List<Episode> episodes) {
        super(name, owner);
        this.episodes = episodes;
    }

    @Override
    public int getNumberOfTracks() {
        return episodes.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return episodes.get(index);
    }
}
