package roomescape.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.FakeReservationRepository;
import roomescape.dao.FakeReservationTimeRepository;
import roomescape.dao.ReservationRepository;
import roomescape.dao.ReservationTimeRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationServiceTest {
    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;
    private ReservationService reservationService;
    private ReservationTime reservationTime;

    @BeforeEach
    void setUp() {
        reservationRepository = new FakeReservationRepository();
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);

        reservationTime = reservationTimeRepository.save(new ReservationTime("10:00"));
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
        ReservationRequest reservationRequest = new ReservationRequest(name, date, reservationTime.getId());

        //when
        ReservationResponse result = reservationService.create(reservationRequest);

        //then
        assertAll(
                () -> assertThat(result.id()).isNotZero(),
                () -> assertThat(result.name()).isEqualTo(name),
                () -> assertThat(result.date()).isEqualTo(date),
                () -> assertThat(result.time().id()).isNotZero(),
                () -> assertThat(result.time().startAt()).isEqualTo(reservationTime.getStartAt())
        );
    }

    @DisplayName("모든 예약 내역을 조회한다.")
    @Test
    void findAll() {
        //given
        String name = "lini";
        String date = "2024-10-04";
        ReservationRequest reservationRequest = new ReservationRequest(name, date, reservationTime.getId());
        reservationService.create(reservationRequest);

        //when
        List<ReservationResponse> reservations = reservationService.findAll();

        //then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("id로 예약을 삭제한다.")
    @Test
    void deleteById() {
        //given
        String name = "lini";
        String date = "2024-10-04";
        ReservationRequest reservationRequest = new ReservationRequest(name, date, reservationTime.getId());
        ReservationResponse target = reservationService.create(reservationRequest);

        //when
        reservationService.deleteById(target.id());

        //then
        assertThat(reservationService.findAll()).hasSize(0);
    }
}
