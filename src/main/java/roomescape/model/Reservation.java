package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime reservationTime;

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        this.id = Objects.requireNonNull(id);
        this.name = validateNonBlank(name);
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(String name, LocalDate date, ReservationTime reservationTime){
        this.id = null;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    private String validateNonBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 null이거나 공백일 수 없습니다");
        }
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return reservationTime.getStartAt();
    }

    public Long getTimeId(){
        return reservationTime.getId();
    }
}
