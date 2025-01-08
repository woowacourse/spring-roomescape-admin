package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Reservation {

    private Long id;
    private LocalDate date;
    private Member member;
    private ReservationTime time;
    private Theme theme;

    public Reservation(LocalDate date, Member member, ReservationTime time, Theme theme) {
        this.member = member;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.date, reservation.member, reservation.time, reservation.getTheme());
    }

    public boolean isBefore(LocalDateTime dateTime) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getStartAt());
        return reservationDateTime.isBefore(dateTime);
    }

    public boolean hasCancelPermission(Member member) {
        return member.isAdmin() || this.member.equals(member);
    }
}
