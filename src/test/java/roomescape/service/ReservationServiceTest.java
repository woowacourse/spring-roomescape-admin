package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.controller.dto.ReservationRequest;
import roomescape.reservation.controller.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;
import roomescape.service.stub.StubReservationRepository;
import roomescape.service.stub.StubReservationTimeRepository;
import roomescape.time.controller.dto.ReservationTimeRequest;
import roomescape.time.controller.dto.ReservationTimeResponse;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;
import roomescape.time.service.ReservationTimeService;

public class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setup(){
        reservationTimeService = new ReservationTimeService(new StubReservationTimeRepository());
        reservationService = new ReservationService(new StubReservationRepository(),reservationTimeService);
    }

    @Test
    @DisplayName("예약 저장")
    void save_test() {
        // given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.parse("10:00"));
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.save(reservationTimeRequest);
        ReservationRequest reservationRequest = new ReservationRequest("쿠다", LocalDate.parse("2023-08-06"),
                reservationTimeResponse.id());

        // when
        ReservationResponse result = reservationService.save(reservationRequest);
        ReservationResponse saved = reservationService.getById(result.id());

        // then
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo(reservationRequest.name());
        assertThat(result.date()).isEqualTo(reservationRequest.date());
        assertThat(result.reservationTime().getId()).isEqualTo(reservationRequest.timeId());

        assertThat(saved.name()).isEqualTo(result.name());
        assertThat(saved.date()).isEqualTo(result.date());
        assertThat(saved.reservationTime().getId()).isEqualTo(result.reservationTime().getId());
    }

    @Test
    @DisplayName("예약 저장 중복 예외")
    void reservation_save_duplicate_test() {
        // given
        LocalDate date = LocalDate.parse("2026-08-06");

        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.parse("10:00"));
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.save(reservationTimeRequest);

        ReservationRequest reservationRequest = new ReservationRequest("쿠다", date, reservationTimeResponse.id());
        ReservationRequest newReservationRequest = new ReservationRequest("아루", date, reservationTimeResponse.id());

        // when
        reservationService.save(reservationRequest);

        // then
        assertThatThrownBy(() -> reservationService.save(newReservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복으로 예약을 생성할 수 없습니다.");
    }

    @Test
    @DisplayName("예약 단일 조회 id 없음 예외")
    void reservation_findById_null_search_test() {
        //given & when & then
        assertThatThrownBy(() -> reservationService.getById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("예약을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("예약 삭제 시 없는 예약 삭제 예외")
    void reservation_delete_non_exists_test() {
        // given & when & then
        assertThatThrownBy(() -> reservationService.deleteById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("삭제할 예약이 존재하지 않습니다.");
    }
}
