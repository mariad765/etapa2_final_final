package fileio.input;

import lombok.Getter;

@Getter
public final class EpisodeInput {
    /**
     * -- GETTER --
     *
     * @return the name of the episode
     */
    private String name;
    /**
     * -- GETTER --
     *
     * @return the duration of the episode
     */
    private Integer duration;
    /**
     * -- GETTER --
     * Description of the episode
     *
     * @return the description of the episode
     */
    private String description;

    /**
     * Constructor used for setting the fields of an EpisodeInput object.
     */
    public EpisodeInput() {
    }

    /**
     * @param name the name of the episode
     */

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param duration the duration of the episode
     */
    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    /**
     * Description of the episode
     *
     * @param description the description of the episode
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Turn the object into a string
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "EpisodeInput{"
                +
                "name='"
                + name
                + '\''
                +
                ", description='"
                + description
                + '\''
                +
                ", duration="
                + duration
                +
                '}';
    }
}
