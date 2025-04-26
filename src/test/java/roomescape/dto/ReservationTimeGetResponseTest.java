package roomescape.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.model.EntityId;
import roomescape.model.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTimeGetResponseTest {

    @Test
    void ReservationTime_엔티티로부터_해당_Dto를_파싱한다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(EntityId.generate(1L), startAt);

        // When
        ReservationTimeGetResponse reservationTimeGetResponse = ReservationTimeGetResponse.from(reservationTime);

        // Then
        assertThat(reservationTimeGetResponse.id()).isEqualTo(1L);
        assertThat(reservationTimeGetResponse.startAt()).isEqualTo(startAt);
    }
}
