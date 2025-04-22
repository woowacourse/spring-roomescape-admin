package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationDto;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationServiceTest {

    private ReservationService reservationService;
    private final ReservationRepository reservationRepository = new MemoryReservationRepository();
    private final ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();

    @BeforeEach
    void setUp() {
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 1, 0, 0);
        Clock clock = Clock.fixed(fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository, clock);
    }

    @Test
    void 전체_예약을_조회한다() {
        // given
        LocalDate date = LocalDate.of(2025, 12, 12);
        ReservationTime time = new ReservationTime(1L, LocalTime.of(9, 0));
        reservationRepository.add(new Reservation(null, "name1", ReservationDateTime.of(date, time)));
        reservationRepository.add(new Reservation(null, "name2", ReservationDateTime.of(date, time)));
        // when
        List<ReservationDto> allReservations = reservationService.findAllReservations();
        // then
        assertThat(allReservations).hasSize(2);
    }

    @Test
    void 예약을_생성한다() {
        // given
        ReservationTime time = new ReservationTime(null, LocalTime.of(9, 0));
        reservationTimeRepository.add(time);
        LocalDate date = LocalDate.of(2025, 12, 12);
        CreateReservationDto createReservationDto = new CreateReservationDto("name1", date, 1L);
        // when
        ReservationDto reservation = reservationService.createReservation(createReservationDto);
        // then
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(reservationRepository.findAll()).hasSize(1);
        soft.assertThat(reservation.id()).isEqualTo(1L);
        soft.assertThat(reservation.name()).isEqualTo("name1");
        soft.assertThat(reservation.date()).isEqualTo("2025-12-12");
        soft.assertThat(reservation.time().startAt()).isEqualTo("09:00");
        soft.assertAll();
    }

    @Test
    void 예약시간이_존재하지_않을_경우_예외가_발생한다() {
        // given
        LocalDate date = LocalDate.of(2025, 12, 12);
        CreateReservationDto createReservationDto = new CreateReservationDto("name1", date, 1L);
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(createReservationDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약을_삭제한다() {
        // given
        LocalDate date = LocalDate.of(2025, 12, 12);
        ReservationTime time = new ReservationTime(1L, LocalTime.of(9, 0));
        reservationRepository.add(new Reservation(null, "name1", ReservationDateTime.of(date, time)));
        // when & then
        SoftAssertions soft = new SoftAssertions();
        soft.assertThatCode(() -> reservationService.deleteReservation(1L))
                .doesNotThrowAnyException();
        soft.assertThat(reservationRepository.findAll()).hasSize(0);
        soft.assertAll();
    }

    @Test
    void 존재하지_않는_예약ID인_경우_예외가_발생한다() {
        // given
        LocalDate date = LocalDate.of(2025, 12, 12);
        ReservationTime time = new ReservationTime(1L, LocalTime.of(9, 0));
        reservationRepository.add(new Reservation(null, "name1", ReservationDateTime.of(date, time)));
        // when & then
        assertThatThrownBy(() -> reservationService.deleteReservation(2L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}