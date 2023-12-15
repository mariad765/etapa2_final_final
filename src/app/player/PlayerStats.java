package app.player;

import app.utils.Enums;
import lombok.Getter;

@Getter
public class PlayerStats {
    private final String name;
    private final int remainedTime;
    private String repeat;
    private final boolean shuffle;
    private final boolean paused;

    /**
     * Constructor for PlayerStats
     * @param name name of the song
     * @param remainedTime remaining time of the song
     * @param repeatMode repeat mode of the song
     * @param shuffle shuffle mode of the song
     * @param paused paused mode of the song
     */

    public PlayerStats(final String name, final int remainedTime,
                       final Enums.RepeatMode repeatMode,
                       final boolean shuffle, final boolean paused) {
        this.name = name;
        this.remainedTime = remainedTime;
        this.paused = paused;
        this.repeat = getRepeatMode(repeatMode);
        this.shuffle = shuffle;
    }

    /**
     * Get repeat mode
     * @param repeatMode repeat mode of the song
     * @return repeat mode
     */
    private String getRepeatMode(final Enums.RepeatMode repeatMode) {
        switch (repeatMode) {
            case REPEAT_ALL:
                return "Repeat All";
            case REPEAT_ONCE:
                return "Repeat Once";
            case REPEAT_INFINITE:
                return "Repeat Infinite";
            case REPEAT_CURRENT_SONG:
                return "Repeat Current Song";
            case NO_REPEAT:
                return "No Repeat";
            default:
                return "No case";
        }
    }
}
