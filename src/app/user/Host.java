package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Collections.SimplifiedPodcast;
import app.audio.Files.Episode;
import app.strategy.HostPage;
import app.strategy.UserPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Host extends User {
    @Getter
    private List<Podcast> podcasts;
    @Getter
    private List<Announcement> announcements;
    @Getter
    private UserPage hostPage;


    public Host(String username, int age, String city, String type) {
        super(username, age, city, type);
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
        this.hostPage = new HostPage(username, podcasts, announcements);

    }

    public Podcast createPodcast(String name, String description, List<Episode> episodes) {
        return new Podcast(name, description, episodes);
    }

    public String addPodcast(Podcast podcast) {
        // check if the host has a podcast with the same name
        for (Podcast podcast1 : getPodcasts()) {
            if (podcast1.getName().equals(podcast.getName())) {
                return getUsername() + " has another podcast with the same name.";
            }
        }
        this.podcasts.add(podcast);
        // also add in the admin array of podcasts
        Admin.addPodcast(podcast);
        return getUsername() + " has added new podcast successfully.";
    }

    public String addAnnouncement(Announcement announcement) {
        //<username> has already added an announcement with this name.
        for (Announcement announcement1 : getAnnouncements()) {
            if (announcement1.getName().equals(announcement.getName())) {
                return getUsername() + " has already added an announcement with this name.";
            }
        }
        this.announcements.add(announcement);
        return getUsername() + " has successfully added new announcement.";

    }

    public Announcement createAnnouncement(String name, String description) {
        return new Announcement(name, description);
    }


    public String removeAnnouncement(String name) {
        for (Announcement announcement : getAnnouncements()) {
            if (announcement.getName().equals(name)) {
                getAnnouncements().remove(announcement);
                return getUsername() + " has successfully deleted the announcement.";
            }
        }
        return getUsername() + " has no announcement with the given name.";
    }

    public JsonNode showPodcasts() {

        ObjectMapper objectMapper = new ObjectMapper();

            // Create a list of simplified Podcast objects
            ArrayList<Object> simplifiedPodcasts = new ArrayList<>();
            for (Podcast podcast : podcasts) {
                List<String> episodeNames = new ArrayList<>();
                for (Episode episode : podcast.getEpisodes()) {
                    episodeNames.add(episode.getName());
                }
                simplifiedPodcasts.add(new SimplifiedPodcast(podcast.getName(), episodeNames));
            }

            // Convert the simplifiedPodcasts list into a JSON string
            return objectMapper.valueToTree(simplifiedPodcasts);

       // return null;
    }
}
