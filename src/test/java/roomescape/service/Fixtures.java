package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class Fixtures {

    public static final ReservationTimeRequest THIRD_TIME_REQUEST = new ReservationTimeRequest(LocalTime.of(12, 30));
    public static final ReservationTimeResponse FIRST_TIME_RESPONSE = new ReservationTimeResponse(1L, LocalTime.of(10, 5));
    public static final ReservationTimeResponse SECOND_TIME_RESPONSE = new ReservationTimeResponse(2L, LocalTime.of(9, 15));
    public static final ReservationTimeResponse THIRD_TIME_RESPONSE = new ReservationTimeResponse(3L, LocalTime.of(12, 30));
    public static final ReservationTime FIRST_TIME = new ReservationTime(1L, LocalTime.of(10, 5));
    public static final ReservationTime SECOND_TIME = new ReservationTime(2L, LocalTime.of(9, 15));
    public static final ReservationTime THIRD_TIME = new ReservationTime(3L, LocalTime.of(12, 30));
    public static final ReservationRequest THIRD_REQUEST = new ReservationRequest("third", LocalDate.of(2024, 1, 2), 2L);
    public static final ReservationResponse FIRST_RESPONSE = new ReservationResponse(1L, "first", LocalDate.of(2024, 5, 10), FIRST_TIME_RESPONSE);
    public static final ReservationResponse SECOND_RESPONSE = new ReservationResponse(2L, "second", LocalDate.of(2024, 10, 6), SECOND_TIME_RESPONSE);
    public static final ReservationResponse THIRD_RESPONSE = new ReservationResponse(3L, "third", LocalDate.of(2024, 1, 2), SECOND_TIME_RESPONSE);
    public static final Reservation FIRST_RESERVATION = new Reservation(1L, "first", LocalDate.of(2024, 5, 10), FIRST_TIME);
    public static final Reservation SECOND_RESERVATION = new Reservation(2L, "second", LocalDate.of(2024, 10, 6), SECOND_TIME);
    public static final Reservation THIRD_RESERVATION = new Reservation(3L, "third", LocalDate.of(2024, 1, 2), SECOND_TIME);

}
