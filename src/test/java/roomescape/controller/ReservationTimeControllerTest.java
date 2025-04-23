package roomescape.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.ResponseEntity;
import roomescape.domain.ReservationTime;
import roomescape.repository.fake.FakeReservationTimeRepository;

class ReservationTimeControllerTest {
    private final ReservationTimeController reservationTimeController = new ReservationTimeController(new FakeReservationTimeRepository());

    @ParameterizedTest
    @CsvSource(value = {"18:00"})
    void create(LocalTime startAt) {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, startAt);

        // when
        ResponseEntity<ReservationTime> actualResponse = reservationTimeController.create(reservationTime);

        // then
        assertAll(
                () -> Assertions.assertThat(actualResponse.getStatusCode().is2xxSuccessful()).isTrue(),
                () -> Assertions.assertThat(actualResponse.getBody().getStartAt()).isEqualTo(reservationTime.getStartAt())
        );
    }

    @Test
    void read() {
        // given
        LocalTime givenLocalTime = LocalTime.now();
        create(givenLocalTime);

        // when
        List<ReservationTime> actualResponse = reservationTimeController.read().getBody();

        // then
        Assertions.assertThat(actualResponse).hasSize(1);
        Assertions.assertThat(actualResponse.getFirst().getStartAt()).isEqualTo(givenLocalTime);
    }

    @Test
    void delete() {
        // given
        LocalTime givenLocalTime = LocalTime.now();
        create(givenLocalTime);

        // when
        reservationTimeController.delete(1L);

        // then
        List<ReservationTime> actualResponse = reservationTimeController.read().getBody();
        Assertions.assertThat(actualResponse).isEmpty();
    }
}
