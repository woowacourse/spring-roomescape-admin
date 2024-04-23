package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationTimeDao;
import roomescape.dao.fakedao.FakeReservationDao;
import roomescape.dao.fakedao.FakeReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

class ReservationServiceTest {
    private ReservationService reservationService;
    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new FakeReservationTimeDao();
        reservationService = new ReservationService(
                new FakeReservationDao(), reservationTimeDao);
    }

    @DisplayName("모든 예약 검색")
    @Test
    void findAll() {
        assertThat(reservationService.findAll()).isEmpty();
    }

    @DisplayName("예약 저장")
    @Test
    void save() {
        //given
        reservationTimeDao.save("10:00");
        ReservationRequest reservationRequest = new ReservationRequest("aa", "2024-10-10", 1);
        //when
        ReservationResponse response = reservationService.save(reservationRequest);
        //then
        assertAll(
                () -> assertThat(reservationService.findAll()).hasSize(1),
                () -> assertThat(response.id()).isEqualTo(1),
                () -> assertThat(response.name()).isEqualTo("aa"),
                () -> assertThat(response.date()).isEqualTo("2024-10-10"),
                () -> assertThat(response.time().id()).isEqualTo(1),
                () -> assertThat(response.time().startAt()).isEqualTo("10:00")
        );
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteById() {
        //given
        reservationTimeDao.save("10:00");
        reservationService.save(new ReservationRequest("aa", "2024-10-10", 1));
        //when
        reservationService.deleteById(1);
        //then
        assertThat(reservationService.findAll()).isEmpty();
    }
}
