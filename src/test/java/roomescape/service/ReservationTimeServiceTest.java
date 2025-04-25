package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

class ReservationTimeServiceTest {

    ReservationTimeService reservationTimeService;
    ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @DisplayName("저장된 예약 시간을 모두 찾는다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        reservationTimeRepository.save(reservationTime);

        // when
        List<ReservationTimeResponse> responses = reservationTimeService.findAll();

        //then
        assertAll(() -> assertThat(responses).hasSize(1));
    }

    @DisplayName("id를 통해 예약 시간을 찾는다.")
    @Test
    void findById() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        reservationTimeRepository.save(reservationTime);

        // when
        ReservationTimeResponse response = reservationTimeService.findById(1);

        //then
        assertAll(() -> {
            assertThat(response.id()).isEqualTo(1);
            assertThat(response.startAt()).isEqualTo(LocalTime.of(10, 0));
        });
    }

    @DisplayName("예약 시간을 저장한다.")
    @Test
    void save() {
        // given
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.of(10, 0));

        // when
        ReservationTimeResponse response = reservationTimeService.save(request);

        //then
        assertAll(() -> {
            assertThat(response.id()).isEqualTo(1);
            assertThat(response.startAt()).isEqualTo(LocalTime.of(10, 0));
        });
    }

    @DisplayName("id를 통해 예약 시간을 삭제한다.")
    @Test
    void deleteById() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        reservationTimeRepository.save(reservationTime);

        // when
        reservationTimeService.deleteById(1);

        //then
        assertThat(reservationTimeRepository.findAll()).isEmpty();
    }
}
