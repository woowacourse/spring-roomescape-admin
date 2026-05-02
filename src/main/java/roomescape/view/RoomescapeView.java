package roomescape.view;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.util.Retry;

public class RoomescapeView {

    private final InputView in = new InputView();
    private final OutputView out = new OutputView();

    public InputMenu askMenu() {
        return Retry.untilSuccess(() -> {
            out.askMenu();
            out.printMenu();
            return in.readMenu();
        });
    }

    public void printReservationTimes(List<ReservationTimeResponse> reservationTimes) {
        out.printReservationTimes(reservationTimes);
    }

    public ReservationTimeRequest askReservationTimeRequest() {
        return Retry.untilSuccess(() -> {
            out.askReservationTime();
            return in.readReservationTime();
        });
    }

    public void printReservationTime(ReservationTimeResponse reservationTimeResponse) {
        out.printReservationTime(reservationTimeResponse);
    }

    public long askReservationTimeId() {
        return Retry.untilSuccess(() -> {
            out.askReservationTimeId();
            return in.readLongId();
        });
    }

    public void printReservations(List<ReservationResponse> reservations) {
        out.printReservations(reservations);
    }

    public void printReservation(ReservationResponse reservationResponse) {
        out.printReservation(reservationResponse);
    }

    public ReservationRequest askReservationRequest() {
        return Retry.untilSuccess(() -> {
            out.askReservation();
            return in.readReservation();
        });
    }

    public long askReservationId() {
        return Retry.untilSuccess(() -> {
            out.askReservationId();
            return in.readLongId();
        });
    }
}
