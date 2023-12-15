package app.audio.Files;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Song extends AudioFile {

    private final String album;

    private final ArrayList<String> tags;

    private final String lyrics;

    private final String genre;

    private final Integer releaseYear;

    private final String artist;

    private Integer likes;

    /**
     *  Constructor for Song
     * @param name name of the song
     * @param duration  duration of the song
     * @param album album of the song
     * @param tags  tags of the song
     * @param lyrics lyrics of the song
     * @param genre genre of the song
     * @param releaseYear release year of the song
     * @param artist artist of the song
     */
    public Song(final String name, final Integer duration,
                final String album, final ArrayList<String> tags,
                final String lyrics, final String genre,
                final Integer releaseYear, final String artist) {
        super(name, duration);
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
        this.likes = 0;
    }

    /**
     * Method to get the total duration of the song
     * @param year year to filter by
     * @param query query to filter by
     * @return true if the song matches the filter, false otherwise
     */
    private static boolean filterByYear(final int year, final String query) {
        if (query.startsWith("<")) {
            return year < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return year > Integer.parseInt(query.substring(1));
        } else {
            return year == Integer.parseInt(query);
        }
    }

    /**
     * Method to check if the song matches the name
     * @param albumQuery album query
     * @return true if the song matches the name, false otherwise
     */
    @Override
    public boolean matchesAlbum(final String albumQuery) {
        return this.getAlbum().equalsIgnoreCase(albumQuery);
    }

    /**
     * Method to check if the song matches the tags
     * @param tagsQuery tags query
     * @return true if the song matches the tags, false otherwise
     */
    @Override
    public boolean matchesTags(final ArrayList<String> tagsQuery) {
        List<String> songTags = new ArrayList<>();
        for (String tag : this.getTags()) {
            songTags.add(tag.toLowerCase());
        }

        for (String tag : tagsQuery) {
            if (!songTags.contains(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to check if the song matches the lyrics
     * @param lyricsQuery lyrics query
     * @return true if the song matches the lyrics, false otherwise
     */
    @Override
    public boolean matchesLyrics(final String lyricsQuery) {
        return this.getLyrics().toLowerCase().contains(lyricsQuery.toLowerCase());
    }

    /**
     * Method to check if the song matches the genre
     *
     * @param genreQuery genre query
     * @return true if the song matches the genre, false otherwise
     */
    @Override
    public boolean matchesGenre(final String genreQuery) {
        return this.getGenre().equalsIgnoreCase(genreQuery);
    }

    /**
     * Method to check if the song matches the artist
     *
     * @param artistQuery artist query
     * @return true if the song matches the artist, false otherwise
     */
    @Override
    public boolean matchesArtist(final String artistQuery) {
        return this.getArtist().equalsIgnoreCase(artistQuery);
    }

    /**
     * Method to check if the song matches the release year
     *
     * @param releaseYearQuery release year query
     * @return true if the song matches the release year, false otherwise
     */
    @Override
    public boolean matchesReleaseYear(final String releaseYearQuery) {
        return filterByYear(this.getReleaseYear(), releaseYearQuery);
    }

    /**
     * Method to like a song
     */
    public void like() {
        likes++;
    }

    /**
     * Method to dislike a song
     */
    public void dislike() {
        likes--;
    }
}
