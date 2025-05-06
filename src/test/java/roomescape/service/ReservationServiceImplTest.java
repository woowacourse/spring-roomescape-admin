package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.FakeReservationDAOImpl;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

class ReservationServiceImplTest {

    @Test
    @DisplayName("db의 모든 Reservation 목록을 조회한다")
    void find_all_reservation() {
        // given
        ReservationService reservationService = new ReservationServiceImpl(new FakeReservationDAOImpl());
        reservationService.createReservation(new ReservationRequest("kim", "2025-04-28", 1L));

        // when
        List<ReservationResponse> reservations = reservationService.findAllReservations();

        // then
        assertThat(reservations).hasSize(1);
    }

    @Test
    @DisplayName("db에 Reservation 을 추가한다")
    void create_reservation() {
        // given
        ReservationService reservationService = new ReservationServiceImpl(new FakeReservationDAOImpl());
        String name = "kim";
        ReservationRequest reservationRequest = new ReservationRequest(name, "2025-04-28", 1L);

        // when
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);

        // then
        assertThat(reservationResponse.name()).isEqualTo(name);
    }

    @Test
    @DisplayName("db에 Reservation 을 삭제한다")
    void delete_reservation_by_id() {
        // given
        ReservationService reservationService = new ReservationServiceImpl(new FakeReservationDAOImpl());
        ReservationResponse reservationResponse = reservationService.createReservation(
                new ReservationRequest("kim", "2025-04-28", 1L));
        Long id = reservationResponse.id();

        // when
        int count = reservationService.deleteReservationById(id);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("db에 Reservation 이 존재하는지 확인한다")
    void exists_by_id() {
        // given
        ReservationService reservationService = new ReservationServiceImpl(new FakeReservationDAOImpl());
        ReservationResponse reservationResponse = reservationService.createReservation(
                new ReservationRequest("kim", "2025-04-28", 1L));
        Long id = reservationResponse.id();

        // when
        boolean existsId = reservationService.existsById(id);
        boolean netExistsId = reservationService.existsById(100L);

        // then
        assertAll(
                () -> assertThat(existsId).isTrue(),
                () -> assertThat(netExistsId).isFalse()
        );
    }
}
