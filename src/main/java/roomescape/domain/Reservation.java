package roomescape.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationTime time;

    @ManyToOne(fetch = FetchType.LAZY)
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
