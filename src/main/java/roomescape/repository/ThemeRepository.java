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
            SELECT r.theme 
            FROM Reservation r
            JOIN r.theme t
            WHERE r.date BETWEEN :startDate AND :endDate
            GROUP BY r.theme
            ORDER BY COUNT(r) DESC
            """)
    List<Theme> findPopularThemes(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
