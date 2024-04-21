package roomescape.dto;

import java.util.Objects;
import roomescape.domain.Time;

public class TimeDto {

    private final Long id;
    private final String startAt;

    private TimeDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeDto from(Time time) {
        return new TimeDto(
                time.getId(),
                time.getStartAt().toString()
        );
    }

    public static TimeDto of(Long id, String startAt) {
        return new TimeDto(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeDto other = (TimeDto) o;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.startAt, other.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }

    @Override
    public String toString() {
        return "TimeDto{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
