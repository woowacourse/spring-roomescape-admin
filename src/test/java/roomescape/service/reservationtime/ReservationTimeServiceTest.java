package roomescape.service.reservationtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.repository.reservationtime.FakeReservationTimeDao;
import roomescape.service.reservationtime.request.ReservationTimeServiceRequest;
import roomescape.service.reservationtime.response.ReservationTimeResponse;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao());
    }

    @DisplayName("예약 시간을 생성할 수 있다.")
    @Test
    void save() {
        //given
        ReservationTimeServiceRequest request = creatReservationRequest();

        //when
        ReservationTimeResponse actual = reservationTimeService.save(request);

        //then
        assertThat(actual).isEqualTo(new ReservationTimeResponse(1L, LocalTime.of(10, 30)));
    }

    @DisplayName("예약시간 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        ReservationTimeServiceRequest request1 = creatReservationRequest();
        ReservationTimeServiceRequest request2 = creatReservationRequest();

        reservationTimeService.save(request1);
        reservationTimeService.save(request2);

        //when
        List<ReservationTimeResponse> actual = reservationTimeService.findAll();

        //then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("특정 예약시간을 취소할 수 있다.")
    @Test
    void deleteById() {
        //given
        ReservationTimeServiceRequest request = creatReservationRequest();
        ReservationTimeResponse response = reservationTimeService.save(request);

        //when
        reservationTimeService.deleteById(response.id());

        //then
        List<ReservationTimeResponse> actual = reservationTimeService.findAll();
        assertThat(actual).isEmpty();
    }

    private ReservationTimeServiceRequest creatReservationRequest() {
        return new ReservationTimeServiceRequest(LocalTime.of(10, 30));
    }
}
