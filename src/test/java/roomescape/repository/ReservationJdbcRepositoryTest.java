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
import roomescape.domain.ReservationTime;

import java.util.List;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationJdbcRepositoryTest {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationJdbcRepositoryTest(final JdbcTemplate jdbcTemplate) {
        this.reservationRepository = new ReservationJdbcRepository(jdbcTemplate);
        this.reservationTimeRepository = new ReservationTimeJdbcRepository(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void saveReservation() {
        createInitialReservationTime();
        final ReservationCreateRequest request = new ReservationCreateRequest("냥인", "2024-04-23", 1L);
        final ReservationTime reservationTime = reservationTimeRepository.findById(1L).get();
        final Reservation reservation = request.toReservation(reservationTime);

        final Long actual = reservationRepository.save(reservation);

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void findAllReservations() {
        createInitialReservationTime();
        createInitialReservations();

        final List<Reservation> actual = reservationRepository.findAll();

        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("id에 해당하는 예약 정보를 삭제한다.")
    void deleteReservationById() {
        createInitialReservationTime();
        createInitialReservations();

        reservationRepository.deleteById(1L);

        final Integer actual = jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);
        assertThat(actual).isZero();
    }

    private void createInitialReservationTime() {
        jdbcTemplate.update("insert into reservation_time(start_at) values(?)", "10:00");
    }

    private void createInitialReservations() {
        jdbcTemplate.update("insert into reservation(name, date, time_id) values(?, ?, ?)",
                "냥인", "2024-04-21", 1L);
    }
}
