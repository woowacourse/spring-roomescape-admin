package roomescape.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.JdbcReservationRepository;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.JdbcReservationTimeRepository;

@SpringBootTest
@Transactional
public class JdbcReservationRepositoryTest {

    @Autowired
    private JdbcReservationRepository jdbcReservationRepository;
    @Autowired
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @BeforeEach
    void setup() {
        ReservationTime nonIdReservationTime = ReservationTime.create(LocalTime.parse("10:00"));
        ReservationTime reservationTime = jdbcReservationTimeRepository.save(nonIdReservationTime);
        Reservation reservation = Reservation.create("쿠다", LocalDate.parse("2023-08-06"), reservationTime);

        jdbcReservationRepository.save(reservation);
    }

    @Test
    @DisplayName("예약 저장")
    void reservation_save_test() {
        //given
        String name = "쿠다";
        String date = "2023-08-06";
        ReservationTime reservationTime = jdbcReservationTimeRepository.finaAll()
                .stream()
                .findFirst()
                .orElseThrow();

        Reservation reservation = Reservation.create(name, LocalDate.parse(date), reservationTime);
        //when
        Reservation result = jdbcReservationRepository.save(reservation);

        //then
        assertThat(result.getId()).isNotNull();
    }

    @Test
    @DisplayName("예약 전체")
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
