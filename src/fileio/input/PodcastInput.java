package fileio.input;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class PodcastInput {
    /**
     * -- GETTER --
     * Getter for name
     *
     * @return the name of the podcast
     */
    private String name;
    /**
     * -- GETTER --
     * Getter for owner
     *
     * @return the owner of the podcast
     */
    private String owner;
    /**
     * -- GETTER --
     * Getter for episodes
     *
     * @return the episodes of the podcast
     */
    private ArrayList<EpisodeInput> episodes;

    /**
     * Constructor used for setting the fields of an PodcastInput object.
     */
    public PodcastInput() {
    }

    /**
     * Setter for name
     *
     * @param name the name of the podcast
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * Setter for owner
     *
     * @param owner the owner of the podcast
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }

    /**
     * Setter for episodes
     *
     * @param episodes the episodes of the podcast
     */
    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {

        this.episodes = episodes;
    }

    /**
     * Turn the object into a string
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "PodcastInput{" + "name='" + name + '\'' + ", "
                +
                "owner='"
                + owner
                + '\''
                + ", episodes="
                + episodes
                + '}';
    }
}
