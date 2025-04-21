package roomescape.console.view;

import java.util.List;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;

public class ConsoleOutputView {

    public void printCreateTimeTitle() {
        System.out.println("시간 추가");
    }

    public void printCreateReservationTitle() {
        System.out.println("예약 추가");
    }

    public void printTimes(List<ReservationTimeResponse> responses) {
        for (ReservationTimeResponse response : responses) {
            System.out.printf("%d: %s\n", response.id(), response.startAt());
        }
    }

    public void printReservation(ReservationResponse response) {
        System.out.println("예약 성공");
        System.out.printf("id: %d\n", response.id());
        System.out.printf("name: %s\n", response.name());
        System.out.printf("date: %s\n", response.date());
        System.out.printf("time: id-%d: time-%s\n", response.time().id(), response.time().startAt());
    }
}
