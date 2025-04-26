package roomescape.reservationTime.fixture;

import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.domain.dto.ReservationTimeReqDto;

import java.time.LocalTime;

public class ReservationTimeFixture {

    public static ReservationTimeReqDto createReservationTimeReqDto(LocalTime time) {
        return new ReservationTimeReqDto(time);
    }

    public static ReservationTime createReservationTime(LocalTime time) {
        ReservationTimeReqDto reqDto = createReservationTimeReqDto(time);
        return ReservationTime.from(reqDto);
    }
}
