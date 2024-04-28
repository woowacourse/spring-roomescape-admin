package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:initReservation.sql")
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    private final Reservation reservation = createTestReservation();

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        // given & when
        Reservation savedReservation = reservationDao.save(reservation);

        // then
        assertAll(
                () -> assertThat(savedReservation.getId()).isEqualTo(2L),
                () -> assertThat(savedReservation.getName()).isEqualTo(reservation.getName()),
                () -> assertThat(savedReservation.getDate()).isEqualTo(reservation.getDate()),
                () -> assertThat(savedReservation.getReservationTime().getId()).isEqualTo(reservation.getReservationTime().getId()),
                () -> assertThat(savedReservation.getReservationTime().getStartAt()).isEqualTo(reservation.getReservationTime().getStartAt())
        );
    }

    @DisplayName("예약을 조회한다.")
    @Test
    void findAll() {
        // given & when
        List<Reservation> reservations = reservationDao.findAll();

        // then
        Reservation reservation = reservations.get(0);
        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(reservation.getId()).isEqualTo(1L),
                () -> assertThat(reservation.getName()).isEqualTo("브라운"),
                () -> assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2024, 4, 26)),
                () -> assertThat(reservation.getReservationTime().getId()).isEqualTo(1L),
                () -> assertThat(reservation.getReservationTime().getStartAt()).isEqualTo(LocalTime.of(10, 0))
        );
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        // given & when
        reservationDao.delete(1L);

        // then
        assertThat(reservationDao.findAll()).isEmpty();
    }

    @DisplayName("id값으로 예약을 조회한다.")
    @Test
    void findById() {
        // given & when
        Reservation findReservation = reservationDao.findById(1L);

        // then
        assertAll(
                () -> assertThat(findReservation.getId()).isEqualTo(1L),
                () -> assertThat(findReservation.getName()).isEqualTo("브라운"),
                () -> assertThat(findReservation.getDate()).isEqualTo(LocalDate.of(2024, 4, 26)),
                () -> assertThat(findReservation.getReservationTime().getId()).isEqualTo(1L),
                () -> assertThat(findReservation.getReservationTime().getStartAt()).isEqualTo(LocalTime.of(10, 0))
        );
    }

    private Reservation createTestReservation() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        return new Reservation("브라운", LocalDate.of(2023, 8, 5), reservationTime);
    }
}
