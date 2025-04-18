package roomescape.reservation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.ReservationRepositoryImpl;

class ReservationControllerTest {

    private ReservationRepository reservationRepository;
    private ReservationController reservationController;

    @BeforeEach
    void init() {
        reservationRepository = new ReservationRepositoryImpl();
        reservationController = new ReservationController(reservationRepository);
    }

    @DisplayName("모든 예약 정보를 가져온다.")
    @Test
    void test1() {
        // given
        LocalDateTime now = LocalDateTime.now();

        List<Reservation> reservations = List.of(
                new Reservation(1, "꾹", now),
                new Reservation(2, "꾹", now),
                new Reservation(3, "꾹", now)
        );

        List<ReservationResponseDto> expected = reservations.stream()
                .map(ReservationResponseDto::toDto)
                .toList();

        for (Reservation reservation : reservations) {
            reservationRepository.save(reservation);
        }

        // when
        ResponseEntity<List<ReservationResponseDto>> result = reservationController.readAllReservations();

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expected);
    }

    @DisplayName("예약 정보를 추가한다.")
    @ParameterizedTest
    @CsvSource(value = {"꾹,2025-04-17,10:00"}, delimiter = ',')
    void test2(String name, String date, String time) {
        // given
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        ReservationRequestDto requestDto = new ReservationRequestDto(name, localDate, localTime);
        ReservationResponseDto expected = new ReservationResponseDto(1L, name, localDate, localTime);

        // when
        ResponseEntity<ReservationResponseDto> result = reservationController.add(requestDto);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expected);
    }

    @DisplayName("예약 정보를 삭제한다.")
    @Test
    void test3() {
        // given
        long id = 1L;
        Reservation reservation = new Reservation(id, "꾹", LocalDateTime.now());
        reservationRepository.save(reservation);

        // when & then
        assertThatCode(() -> reservationController.delete(id))
                .doesNotThrowAnyException();
    }

    @DisplayName("삭제할 예약 정보가 없다면 404 에러 코드를 반환한다")
    @Test
    void test4() {
        // given
        long id = 1L;

        // when & then
        assertThatThrownBy(() -> reservationController.delete(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

}
