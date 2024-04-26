package roomescape.entity.exception;

import roomescape.controller.exception.IllegalRequestException;

public class ReservationDateIsPastException extends IllegalRequestException {
    public ReservationDateIsPastException(String s) {
        super(s);
    }
}
