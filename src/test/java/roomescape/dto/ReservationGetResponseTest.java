package roomescape.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.model.EntityId;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationGetResponseTest {

    @Test
    void Reservation_엔티티로부터_해당_Dto를_파싱한다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(EntityId.generate(1L), startAt);
        Reservation reservation = new Reservation(EntityId.generate(1L), "프리", LocalDate.of(2025, 4, 24), reservationTime);

        // When
        ReservationGetResponse reservationGetResponse = ReservationGetResponse.from(reservation);

        // Then
        assertThat(reservationGetResponse.id()).isEqualTo(1L);
        assertThat(reservationGetResponse.name()).isEqualTo("프리");
        assertThat(reservationGetResponse.date()).isEqualTo(LocalDate.of(2025, 4, 24));
        assertThat(reservationGetResponse.time()).isEqualTo(ReservationTimeGetResponse.from(reservationTime));
    }
}
