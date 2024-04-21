package roomescape.times;

import java.util.Objects;

public class Times {

    private final Long id;
    private final String startAt;

    public Times(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Times times = (Times) o;
        return Objects.equals(id, times.id) && Objects.equals(startAt, times.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startAt);
    }

    @Override
    public String toString() {
        return "Times{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
