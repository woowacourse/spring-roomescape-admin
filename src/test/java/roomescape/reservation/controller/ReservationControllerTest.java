package roomescape.reservation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.reservation.service.fake.FakeReservationService;

class ReservationControllerTest {

    private FakeReservationService reservationService;
    private ReservationController reservationController;

    @BeforeEach
    void init() {
        reservationService = new FakeReservationService();
        reservationController = new ReservationController(reservationService);
    }

    @DisplayName("모든 예약 정보를 가져온다.")
    @Test
    void test1() {
        // given
        List<String> names = List.of("꾹", "드라고", "히로");
        LocalDate now = LocalDate.now();
        LocalTime time = LocalTime.now();
        Long timeId = 1L;

        reservationService.addReservationTime(timeId, new ReservationTime(timeId, time));

        for (String name : names) {
            ReservationRequestDto requestDto = new ReservationRequestDto(name, now, timeId);
            reservationService.save(requestDto);
        }

        // when
        ResponseEntity<List<ReservationResponseDto>> result = reservationController.readAllReservations();
        List<ReservationResponseDto> body = result.getBody();

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertNotNull(body);

        List<String> resultNames = body.stream().map(ReservationResponseDto::name).toList();
        List<LocalDate> resultDates = body.stream().map(ReservationResponseDto::date).toList();
        List<LocalTime> resultTimes = body.stream()
                .map(ReservationResponseDto::time)
                .map(ReservationTimeResponseDto::startAt)
                .toList();

        assertThat(resultNames).containsExactlyElementsOf(names);

        SoftAssertions softly = new SoftAssertions();
        for (LocalDate dateTime : resultDates) {
            softly.assertThat(dateTime).isEqualTo(now);
        }
        for (LocalTime resultTime : resultTimes) {
            softly.assertThat(resultTime).isEqualTo(time);
        }
        softly.assertAll();
    }

    @DisplayName("예약 정보를 추가한다.")
    @ParameterizedTest
    @CsvSource(value = {"꾹,2025-04-17,1"}, delimiter = ',')
    void test2(String name, String date, Long timeId) {
        // given
        LocalTime localTime = LocalTime.now();
        ReservationTime time = new ReservationTime(timeId, localTime);
        reservationService.addReservationTime(timeId, time);

        LocalDate localDate = LocalDate.parse(date);

        ReservationRequestDto requestDto = new ReservationRequestDto(name, localDate, timeId);
        ReservationTimeResponseDto reservationTimeResponseDto = new ReservationTimeResponseDto(timeId, localTime);
        ReservationResponseDto expected = new ReservationResponseDto(1L, name, localDate, reservationTimeResponseDto);

        // when
        ResponseEntity<ReservationResponseDto> result = reservationController.save(requestDto);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expected);
    }

    @DisplayName("예약 정보를 삭제한다.")
    @Test
    void test3() {
        // given
        long id = 1L;
        ReservationTime reservationTime = new ReservationTime(id, LocalTime.now());
        Reservation reservation = new Reservation(id, "꾹", LocalDate.now(), reservationTime);

        reservationService.addReservation(id, reservation);

        // when & then
        assertThatCode(() -> reservationController.delete(id))
                .doesNotThrowAnyException();
    }

    @DisplayName("삭제할 예약 정보가 없다면 예외를 반환한다")
    @Test
    void test4() {
        // given
        long id = 1L;

        // when & then
        assertThatThrownBy(() -> reservationController.delete(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @SpringBootTest
    @Nested
    class bean {

        @Autowired
        private ReservationController reservationController;

        @DisplayName("컨트롤러 jdbc 분리 테스트")
        @Test
        void test1() {
            boolean isJdbcTemplateInjected = false;

            for (Field field : reservationController.getClass().getDeclaredFields()) {
                if (field.getType().equals(JdbcTemplate.class)) {
                    isJdbcTemplateInjected = true;
                    break;
                }
            }

            assertThat(isJdbcTemplateInjected).isFalse();
        }
    }

}
