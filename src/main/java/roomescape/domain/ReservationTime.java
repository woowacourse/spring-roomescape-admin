package roomescape.domain;

public class ReservationTime {

    private final Long id;
    private final String startAt;

    public ReservationTime(final Long id, final String startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validate(final String startAt) {
        if (startAt == null || startAt.isBlank()) {
            throw new IllegalArgumentException("시작 시간은 비어있을 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
