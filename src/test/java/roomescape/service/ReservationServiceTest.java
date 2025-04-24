package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.domain.exception.ReservationTimeException;
import roomescape.persist.repository.FakeReservationRepository;
import roomescape.persist.repository.FakeReservationTimeRepository;
import roomescape.persist.repository.ReservationRepository;
import roomescape.persist.repository.ReservationTimeRepository;
import roomescape.presentation.dto.ReservationRequestDto;
import roomescape.presentation.dto.ReservationResponseDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;

class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    public void setUp() {
        reservationRepository = new FakeReservationRepository();
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void getAllReservationsTest() {
        // given
        addTestReservation();

        // when
        List<ReservationResponseDto> reservations = reservationService.getAllReservations();

        // then
        assertThat(reservations)
                .hasSize(1);
    }

    @DisplayName("예약을 생성할 수 있다.")
    @Test
    void makeReservationTest() {
        // given
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        LocalDate date = now.toLocalDate();
        ReservationTime reservationTime = reservationTimeRepository.add(new ReservationTime(now.toLocalTime()));
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto("브라운", date, reservationTime.getId());
        ReservationResponseDto expected = new ReservationResponseDto(
                1L,
                "브라운",
                date,
                new ReservationTimeResponseDto(
                        reservationTime.getId(),
                        reservationTime.getStartTime()
                )
        );

        // when
        ReservationResponseDto actual = reservationService.makeReservation(reservationRequestDto);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 예약 가능 시간으로 예약을 생성할 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenMakingReservationWithNonExistentTime() {
        // given
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        LocalDate date = now.toLocalDate();
        long nonExistentTimeId = 999L;
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto("브라운", date, nonExistentTimeId);

        // when & then
        assertThatCode(() -> reservationService.makeReservation(reservationRequestDto))
                .isInstanceOf(ReservationTimeException.class)
                .hasMessage("예약 가능한 시간이 존재하지 않습니다.");
    }

    @DisplayName("예약을 취소할 수 있다.")
    @Test
    void cancelReservationTest() {
        // given
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        ReservationDate reservationDate = new ReservationDate(1L, now.toLocalDate());
        ReservationTime reservationTime = reservationTimeRepository.add(new ReservationTime(LocalTime.of(10, 0)));
        Reservation reservation = reservationRepository.add(new Reservation("브라운", reservationDate, reservationTime));

        // when
        reservationService.cancelReservation(reservation.getId());

        // then
        assertThat(reservationRepository.findAll())
                .isEmpty();
    }

    @DisplayName("예약을 취소할 때 존재하지 않는 예약 ID를 입력해도 예외가 발생하지 않는다.")
    @Test
    void cancelReservationWithNonExistentIdTest() {
        // given
        long nonExistentTimeId = 999L;

        // when & then
        assertThatCode(() -> reservationService.cancelReservation(nonExistentTimeId))
                .doesNotThrowAnyException();
    }

    void addTestReservation() {
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        ReservationDate reservationDate = new ReservationDate(1L, now.toLocalDate());
        ReservationTime reservationTime = reservationTimeRepository.add(new ReservationTime(LocalTime.of(10, 0)));
        reservationRepository.add(new Reservation("브라운", reservationDate, reservationTime));
    }
}
