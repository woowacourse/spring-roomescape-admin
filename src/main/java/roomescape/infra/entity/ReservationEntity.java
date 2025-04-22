package roomescape.infra.entity;

import roomescape.business.domain.Customer;
import roomescape.business.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

import java.time.LocalDate;
import java.util.Map;

public class ReservationEntity {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeEntity time;

    public ReservationEntity(final Long id, final String name, final LocalDate date, final ReservationTimeEntity time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationEntity beforeSave(ReservationCreateRequest request) {
        return new ReservationEntity(null, request.name(), request.date(), new ReservationTimeEntity(request.timeId(), null));
    }

    public Map<String, ?> dataMap() {
        return Map.of(
                "name", name,
                "date", date,
                "time_id", time.getId()
        );
    }

    public Reservation toDomain() {
        return new Reservation(new Customer(name), date, time.toDomain());
    }

    public Long getId() {
        return id;
    }

    public ReservationTimeEntity getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getTimeId() {
        return time.getId();
    }
}
