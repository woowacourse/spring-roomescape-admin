package roomescape.persist.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTimeFormatter;

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

    private ReservationEntity(String name, String date, ReservationTimeEntity timeEntity) {
        this.id = null;
        this.name = name;
        this.date = date;
        this.timeEntity = timeEntity;
    }

    public ReservationEntity copyWithId(Long id) {
        return new ReservationEntity(id, name, date, timeEntity);
    }

    public static ReservationEntity fromDomain(Reservation reservation) {
        String reservationDate = reservation.getDate().getStartDate().toString();
        ReservationTimeEntity timeEntity = ReservationTimeEntity.fromDomain(reservation.getTime());
        if (reservation.getId() != null) {
            return new ReservationEntity(reservation.getId(), reservation.getName(), reservationDate, timeEntity);
        }
        return new ReservationEntity(reservation.getName(), reservationDate, timeEntity);
    }

    public Reservation toDomain() {
        if (id == null) {
            throw new IllegalArgumentException("예약 엔티티의 ID가 null이어서 도메인 객체로 변환할 수 없습니다.");
        }
        if (timeEntity.getId() == null) {
            throw new IllegalArgumentException("예약 가능 시간 엔티티의 ID가 null이어서 도메인 객체로 변환할 수 없습니다.");
        }
        return new Reservation(
                id,
                name,
                new ReservationDate(ReservationDateTimeFormatter.parseDate(date)),
                timeEntity.toDomain()
        );
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
