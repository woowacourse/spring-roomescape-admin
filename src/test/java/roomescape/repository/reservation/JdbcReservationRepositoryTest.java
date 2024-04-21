package roomescape.repository.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import roomescape.domain.Reservation;
import roomescape.repository.DatabaseCleanupListener;

@TestExecutionListeners(value = {
        DatabaseCleanupListener.class,
        DependencyInjectionTestExecutionListener.class
})
@JdbcTest
class JdbcReservationRepositoryTest {

    @Autowired
    private DataSource dataSource;
    private JdbcReservationRepository jdbcReservationRepository;

    private final Reservation reservation1 = new Reservation(null, "안돌", LocalDate.now(), LocalTime.now());
    private final Reservation reservation2 = new Reservation(null, "재즈", LocalDate.now(), LocalTime.now());

    @BeforeEach
    void setUp() {
        jdbcReservationRepository = new JdbcReservationRepository(dataSource);
    }

    @Test
    @DisplayName("저장된 모든 예약 정보를 가져온다")
    void find_all_reservations() {
        jdbcReservationRepository.insertReservation(reservation1);
        jdbcReservationRepository.insertReservation(reservation2);

        List<Reservation> allReservations = jdbcReservationRepository.findAllReservations();

        assertThat(allReservations.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void save_reservation() {
        Long savedId = jdbcReservationRepository.insertReservation(reservation1);

        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약을 id로 삭제한다.")
    void delete_reservation_by_id() {
        jdbcReservationRepository.insertReservation(reservation1);
        int beforeSize = jdbcReservationRepository.findAllReservations().size();

        jdbcReservationRepository.deleteReservationById(1L);
        int afterSize = jdbcReservationRepository.findAllReservations().size();

        assertAll(
                () -> Assertions.assertThat(beforeSize).isEqualTo(1),
                () -> Assertions.assertThat(afterSize).isEqualTo(0)
        );
    }
}
