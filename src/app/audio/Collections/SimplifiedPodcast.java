package app.audio.Collections;

import java.util.List;

// Simplified Podcast class
public class SimplifiedPodcast {
    private String name;
    private List<String> episodes;

    public SimplifiedPodcast(String name, List<String> episodes) {
        this.name = name;
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public List<String> getEpisodes() {
        return episodes;
    }
}
