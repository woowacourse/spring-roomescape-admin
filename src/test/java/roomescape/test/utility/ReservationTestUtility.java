package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeFieldWithoutId;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeId;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreationRequest;

public class ReservationTestUtility {

    public static void checkReservationId(long actualId, long expectedId) {
        assertThat(actualId).isEqualTo(expectedId);
    }

    public static void checkReservationFieldWithoutId(Reservation actual, Reservation expected) {
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDate()).isEqualTo(expected.getDate());
        checkReservationTimeId(actual.getTime().getId(), expected.getTime().getId());
        checkReservationTimeFieldWithoutId(actual.getTime(), expected.getTime());
    }

    public static void checkReservationFieldWithoutId(Reservation actual, ReservationCreationRequest creationDto) {
        assertThat(actual.getName()).isEqualTo(creationDto.getName());
        assertThat(actual.getDate()).isEqualTo(creationDto.getDate());
        checkReservationTimeId(actual.getTime().getId(), creationDto.getTimeId());
    }

    public static void checkDeleteReservation(List<Reservation> reservations, long deletedId) {
        assertThat(reservations)
                .extracting(Reservation::getId)
                .doesNotContain(deletedId);
    }
}
