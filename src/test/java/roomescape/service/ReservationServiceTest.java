package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import roomescape.dao.reservation.InMemoryReservationDao;
import roomescape.dao.reservation.ReservationDao;
import roomescape.dao.resetvationTime.InMemoryReservationTimeDao;
import roomescape.dao.resetvationTime.ReservationTimeDao;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationResponse;

class ReservationServiceTest {

    private final ReservationTimeDao reservationTimeDao = new InMemoryReservationTimeDao(new ArrayList<>());
    private final ReservationDao reservationDao = new InMemoryReservationDao(new ArrayList<>(), reservationTimeDao);
    private final ReservationService reservationService = new ReservationServiceImpl(reservationDao,
            reservationTimeDao);

    @Test
    void createTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));
        reservationService.create(new ReservationCreateRequest("체체", LocalDate.of(2024, 10, 10), 1L));

        // when
        List<ReservationResponse> reservations = reservationService.findAll();

        // then
        assertThat(reservations.getFirst().name()).isEqualTo("체체");
    }

    @Test
    void findAllTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));
        reservationService.create(new ReservationCreateRequest("체체", LocalDate.of(2024, 10, 10), 1L));
        reservationService.create(new ReservationCreateRequest("체체2", LocalDate.of(2024, 10, 11), 1L));

        // when
        List<ReservationResponse> reservations = reservationService.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    void deleteTest() {

        // given
        reservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));
        reservationService.create(new ReservationCreateRequest("체체", LocalDate.of(2024, 10, 10), 1L));

        // when
        reservationService.delete(1L);
        List<ReservationResponse> reservations = reservationService.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(0);
    }
}
