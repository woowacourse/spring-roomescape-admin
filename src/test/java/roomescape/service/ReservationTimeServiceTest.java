package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
    private final ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();

    @BeforeEach
    void setUp() {
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @Test
    void 모든_예약시간을_조회한다() {
        // given
        ReservationTime reservationTime1 = new ReservationTime(null, LocalTime.of(9, 0));
        ReservationTime reservationTime2 = new ReservationTime(null, LocalTime.of(10, 0));
        reservationTimeRepository.add(reservationTime1);
        reservationTimeRepository.add(reservationTime2);
        // when
        List<ReservationTimeDto> times = reservationTimeService.findAllReservationTime();
        // then
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(reservationTimeRepository.findAll()).hasSize(2);
        soft.assertThat(times.getFirst().id()).isEqualTo(1L);
        soft.assertThat(times.getFirst().startAt()).isEqualTo(LocalTime.of(9, 0));
        soft.assertThat(times.get(1).id()).isEqualTo(2L);
        soft.assertThat(times.get(1).startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 예약시간을_추가한다() {
        // given
        CreateReservationTimeDto createReservationTimeDto = new CreateReservationTimeDto(LocalTime.of(9, 0));
        // when
        ReservationTimeDto reservationTime = reservationTimeService.createReservationTime(createReservationTimeDto);
        // then
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(reservationTimeRepository.findAll()).hasSize(1);
        soft.assertThat(reservationTime.id()).isEqualTo(1L);
        soft.assertThat(reservationTime.startAt()).isEqualTo(LocalTime.of(9, 0));
        soft.assertAll();
    }

    @Test
    void 시간을_삭제한다() {
        // given
        reservationTimeRepository.add(new ReservationTime(null, LocalTime.of(9, 0)));
        // when & then
        reservationTimeService.deleteReservationTime(1L);
        SoftAssertions soft = new SoftAssertions();
        soft.assertThatCode(() -> reservationTimeService.deleteReservationTime(1L))
                .doesNotThrowAnyException();
        soft.assertThat(reservationTimeRepository.findAll()).hasSize(0);
    }

    @Test
    void 시간을_삭제할_때_존재하지_않는_시간ID인_경우_예외가_발생한다() {
        // given
        reservationTimeRepository.add(new ReservationTime(null, LocalTime.of(9, 0)));
        // when & then
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(2L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}