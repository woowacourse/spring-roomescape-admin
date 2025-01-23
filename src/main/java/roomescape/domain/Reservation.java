package roomescape.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationSlot slot;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation(Member member, ReservationSlot slot, ReservationStatus status) {
        this.member = member;
        this.slot = slot;
        this.status = status;
    }

    public boolean isBefore(LocalDateTime dateTime) {
        return slot.isBefore(dateTime);
    }

    public boolean cancelBy(Member member) {
        if (hasCancelPermission(member)) {
            status = ReservationStatus.CANCEL;
            return true;
        }
        return false;
    }

    private boolean hasCancelPermission(Member member) {
        return member.isAdmin() || this.member.equals(member);
    }
}
