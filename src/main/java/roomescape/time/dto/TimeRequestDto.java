package roomescape.time.dto;

public class TimeRequestDto {

    private final String startAt;

    public TimeRequestDto(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
