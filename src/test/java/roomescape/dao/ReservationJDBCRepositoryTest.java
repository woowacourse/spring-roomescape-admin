package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
class ReservationJDBCRepositoryTest {
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationJDBCRepository(jdbcTemplate);
    }

    @DisplayName("새로운 예약을 저장한다.")
    @Test
    void saveReservation() {
        //given
        String date = LocalDate.now().plusDays(1).toString();
        String time = LocalTime.now().toString();
        Reservation reservation = new Reservation("브라운", date, time);

        //when
        Reservation result = reservationRepository.save(reservation);

        //then
        assertThat(result.getId()).isNotZero();
    }

    @DisplayName("모든 예약 내역을 조회한다.")
    @Test
    void findAllReservationTest() {
        //given
        String date = LocalDate.now().plusDays(1).toString();
        String time = LocalTime.now().toString();
        Reservation reservation = new Reservation("브라운", date, time);
        reservationRepository.save(reservation);
        int expectedSize = 1;

        //when
        List<Reservation> reservations = reservationRepository.findAll();

        //then
        assertThat(reservations.size()).isEqualTo(expectedSize);
    }

    @DisplayName("id로 예약을 삭제한다.")
    @Test
    void deleteReservationByIdTest() {
        //given
        String date = LocalDate.now().plusDays(1).toString();
        String time = LocalTime.now().toString();
        Reservation reservation = new Reservation("브라운", date, time);
        Reservation target = reservationRepository.save(reservation);
        int expectedSize = 0;

        //when
        reservationRepository.deleteById(target.getId());

        //then
        assertThat(reservationRepository.findAll().size()).isEqualTo(expectedSize);
    }

    @DisplayName("해당 id를 가진 예약 내역이 존재한다.")
    @Test
    void existsByIdTest() {
        //given
        String date = LocalDate.now().plusDays(1).toString();
        String time = LocalTime.now().toString();
        Reservation reservation = new Reservation("브라운", date, time);
        Reservation target = reservationRepository.save(reservation);
        long id = target.getId();

        //when
        boolean result = reservationRepository.existsById(id);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("해당 id를 가진 예약 내역이 존재하지 않는다.")
    @Test
    void notExistsByIdTest() {
        //given
        long invalidId = 0;

        //when
        boolean result = reservationRepository.existsById(invalidId);

        //then
        assertThat(result).isFalse();
    }
}
