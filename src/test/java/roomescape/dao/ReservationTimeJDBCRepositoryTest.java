package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
class ReservationTimeJDBCRepositoryTest {
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new ReservationTimeJDBCRepository(jdbcTemplate);
    }

    @DisplayName("새로운 예약을 저장한다.")
    @Test
    void saveReservation() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);

        //when
        ReservationTime result = reservationTimeRepository.save(reservationTime);

        //then
        assertThat(result.getId()).isNotZero();
    }

    @DisplayName("모든 예약 내역을 조회한다.")
    @Test
    void findAllReservationTest() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);
        reservationTimeRepository.save(reservationTime);
        int expectedSize = 1;

        //when
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        //then
        assertThat(reservationTimes.size()).isEqualTo(expectedSize);
    }

    @DisplayName("id로 예약을 삭제한다.")
    @Test
    void deleteReservationByIdTest() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);
        ReservationTime target = reservationTimeRepository.save(reservationTime);
        int expectedSize = 0;

        //when
        reservationTimeRepository.deleteById(target.getId());

        //then
        assertThat(reservationTimeRepository.findAll().size()).isEqualTo(expectedSize);
    }

    @DisplayName("해당 id를 가진 예약 내역이 존재한다.")
    @Test
    void existsByIdTest() {
        //given
        String startAt = "10:00";
        ReservationTime reservationTime = new ReservationTime(startAt);
        ReservationTime target = reservationTimeRepository.save(reservationTime);
        long id = target.getId();

        //when
        boolean result = reservationTimeRepository.existsById(id);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("해당 id를 가진 예약 내역이 존재하지 않는다.")
    @Test
    void notExistsByIdTest() {
        //given
        long invalidId = 0;

        //when
        boolean result = reservationTimeRepository.existsById(invalidId);

        //then
        assertThat(result).isFalse();
    }
}
