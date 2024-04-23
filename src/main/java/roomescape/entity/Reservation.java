package roomescape.entity;

import java.time.LocalDate;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(long id, String name, LocalDate date, ReservationTime time) {
        validateIdRange(id);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateIdRange(long id) {
        if (id <= 0) {
            throw new RuntimeException("[ERROR] 데이터베이스 오류입니다. 관리자에게 문의해주세요.");
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
