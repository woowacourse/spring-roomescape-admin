package roomescape.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.FakeReservationRepository;
import roomescape.dao.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationServiceTest {
    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new FakeReservationRepository();
        reservationService = new ReservationService(reservationRepository);
    }

    @AfterEach
    void init() {
        for (final Reservation reservation : reservationRepository.findAll()) {
            reservationRepository.deleteById(reservation.getId());
        }
    }

    @DisplayName("새로운 예약을 저장한다.")
    @Test
    void create() {
        //given
        String name = "lini";
        String date = "2024-10-04";
        String time = "10:00";
        Reservation reservation = new Reservation(name, date, time);

        //when
        Reservation result = reservationService.create(reservation);

        //then
        assertAll(
                () -> assertThat(result.getId()).isNotZero(),
                () -> assertThat(result.getName()).isEqualTo(name),
                () -> assertThat(result.getDate()).isEqualTo(date),
                () -> assertThat(result.getTime()).isEqualTo(time)
        );
    }

    @DisplayName("모든 예약 내역을 조회한다.")
    @Test
    void findAll() {
        //given
        String name = "lini";
        String date = "2024-10-04";
        String time = "10:00";
        Reservation reservation = new Reservation(name, date, time);
        reservationService.create(reservation);

        //when
        List<Reservation> reservations = reservationService.findAll();

        //then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("id로 예약을 삭제한다.")
    @Test
    void deleteById() {
        //given
        String name = "lini";
        String date = "2024-10-04";
        String time = "10:00";
        Reservation reservation = new Reservation(name, date, time);
        Reservation result = reservationService.create(reservation);

        //when
        reservationService.deleteById(result.getId());

        //then
        assertThat(reservationService.findAll()).hasSize(0);
    }

    @DisplayName("존재하지 않는 id로 예약 삭제를 시도하면 예외를 던진다.")
    @Test
    void deleteFailByInvalidId() {
        //given
        long invalidId = 0;

        //when&then
        assertThatThrownBy(() -> reservationService.deleteById(invalidId))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("존재하지 않는 id입니다. id: " + invalidId);
    }
}
