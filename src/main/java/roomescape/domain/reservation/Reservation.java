package roomescape.domain.reservation;

import java.time.LocalDate;
import roomescape.domain.time.ReservationTime;

public class Reservation {
    private final Long id;
    private final String userName;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String userName, LocalDate date, ReservationTime time) {
        validate(userName, date, time);
        this.id = id;
        this.userName = userName;
        this.date = date;
        this.time = time;
    }

    private void validate(String userName, LocalDate date, ReservationTime time) {
        validateUserName(userName);
        validateDate(date);
        validateTime(time);
    }

    private void validateUserName(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 예약자 이름은 필수입니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 예약 날짜는 필수입니다.");
        }
    }

    private void validateTime(ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 예약 시간은 필수입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
