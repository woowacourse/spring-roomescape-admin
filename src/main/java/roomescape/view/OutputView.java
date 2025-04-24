package roomescape.view;

import java.util.List;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;

public class OutputView {

    public void printReservationTimes(List<ReservationTimeResponse> reservationTimeResponses) {
        System.out.println("예약시간");
        for (ReservationTimeResponse reservationTimeResponse : reservationTimeResponses) {
            System.out.println(reservationTimeResponse.id() + " " + reservationTimeResponse.startAt());
        }
        System.out.println();
    }

    public void printReservations(List<ReservationResponse> reservationResponses) {
        System.out.println("예약시간");
        for (ReservationResponse reservationResponse : reservationResponses) {
            System.out.println(
                    reservationResponse.id() + " " + reservationResponse.name() + " " + reservationResponse.date() + " "
                            + reservationResponse.time().startAt());
        }
        System.out.println();
    }

    public void printSuccessMessage() {
        System.out.println("작업에 성공했습니다.");
    }

    public void printErrorMessage(Exception e) {
        System.out.printf("[ERROR] %s%n", e.getMessage());
    }
}
