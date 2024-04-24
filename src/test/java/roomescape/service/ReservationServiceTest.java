package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        reservationTimeRepository.save(new ReservationTime(LocalTime.parse("10:00")));
        final ReservationCreateRequest request = new ReservationCreateRequest(
                "냥인", "2024-04-21", 1L);

        final ReservationResponse actual = reservationService.createReservation(request);

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1L),
                () -> assertThat(actual.getName()).isEqualTo("냥인"),
                () -> assertThat(actual.getDate()).isEqualTo("2024-04-21"),
                () -> assertThat(actual.getTime().getId()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("id에 해당하는 예약 시간 정보가 없는 경우 예외가 발생한다.")
    void throwExceptionIfNotFindReservationTime() {
        final ReservationCreateRequest request = new ReservationCreateRequest(
                "냥인", "2024-04-21", 1L);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readAllReservations() {
        getIdAfterCreateReservation();

        final List<Reservation> actual = reservationService.getAllReservations();

        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void deleteReservation() {
        final Long id = getIdAfterCreateReservation();

        reservationService.deleteReservation(id);
        final List<Reservation> actual = reservationRepository.findAll();

        assertThat(actual).hasSize(0);
    }

    private Long getIdAfterCreateReservation() {
        reservationTimeRepository.save(new ReservationTime(LocalTime.parse("10:00")));
        final ReservationCreateRequest request = new ReservationCreateRequest(
                "냥인", "2024-04-21", 1L);
        final ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId()).get();
        final Reservation reservation = request.toReservation(reservationTime);
        return reservationRepository.save(reservation);
    }
}
