package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.SaveReservationRequest;
import roomescape.dto.SaveReservationTimeRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "/schema.sql", executionPhase = BEFORE_TEST_METHOD)
class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @DisplayName("전체 예약 정보를 조회한다.")
    @Test
    void getReservationsTest() {
        // When
        List<Reservation> reservations = reservationService.getReservations();

        // Then
        assertThat(reservations).hasSize(2);
    }

    @DisplayName("예약 정보를 저장한다.")
    @Test
    void saveReservationTest() {
        // Given
        LocalDate date = LocalDate.now().plusDays(3);
        SaveReservationRequest saveReservationRequest = new SaveReservationRequest(date, "켈리", 1L);

        // When
        Reservation reservation = reservationService.saveReservation(saveReservationRequest);

        // Then
        List<Reservation> reservations = reservationService.getReservations();
        assertAll(
                () -> assertThat(reservations).hasSize(3),
                () -> assertThat(reservation.getId()).isEqualTo(3L),
                () -> assertThat(reservation.getClientName().getValue()).isEqualTo("켈리"),
                () -> assertThat(reservation.getDate()).isEqualTo(date),
                () -> assertThat(reservation.getTime().getStartAt()).isEqualTo(LocalTime.of(10, 10))
        );
    }

    @DisplayName("저장하려는 예약 시간이 존재하지 않는다면 예외를 발생시킨다.")
    @Test
    void throwExceptionWhenSaveReservationWithNotExistReservationTimeTest() {
        // Given
        SaveReservationRequest saveReservationRequest = new SaveReservationRequest(LocalDate.now(), "켈리", 3L);

        // When & Then
        assertThatThrownBy(() -> reservationService.saveReservation(saveReservationRequest))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 id의 예약 시간이 존재하지 않습니다.");
    }

    @DisplayName("예약 정보를 삭제한다.")
    @Test
    void deleteReservationTest() {
        // When
        reservationService.deleteReservation(1L);

        // When & Then
        List<Reservation> reservations = reservationService.getReservations();
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("존재하지 않는 예약 정보를 삭제하려고 하면 예외가 발생한다.")
    @Test
    void throwExceptionWhenDeleteNotExistReservationTest() {
        // When & Then
        assertThatThrownBy(() -> reservationService.deleteReservation(3L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 id의 예약이 존재하지 않습니다.");
    }

    @DisplayName("전체 예약 시간 정보를 조회한다.")
    @Test
    void getReservationTimesTest() {
        // When
        List<ReservationTime> reservationTimes = reservationService.getReservationTimes();

        // Then
        assertThat(reservationTimes).hasSize(2);
    }

    @DisplayName("예약 시간 정보를 저장한다.")
    @Test
    void saveReservationTimeTest() {
        // Given
        LocalTime startAt = LocalTime.now().plusHours(3);
        SaveReservationTimeRequest saveReservationTimeRequest = new SaveReservationTimeRequest(startAt);

        // When
        ReservationTime reservationTime = reservationService.saveReservationTime(saveReservationTimeRequest);

        // Then
        List<ReservationTime> reservationTimes = reservationService.getReservationTimes();
        Assertions.assertAll(
                () -> assertThat(reservationTimes).hasSize(3),
                () -> assertThat(reservationTime.getId()).isEqualTo(3L),
                () -> assertThat(reservationTime.getStartAt()).isEqualTo(startAt)
        );
    }

    @DisplayName("예약 시간 정보를 삭제한다.")
    @Test
    void deleteReservationTimeTest() {
        // When
        reservationService.deleteReservationTime(2L);

        // Then
        List<ReservationTime> reservationTimes = reservationService.getReservationTimes();
        assertThat(reservationTimes).hasSize(1);
    }

    @DisplayName("존재하지 않는 예약 시간 정보를 삭제하려고 하면 예외가 발생한다.")
    @Test
    void throwExceptionWhenDeleteNotExistReservationTimeTest() {
        // When & Then
        assertThatThrownBy(() -> reservationService.deleteReservationTime(3L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 id의 예약 시간이 존재하지 않습니다.");
    }
}
