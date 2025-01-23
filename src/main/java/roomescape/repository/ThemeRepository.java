package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query("""
            SELECT rs.theme
            FROM ReservationSlot rs
            JOIN rs.theme t
            WHERE rs.date BETWEEN :startDate AND :endDate
            GROUP BY rs.theme
            ORDER BY COUNT(rs) DESC
            """)
    List<Theme> findPopularThemes(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
