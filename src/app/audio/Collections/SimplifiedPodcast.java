package app.audio.Collections;

import lombok.Getter;

import java.util.List;

// Simplified Podcast class
@Getter
public class SimplifiedPodcast {
    private String name;
    private List<String> episodes;

    /**
     * Constructor for SimplifiedPodcast
     * @param name name of the podcast
     * @param episodes episodes of the podcast
     */

    public SimplifiedPodcast(final String name, final List<String> episodes) {
        this.name = name;
        this.episodes = episodes;
    }

}
