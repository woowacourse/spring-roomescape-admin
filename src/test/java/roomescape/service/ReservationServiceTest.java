package roomescape.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.FakeReservationRepository;
import roomescape.dao.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationServiceTest {
    private ReservationRepository reservationRepository;
    private ReservationService reservationService;
    private ReservationTime reservationTime;

    @BeforeEach
    void setUp() {
        reservationRepository = new FakeReservationRepository();
        reservationService = new ReservationService(reservationRepository);
        reservationTime = new ReservationTime("10:00");
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
        Reservation reservation = new Reservation(name, date, reservationTime);

        //when
        Reservation result = reservationService.create(reservation);

        //then
        assertAll(
                () -> assertThat(result.getId()).isNotZero(),
                () -> assertThat(result.getName()).isEqualTo(name),
                () -> assertThat(result.getDate()).isEqualTo(date),
                () -> assertThat(result.getTime()).isEqualTo(reservationTime.getStartAt())
        );
    }

    @DisplayName("모든 예약 내역을 조회한다.")
    @Test
    void findAll() {
        //given
        String name = "lini";
        String date = "2024-10-04";
        Reservation reservation = new Reservation(name, date, reservationTime);
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
        Reservation reservation = new Reservation(name, date, reservationTime);
        Reservation result = reservationService.create(reservation);

        //when
        reservationService.deleteById(result.getId());

        //then
        assertThat(reservationService.findAll()).hasSize(0);
    }
}
