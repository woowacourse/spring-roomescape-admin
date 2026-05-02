package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.exception.CustomException;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void notExistReservationDeleteExceptionTest() {
        assertThatThrownBy(() -> reservationTimeService.delete(1L))
                .hasMessage("[ERROR] 해당 ID의 예약 시간을 찾을 수 없습니다.")
                .isInstanceOf(CustomException.class);
    }

    @Test
    void createTest() {
        ReservationTimeResponseDto responseDto = reservationTimeService.create(
                new ReservationTimeRequestDto(LocalTime.of(10, 0)));

        assertThat(responseDto).isEqualTo(new ReservationTimeResponseDto(1L, LocalTime.of(10, 0)));
    }

    @Test
    void readAllTest() {
        reservationTimeService.create(new ReservationTimeRequestDto(LocalTime.of(10, 0)));
        reservationTimeService.create(new ReservationTimeRequestDto(LocalTime.of(11, 0)));

        List<ReservationTimeResponseDto> responseDtos = reservationTimeService.readAll();

        assertThat(responseDtos.getFirst()).isEqualTo(new ReservationTimeResponseDto(1L, LocalTime.of(10, 0)));
        assertThat(responseDtos.get(1)).isEqualTo(new ReservationTimeResponseDto(2L, LocalTime.of(11, 0)));
    }

    @Test
    void deleteTest() {
        reservationTimeService.create(new ReservationTimeRequestDto(LocalTime.of(10, 0)));
        reservationTimeService.delete(1L);

        List<ReservationTimeResponseDto> responseDtos = reservationTimeService.readAll();

        assertThat(responseDtos.size()).isEqualTo(0);
    }
}
