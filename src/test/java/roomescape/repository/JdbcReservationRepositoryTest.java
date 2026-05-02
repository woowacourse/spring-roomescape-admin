package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class JdbcReservationRepositoryTest {

    private JdbcReservationRepository jdbcReservationRepository;

    @BeforeEach
    void setUp() {
        FakeReservationDao fakeReservationDao = new FakeReservationDao();
        jdbcReservationRepository = new JdbcReservationRepository(fakeReservationDao);
    }

    @Test
    @DisplayName("새로운 예약을 저장하고 부여된 식별자로 다시 조회할 수 있다.")
    void save_ValidReservation_CanBeFoundById() {
        Reservation reservation = new Reservation(null, "브라운", LocalDate.now().plusDays(1), 1L);
        long reservationId = jdbcReservationRepository.save(reservation);
        Reservation foundReservation = jdbcReservationRepository.findById(reservationId);
        assertThat(foundReservation.name()).isEqualTo("브라운");
    }

    @Test
    @DisplayName("존재하는 예약을 식별자를 통해 성공적으로 삭제할 수 있다.")
    void deleteById_ExistingId_RemovesReservation() {
        Reservation reservation = new Reservation(null, "브라운", LocalDate.now().plusDays(1), 1L);
        long reservationId = jdbcReservationRepository.save(reservation);
        jdbcReservationRepository.deleteById(reservationId);
        assertThat(jdbcReservationRepository.findAllJoinedDto()).isEmpty();
    }
}
