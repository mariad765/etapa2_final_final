package app.user;

import lombok.Getter;

@Getter

public class Announcement {
    private String name;
    private String description;

    /**
     * Constructor for Announcement
     *
     * @param name        name of the announcement
     * @param description description of the announcement
     */
    public Announcement(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
}
