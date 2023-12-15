package app.player;

import lombok.Getter;

@Getter
public class PodcastBookmark {
    private final String name;
    private final int id;
    private final int timestamp;

    /**
     * Constructor for PodcastBookmark
     * @param name name of the podcast
     * @param id id of the podcast
     * @param timestamp timestamp of the podcast
     */
    public PodcastBookmark(final String name, final int id,
                           final int timestamp) {
        this.name = name;
        this.id = id;
        this.timestamp = timestamp;
    }

    /**
     * Get name of the podcast
     * @return name of the podcast
     * NOTE CHECKSTYLE IS WEIRD IT WANTS EACH PLUS ON A NEWLINE
     */
    @Override
    public String toString() {
        return "PodcastBookmark{"
                +
                "name='"
                + name
                + '\''
                +
                ", id="
                + id
                +
                ", timestamp="
                + timestamp
                +
                '}';
    }
}
