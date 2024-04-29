package roomescape.repository;

import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

public record ReservationRegisterDetail(String name, String date, long timeId, String timeValue) {

    public ReservationRegisterDetail(ReservationRequest request, String timeValue) {
        this(request.name(), request.date(), request.timeId(), timeValue);
    }

    public Reservation toEntity(long id) {
        return new Reservation(id, name, date, new ReservationTime(timeId, timeValue));
    }
}
