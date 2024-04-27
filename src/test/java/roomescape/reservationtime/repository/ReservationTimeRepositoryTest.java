package roomescape.reservationtime.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Transactional
class ReservationTimeRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("등록된 시간 목록을 반환한다.")
    void given_reservationTimeRepository_when_findAll_Then_returnReservationTimeResponses() {
        //given
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
        //when, then
        assertThat(reservationTimeRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 1개 등록한다")
    void given_reservationRepository_when_save_then_saveOneReservationInDatabase() {
        //given
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
        //when
        reservationTimeRepository.save(LocalTime.parse("10:30"));
        //then
        assertThat(reservationTimeRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("지정한 예약 Id로 예약 정보를 삭제한다.")
    void given_reservationRepository_when_deleteById_then_deleteReservationInDatabase() {
        //given
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
        //when
        reservationTimeRepository.deleteById(1L);
        //then
        assertThat(reservationTimeRepository.findAll().size()).isEqualTo(0);
    }
}