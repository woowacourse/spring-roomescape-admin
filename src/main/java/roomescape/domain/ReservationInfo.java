package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationInfo {
    private Id id;
    private Name name;
    private LocalDate date;
    private LocalTime time;

    public ReservationInfo() {
    }

    public ReservationInfo(Id id, Name name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationInfo(Name name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationInfo toEntity(ReservationInfo reservationInfo, Long id) {
        return new ReservationInfo(new Id(id), reservationInfo.name, reservationInfo.date, reservationInfo.time);
    }

    public Id getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
