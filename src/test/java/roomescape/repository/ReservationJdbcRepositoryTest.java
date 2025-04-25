package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationJdbcRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationJdbcRepository reservationJdbcRepository;

    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationJdbcRepository = new ReservationJdbcRepository(jdbcTemplate);
        reservationTimeRepository = new ReservationTimeJdbcRepository(jdbcTemplate);
        reservationTimeRepository.save(ReservationTime.of(LocalTime.now()));
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
    }

    //    @Disabled
    @DisplayName("예약 데이터를 성공적으로 저장한다.")
    @Test
    void saveTest() {
        //given
        final LocalDate date = LocalDate.MAX;
        final LocalTime time = LocalTime.MAX;
        final Reservation reservation = Reservation.of(1L, "윌슨", date.toString(),
                ReservationTime.of(1L, time.toString()));

        //when
        final long id = reservationJdbcRepository.save(reservation);

        //then
        assertThat(id).isEqualTo(1L);
    }

    @DisplayName("예약 데이터를 성공적으로 조회 한다.")
    @Test
    void findAllTest() {
        //given
        final LocalDate date = LocalDate.MAX;
        final LocalTime time = LocalTime.MAX;
        final Reservation reservation = Reservation.of(1L, "윌슨", date.toString(),
                ReservationTime.of(1L, time.toString()));
        reservationJdbcRepository.save(reservation);

        //when
        final List<Reservation> reservations = reservationJdbcRepository.findAll();

        //then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("존재하는 데이터를 삭제하여 1이상을 반환한다.")
    @Test
    void deleteByIdTest1() {
        //given
        final LocalDate date = LocalDate.MAX;
        final LocalTime time = LocalTime.MAX;
        final Reservation reservation = Reservation.of(1L, "윌슨", date.toString(),
                ReservationTime.of(1L, time.toString()));
        final long id = reservationJdbcRepository.save(reservation);

        //when
        final int expected = reservationJdbcRepository.deleteById(id);

        //then
        assertThat(expected).isPositive();
    }

    @DisplayName("존재하지 않는 데이터를 삭제하여 0을 반환한다.")
    @Test
    void deleteByIdTest2() {
        //given
        final long id = 1L;
        //when
        final int expected = reservationJdbcRepository.deleteById(id);

        //then
        assertThat(expected).isZero();
    }
}
