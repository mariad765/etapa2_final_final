package app.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private String name;
    private String date;
    private String description;

    /**
     * Constructor for Event
     * @param name name of the event
     * @param date date of the event
     * @param description description of the event
     */
    public Event(final String name,
                 final String date, final String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
