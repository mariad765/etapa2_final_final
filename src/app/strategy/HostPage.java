package app.strategy;

import app.Admin;
import app.audio.Collections.Podcast;
import app.user.Announcement;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class HostPage implements UserPage {
    private List<Podcast> podcasts;
    @Getter
    private String hostName;
    private List<Announcement> announcements;

    public HostPage(String hostNmae, List<Podcast> podcasts, List<Announcement> announcements) {
        this.podcasts = podcasts;
        this.hostName = hostName;
        this.announcements = announcements;
    }

    @Override
    public String displayPage() {
        StringBuilder pageContent = new StringBuilder();

        pageContent.append("Podcasts:\n\t[");
        podcasts.forEach(podcast -> {
            pageContent.append(podcast.getName()).append(":\n\t[");
            podcast.getEpisodes().forEach(episode -> pageContent.append(episode.getName())
                    .append(" - ")
                    .append(episode.getDescription())
                    .append(", "));
            pageContent.setLength(pageContent.length() - 3); // Remove the last comma, space and newline
            pageContent.append(".]\n, ");
        });
        pageContent.setLength(pageContent.length() - 3); // Remove the last comma, space and newline

        pageContent.append("\n]\n\nAnnouncements:\n\t[");
        announcements.forEach(announcement -> pageContent.append(announcement.getName())
                .append(":\n\t")
                .append(announcement.getDescription())
                .append(",\n\t"));
        pageContent.setLength(pageContent.length() - 3); // Remove the last comma, space and newline
        pageContent.append("\n]");

        return pageContent.toString();
    }

    @Override
    public void updatePage(String userName) {
        User user = Admin.getUser(getHostName());
        assert user != null;
        if (user instanceof Host h) {
            this.announcements = h.getAnnouncements();
            this.podcasts = h.getPodcasts();
        }


    }
}
