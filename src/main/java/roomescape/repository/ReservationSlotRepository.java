package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationSlot;

@Repository
public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {

    Optional<ReservationSlot> findByDateAndTimeIdAndThemeId(LocalDate date, long thimeId, long themeId);

    @Query("""
                SELECT rs.time.id
                FROM ReservationSlot rs
                WHERE rs.date = :date AND rs.theme.id = :themeId
            """)
    List<Long> findTimeIdByDateAndThemeId(LocalDate date, Long themeId);

    boolean existsByTimeId(Long timeId);

    boolean existsByThemeId(Long themeId);

    boolean existsByDateAndThemeIdAndTimeId(LocalDate date, Long themeId, Long timeId);
}
