package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import roomescape.domain.ReservationTime;
import roomescape.domain.exception.ReservationTimeException;
import roomescape.persist.repository.FakeReservationTimeRepository;
import roomescape.persist.repository.ReservationTimeRepository;
import roomescape.presentation.dto.ReservationTimeRequestDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @DisplayName("예약 시간 목록을 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "10:00-11:00-12:00,3",
            "10:00-11:00,2",
            "10:00,1",
    })
    void getAllReservationsTest(String reservationTimes, int expectedSize) {
        // given
        addReservationTimesFromDelimiter(reservationTimes);

        // when
        List<ReservationTimeResponseDto> actual = reservationTimeService.getAllReservationTimes();

        // then
        assertThat(actual)
                .hasSize(expectedSize);
    }

    @DisplayName("예약 시간을 추가할 수 있다.")
    @Test
    void addReservationTimeTest() {
        // given
        ReservationTimeRequestDto reservationTimeRequestDto = new ReservationTimeRequestDto(LocalTime.of(10, 0));
        ReservationTimeResponseDto expected = new ReservationTimeResponseDto(1L, LocalTime.of(10, 0));

        // when
        ReservationTimeResponseDto actual = reservationTimeService.addReservationTime(reservationTimeRequestDto);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @DisplayName("중복된 예약 시간을 추가하면 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenAddingDuplicateReservationTime() {
        // given
        LocalTime reservationTime = LocalTime.of(10, 0);
        reservationTimeService.addReservationTime(new ReservationTimeRequestDto(reservationTime));

        // when & then
        assertThatCode(() -> reservationTimeService.addReservationTime(new ReservationTimeRequestDto(reservationTime)))
                .isInstanceOf(ReservationTimeException.class)
                .hasMessage("예약 가능한 시간은 중복될 수 없습니다.");
    }

    @DisplayName("예약 시간 추가 후 전체 조회 결과에 포함된다.")
    @Test
    void addThenFindReservationTimeTest() {
        // given
        LocalTime time = LocalTime.of(14, 0);
        reservationTimeService.addReservationTime(new ReservationTimeRequestDto(time));
        ReservationTimeResponseDto expected = new ReservationTimeResponseDto(1L, time);

        // when
        List<ReservationTimeResponseDto> actual = reservationTimeService.getAllReservationTimes();

        // then
        assertThat(actual)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @DisplayName("예약 시간을 삭제할 수 있다.")
    @Test
    void deleteReservationTimeTest() {
        // given
        insertReservationTimes(new ReservationTime(LocalTime.of(10, 0)));

        // when
        reservationTimeService.deleteReservationTime(1L);

        // then
        assertThat(reservationTimeRepository.findAll())
                .isEmpty();
    }

    @DisplayName("존재하지 않는 예약 시간을 삭제헤도 아무 일도 일어나지 않는다.")
    @Test
    void deleteNonExistentReservationTimeTest() {
        // given
        long nonExistentReservationId = 999L;

        // when & then
        assertThatCode(() -> reservationTimeService.deleteReservationTime(nonExistentReservationId))
                .doesNotThrowAnyException();
    }

    private void addReservationTimesFromDelimiter(String reservationTimes) {
        if (!reservationTimes.isEmpty()) {
            String[] times = reservationTimes.split("-");
            for (String time : times) {
                insertReservationTimes(new ReservationTime(LocalTime.parse(time)));
            }
        }
    }

    private void insertReservationTimes(ReservationTime... reservationTimes) {
        for (ReservationTime reservationTime : reservationTimes) {
            reservationTimeRepository.add(reservationTime);
        }
    }
}
