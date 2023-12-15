package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Collections.SimplifiedPodcast;
import app.audio.Files.Episode;
import app.player.PlayerStats;
import app.strategy.HostPage;
import app.strategy.UserPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Host extends User {
    private List<Podcast> podcasts;
    private List<Announcement> announcements;
    private UserPage hostPage;

    /**
     * Constructor for Host
     *
     * @param username username
     * @param age      age
     * @param city     city
     * @param type     type
     */
    public Host(final String username, final int age,
                final String city, final String type) {
        super(username, age, city, type);
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
        this.hostPage = new HostPage(username, podcasts, announcements);

    }

    /**
     * Create a new podcast
     *
     * @param name        name of the podcast
     * @param description description of the podcast
     * @param episodes    episodes in the podcast
     * @return the new podcast
     */
    public Podcast createPodcast(
            final String name, final String description,
            final List<Episode> episodes) {
        return new Podcast(name, description, episodes);
    }

    /**
     * Add a podcast
     *
     * @param podcast podcast
     * @return a message
     */
    public String addPodcast(final Podcast podcast) {

        for (Podcast podcast1 : getPodcasts()) {
            if (podcast1.getName().equals(podcast.getName())) {
                return getUsername() + " has another podcast with the same name.";
            }
        }
        this.podcasts.add(podcast);

        Admin.addPodcast(podcast);
        return getUsername() + " has added new podcast successfully.";
    }

    /**
     * Add an announcement
     *
     * @param announcement announcement
     * @return a message
     */
    public String addAnnouncement(final Announcement announcement) {

        for (Announcement announcement1 : getAnnouncements()) {
            if (announcement1.getName().equals(announcement.getName())) {
                return getUsername() + " has already added an announcement with this name.";
            }
        }
        this.announcements.add(announcement);
        return getUsername() + " has successfully added new announcement.";

    }

    /**
     * Create an announcement
     *
     * @param name        name of the announcement
     * @param description description of the announcement
     * @return the new announcement
     */
    public Announcement createAnnouncement(
            final String name, final String description) {
        return new Announcement(name, description);
    }

    /**
     * Remove an announcement
     *
     * @param name name of the announcement
     * @return a message
     */

    public String removeAnnouncement(final String name) {
        for (Announcement announcement : getAnnouncements()) {
            if (announcement.getName().equals(name)) {
                getAnnouncements().remove(announcement);
                return getUsername() + " has successfully deleted the announcement.";
            }
        }
        return getUsername() + " has no announcement with the given name.";
    }

    /**
     * Show podcasts
     *
     * @return a JsonNode
     */
    public JsonNode showPodcasts() {

        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<Object> simplifiedPodcasts = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            List<String> episodeNames = new ArrayList<>();
            for (Episode episode : podcast.getEpisodes()) {
                episodeNames.add(episode.getName());
            }
            simplifiedPodcasts.add(new SimplifiedPodcast(podcast.getName(), episodeNames));
        }

        return objectMapper.valueToTree(simplifiedPodcasts);


    }

    /**
     * Removes a podcast and its episodes from the library,
     * ensuring no active user has these episodes in their stats.
     *
     * @param name The name of the podcast to be removed.
     * @return A message indicating the success or failure
     * of the podcast removal operation.
     * - If the podcast is successfully deleted, a success
     * message is returned.
     * - If the podcast cannot be deleted due to episodes being
     * in active user stats or not found,
     * an appropriate failure message is returned.
     */
    public String removePodcast(final String name) {

        Host user = Admin.getHost(getUsername());

        for (Podcast podcast : getPodcasts()) {
            if (podcast.getName().equals(name)) {
                assert user != null;
                PlayerStats stats = user.getPlayerStats();
                for (Episode episode : podcast.getEpisodes()) {
                    if (stats.getName().equals(episode.getName())) {
                        return getUsername() + " can't delete this podcast";
                    }
                }
            }
        }
        for (User user1 : Admin.getUsers()) {
            PlayerStats stats = user1.getPlayerStats();
            for (Podcast podcast : getPodcasts()) {
                for (Episode episode : podcast.getEpisodes()) {
                    if (stats.getName().equals(episode.getName())) {
                        return getUsername() + " can't delete this podcast.";
                    }
                }
            }
        }
        for (Podcast podcast : getPodcasts()) {
            if (podcast.getName().equals(name)) {
                Admin.getPodcasts().remove(podcast);
                getPodcasts().remove(podcast);
                return getUsername() + " deleted the podcast successfully.";
            }
        }
        return getUsername() + " doesn't have a podcast with the given name.";
    }
}
