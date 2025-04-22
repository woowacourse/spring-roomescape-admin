package roomescape.entity;

import java.util.Objects;

public final class ReservationEntity {

    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTimeEntity timeEntity;

    public ReservationEntity(Long id, String name, String date, ReservationTimeEntity timeEntity) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeEntity = timeEntity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTimeEntity getTimeEntity() {
        return timeEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationEntity that = (ReservationEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
