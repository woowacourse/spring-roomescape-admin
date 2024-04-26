package roomescape.reservation.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ReservationRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("등록된 예약 목록을 반환한다.")
    void given_reservationRepository_when_findAll_then_returnReservationResponses() {
        //given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        //when, then
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 1개 등록한다")
    void given_reservationRepository_when_save_then_saveOneReservationInDatabase() {
        //given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        //when
        reservationRepository.save("poke", LocalDate.parse("2024-04-25"), 1L);
        //then
        assertThat(reservationRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("지정한 예약 Id로 예약 정보를 삭제한다.")
    void given_reservationRepository_when_deleteById_then_deleteReservationInDatabase() {
        //given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        //when
        reservationRepository.deleteById(1L);
        //then
        assertThat(reservationRepository.findAll().size()).isEqualTo(0);
    }
}