package roomescape.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMemberId(Long memberId);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.slot.id = :slotId AND r.status = 'RESERVED'")
    boolean existsReservedReservation(Long slotId);

    boolean existsByMemberIdAndSlotId(long memberId, long slotId);
}
