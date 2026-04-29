package roomescape.domain;

import java.time.Duration;

public class PlayingTime {

    private static final PlayingTime DEFAULT_PLAYING_TIME = PlayingTime.ofMinutes(60);

    private final Duration playingTime;

    public PlayingTime(Duration playingTime) {
        this.playingTime = playingTime;
    }

    public static PlayingTime toDefaultPlayingTime() {
        return DEFAULT_PLAYING_TIME;
    }

    public static PlayingTime ofMinutes(int minutes) {
        return new PlayingTime(Duration.ofMinutes(minutes));
    }

    public Duration getPlayingTime() {
        return playingTime;
    }
}
