package roomescape.service.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.ReservationTime;
import roomescape.repository.reservation.FakeReservationDao;
import roomescape.repository.reservationtime.FakeReservationTimeDao;
import roomescape.service.reservation.request.ReservationServiceRequest;
import roomescape.service.reservation.response.ReservationResponse;

class ReservationServiceTest {

    private FakeReservationTimeDao fakeReservationTimeDao;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        fakeReservationTimeDao = new FakeReservationTimeDao();
        reservationService = new ReservationService(new FakeReservationDao(), fakeReservationTimeDao);
    }

    @DisplayName("예약을 생성할 수 있다.")
    @Test
    void save() {
        //given
        ReservationTime time = createReservationTime();
        long id = fakeReservationTimeDao.save(time);

        ReservationServiceRequest request = createReservationRequest("도기", LocalDate.of(2025, 4, 30), id);

        //when
        ReservationResponse actual = reservationService.save(request);

        //then
        assertThat(actual).isEqualTo(new ReservationResponse(1L, "도기", LocalDate.of(2025, 4, 30), time));
    }

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        ReservationTime time = createReservationTime();
        long id = fakeReservationTimeDao.save(time);
        ReservationServiceRequest request1 = createReservationRequest("도기", LocalDate.of(2025, 4, 30), id);
        ReservationServiceRequest request2 = createReservationRequest("포비", LocalDate.of(2025, 4, 29), id);

        reservationService.save(request1);
        reservationService.save(request2);

        //when
        List<ReservationResponse> actual = reservationService.findAll();

        //then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("특정 예약을 취소할 수 있다.")
    @Test
    void deleteById() {
        //given
        ReservationTime time = createReservationTime();
        long id = fakeReservationTimeDao.save(time);

        ReservationServiceRequest request = createReservationRequest("도기", LocalDate.of(2025, 4, 30), id);
        ReservationResponse response = reservationService.save(request);

        //when
        reservationService.deleteById(response.id());

        //then
        List<ReservationResponse> actual = reservationService.findAll();
        assertThat(actual).isEmpty();
    }


    private ReservationTime createReservationTime() {
        return ReservationTime.ofWithoutId(LocalTime.of(10, 30));
    }

    private ReservationServiceRequest createReservationRequest(String name, LocalDate date, Long id) {
        return new ReservationServiceRequest(name, date, id);
    }
}
