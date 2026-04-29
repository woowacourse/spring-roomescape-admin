package roomescape.time.dto;

public class TimeResponseDto {

    private final Long id;
    private final String startAt;

    public TimeResponseDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
