package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public interface ReservationRepository {


    Long save(Reservation reservation);

    List<Reservation> findAll();

    void delete(Long id);
}
