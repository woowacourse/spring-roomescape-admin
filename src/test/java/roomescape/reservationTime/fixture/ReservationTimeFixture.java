package roomescape.reservationTime.fixture;

import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.domain.dto.ReservationTimeReqDto;

import java.time.LocalTime;

public class ReservationTimeFixture {

    public static ReservationTimeReqDto createReqDto(LocalTime time) {
        return new ReservationTimeReqDto(time);
    }

    public static ReservationTime create(LocalTime time) {
        ReservationTimeReqDto reqDto = createReqDto(time);
        return ReservationTime.from(reqDto);
    }
}
