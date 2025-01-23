package roomescape.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            SELECT new roomescape.repository.ReservationWithRank( 
                r,
                (SELECT COUNT(r2)
                FROM Reservation r2
                WHERE r2.slot.id = r.slot.id 
                AND r2.status != 'CANCEL'
                AND r2.createdDate < r.createdDate)
            )
            FROM Reservation r
            WHERE r.member.id = :memberId
            """)
    List<ReservationWithRank> findReservationWithRankByMemberId(Long memberId);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.slot.id = :slotId AND r.status = 'RESERVED'")
    boolean existsReservedReservation(Long slotId);

    boolean existsByMemberIdAndSlotId(long memberId, long slotId);
}
