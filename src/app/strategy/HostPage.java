package app.strategy;

import app.Admin;
import app.audio.Collections.Podcast;
import app.user.Announcement;
import app.user.Host;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class HostPage implements UserPage {
    private static final int LAST_CHARS_TO_REMOVE = 3;
    private List<Podcast> podcasts;
    @Getter
    private String hostName;
    private List<Announcement> announcements;

    /**
     * Constructor for HostPage
     *
     * @param hostName      host name
     * @param podcasts      podcasts
     * @param announcements announcements
     */
    public HostPage(final String hostName, final List<Podcast> podcasts,
                    final List<Announcement> announcements) {
        this.podcasts = podcasts;
        this.hostName = hostName;
        this.announcements = announcements;
    }

    /**
     * Display page
     */
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
            pageContent.setLength(
                    pageContent.length() - LAST_CHARS_TO_REMOVE);
            pageContent.append(".]\n, ");
        });
        pageContent.setLength(pageContent.length() - LAST_CHARS_TO_REMOVE);

        pageContent.append("\n]\n\nAnnouncements:\n\t[");
        announcements.forEach(announcement -> pageContent.append(announcement.getName())
                .append(":\n\t")
                .append(announcement.getDescription())
                .append(",\n\t"));
        pageContent.setLength(pageContent.length() - LAST_CHARS_TO_REMOVE);
        pageContent.append("\n]");

        return pageContent.toString();
    }

    /**
     * Method to update page
     *
     * @param userName user name
     */
    @Override
    public void updatePage(final String userName) {
        User user = Admin.getUser(getHostName());
        assert user != null;
        if (Host.class.isAssignableFrom(user.getClass())) {
            Host h = (Host) user;
            this.announcements = h.getAnnouncements();
            this.podcasts = h.getPodcasts();
        }

    }

    /**
     * Method to get user name
     *
     * @return user name
     */
    @Override
    public String userName() {

        return getHostName();
    }

}
