package roomescape.reservationtime;

import org.springframework.http.HttpStatus;

public class ReservationTimeException extends RuntimeException {
    private final HttpStatus status;
    private final String userMessage;

    public ReservationTimeException(HttpStatus status, String userMessage) {
        super(userMessage);
        this.status = status;
        this.userMessage = userMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
