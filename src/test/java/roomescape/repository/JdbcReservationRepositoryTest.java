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
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.JdbcReservationRepository;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.JdbcReservationTimeRepository;

@JdbcTest
class JdbcReservationRepositoryTest {

    private JdbcReservationRepository jdbcReservationRepository;
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcReservationRepository = new JdbcReservationRepository(jdbcTemplate);
        jdbcReservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
        ReservationTime nonIdReservationTime = ReservationTime.createNew(LocalTime.parse("10:00"));
        ReservationTime reservationTime = jdbcReservationTimeRepository.save(nonIdReservationTime);
        Reservation reservation = Reservation.createNew("쿠다", LocalDate.parse("2023-08-06"), reservationTime);

        jdbcReservationRepository.save(reservation);
    }

    @Test
    @DisplayName("예약 저장")
    void reservation_save_test() {
        //given
        String name = "쿠다";
        LocalDate date = LocalDate.parse("2023-08-05");
        ReservationTime reservationTime = jdbcReservationTimeRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        Reservation reservation = Reservation.createNew(name, date, reservationTime);
        //when
        Reservation result = jdbcReservationRepository.save(reservation);
        Reservation saved = jdbcReservationRepository.findById(result.getId())
                .orElseThrow();

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getDate()).isEqualTo(date);
        assertThat(result.getTime().getId()).isEqualTo(reservationTime.getId());

        assertThat(saved.getId()).isEqualTo(result.getId());
        assertThat(saved.getDate()).isEqualTo(result.getDate());
        assertThat(saved.getTime().getId()).isEqualTo(result.getTime().getId());
    }

    @Test
    @DisplayName("예약 전체 조회")
    void reservation_findAll_test() {
        //given & when
        List<Reservation> reservations = jdbcReservationRepository.findAll();

        //then
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약 삭제")
    void reservation_delete_test() {
        // given
        int beforeSize = jdbcReservationRepository.findAll().size();

        Reservation reservation = jdbcReservationRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        // when
        jdbcReservationRepository.deleteById(reservation.getId());

        // then
        int afterSize = jdbcReservationRepository.findAll().size();

        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }

}
