package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final Member member;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, Member member, LocalDate date, LocalTime time) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return member.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
