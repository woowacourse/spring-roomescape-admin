package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.fakedao.FakeReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

class ReservationTimeServiceTest {
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao());
    }

    @DisplayName("존재하는 모든 예약 시간을 반환한다.")
    @Test
    void findAll() {
        assertThat(reservationTimeService.findAll()).isEmpty();
    }

    @DisplayName("예약 시간을 저장한다.")
    @Test
    void save() {
        //given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("10:00");
        //when
        ReservationTimeResponse response = reservationTimeService.save(reservationTimeRequest);
        //then
        assertAll(
                () -> assertThat(reservationTimeService.findAll()).hasSize(1),
                () -> assertThat(response.id()).isEqualTo(1),
                () -> assertThat(response.startAt()).isEqualTo("10:00")
        );
    }

    @DisplayName("예약 시간을 삭제한다.")
    @Test
    void deleteById() {
        //given
        reservationTimeService.save(new ReservationTimeRequest("10:00"));
        //when
        reservationTimeService.deleteById(1);
        //then
        assertThat(reservationTimeService.findAll()).isEmpty();
    }
}
