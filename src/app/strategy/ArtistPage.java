package app.strategy;

import app.Admin;
import app.audio.Files.Album;
import app.user.Artist;
import app.user.Event;
import app.user.Merch;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class ArtistPage implements UserPage {
    private List<Merch> merchs;
    @Getter
    private String artistName;
    private List<Album> albums;
    private List<Event> events;

    /**
     * Constructor for ArtistPage
     *
     * @param albumb     albums
     * @param events     events
     * @param artistName artist name
     * @param merchs     merchs
     */
    public ArtistPage(final List<Album> albumb,
                      final List<Event> events, final String artistName,
                      final List<Merch> merchs) {
        this.albums = albumb;
        this.events = events;
        this.merchs = merchs;
        this.artistName = artistName;
    }

    /**
     * Display page
     *
     * @return page content
     */
    @Override
    public String displayPage() {
        StringBuilder pageContent = new StringBuilder();

        pageContent.append("Albums:\n\t");

        if (albums.isEmpty()) {
            pageContent.append("[]");
        } else {
            pageContent.append("[");
            albums.forEach(album -> pageContent.append(album.getName()).append(", "));
            pageContent.setLength(pageContent.length() - 2);
            pageContent.append("]");
        }
        pageContent.append("\n\nMerch:\n\t");
        if (merchs.isEmpty()) {
            pageContent.append("[]");
        } else {
            pageContent.append("[");
            merchs.forEach(merchandise -> pageContent.append(merchandise.getName())
                    .append(" - ")
                    .append(merchandise.getPrice())
                    .append(":\n\t")
                    .append(merchandise.getDescription())
                    .append(", "));
            pageContent.setLength(pageContent.length() - 2);
            pageContent.append("]");
        }
        if (events.isEmpty()) {
            pageContent.append("\n\nEvents:\n\t[]");
        }

        if (!events.isEmpty()) {
            pageContent.append("\n\nEvents:\n\t[");
            events.forEach(event -> pageContent.append(event.getName())
                    .append(" - ")
                    .append(event.getDate())
                    .append(":\n\t")
                    .append(event.getDescription())
                    .append(", "));
            pageContent.setLength(pageContent.length() - 2);
            pageContent.append("]");
            // pageContent.append("\n");}
        }
        return pageContent.toString();

    }

    /**
     * Update page
     *
     * @param userName username of page owner
     */
    @Override
    public void updatePage(final String userName) {
        User user = Admin.getUser(getArtistName());
        assert user != null;
        //convert user to artist
        if (user instanceof Artist) {
            Artist artist = (Artist) user;

            this.albums = artist.getAlbums();
            this.events = artist.getEvents();
        }

    }

    /**
     * Get username
     *
     * @return username
     */

    @Override
    public String userName() {
        return getArtistName();
    }

}
