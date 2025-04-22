package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.ReservationTime;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationTimeRepository;
import roomescape.reservation.repository.fake.FakeReservationTimeRepository;
import roomescape.reservation.service.impl.ReservationTimeServiceImpl;

public class ReservationTimeServiceTest {

    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationTimeService = new ReservationTimeServiceImpl(reservationTimeRepository);
    }

    @DisplayName("모든 시간 정보를 가져온다.")
    @Test
    void test1() {
        // given
        LocalTime localTime1 = LocalTime.of(8, 0);
        LocalTime localTime2 = LocalTime.of(9, 0);
        List<LocalTime> localTimes = List.of(localTime1, localTime2);

        for (LocalTime localTime : localTimes) {
            reservationTimeRepository.save(ReservationTime.withoutId(localTime));
        }

        // when
        List<ReservationTimeResponseDto> result = reservationTimeService.getAll();

        // then
        List<LocalTime> resultTimes = result.stream().map(ReservationTimeResponseDto::startAt).toList();
        assertThat(resultTimes).containsExactlyInAnyOrderElementsOf(localTimes);
    }

    @DisplayName("정보가 없다면 빈 리스트를 반환한다.")
    @Test
    void test2() {
        // given & when
        List<ReservationTimeResponseDto> result = reservationTimeService.getAll();

        // then
        assertThat(result).isEmpty();
    }

    @DisplayName("예약 시간을 저장한다.")
    @Test
    void test3() {
        // given
        LocalTime localTime1 = LocalTime.of(8, 0);
        ReservationTimeRequestDto reservationTimeRequestDto = new ReservationTimeRequestDto(localTime1);

        // when
        ReservationTimeResponseDto result = reservationTimeService.save(reservationTimeRequestDto);

        // then
        assertThat(result.id()).isNotNull();
        assertThat(result.startAt()).isEqualTo(localTime1);
    }

    @DisplayName("예약 시간을 삭제한다")
    @Test
    void test4() {
        // given
        ReservationTime saved = reservationTimeRepository.save(ReservationTime.withoutId(LocalTime.of(8, 0)));
        Long id = saved.getId();

        // when & then
        assertThatCode(() -> reservationTimeService.delete(id)).doesNotThrowAnyException();
    }

    @DisplayName("예약 시간이 존재하지 않으면 예외를 반환한다.")
    @Test
    void test5(){
        Long id = 1L;

        assertThatThrownBy(() -> reservationTimeRepository.deleteById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

}
