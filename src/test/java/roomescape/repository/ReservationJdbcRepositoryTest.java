package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import java.util.List;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationJdbcRepositoryTest {

    private final ReservationRepository reservationRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationJdbcRepositoryTest(final JdbcTemplate jdbcTemplate) {
        this.reservationRepository = new ReservationJdbcRepository(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void saveReservation() {
        final ReservationCreateRequest createRequest = new ReservationCreateRequest(
                "냥인", "2024-04-23", "10:09");

        final Long id = reservationRepository.save(createRequest);

        assertThat(id).isEqualTo(1);
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void findAllReservations() {
        createReservations();

        final List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(1);
    }

    @Test
    @DisplayName("Id에 해당하는 예약 정보를 삭제한다.")
    void deleteReservationById() {
        createReservations();

        reservationRepository.deleteById(1L);

        final Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);
        assertThat(count).isZero();
    }

    private void createReservations() {
        jdbcTemplate.update("insert into reservation(name, date, time) values(?, ?, ?)",
                "냥인", "2024-04-21", "15:40");
    }
}
