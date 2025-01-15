package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
                SELECT r.time.id
                FROM Reservation r
                WHERE r.date = :date AND r.theme.id = :themeId
            """)
    List<Long> findTimeIdByDateAndThemeId(LocalDate date, Long themeId);

    List<Reservation> findByMemberId(Long memberId);

    boolean existsByTimeId(Long timeId);

    boolean existsByThemeId(Long themeId);

    boolean existsByDateAndThemeIdAndTimeId(LocalDate date, Long themeId, Long timeId);
}
