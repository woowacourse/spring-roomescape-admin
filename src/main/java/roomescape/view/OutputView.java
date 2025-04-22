package roomescape.view;

import java.util.List;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationTimeDto;

public class OutputView {

    public void printReservationTimes(List<ReservationTimeDto> reservationTimeDtos) {
        System.out.println("예약시간");
        for (ReservationTimeDto reservationTimeDto : reservationTimeDtos) {
            System.out.println(reservationTimeDto.id() + " " + reservationTimeDto.startAt());
        }
        System.out.println();
    }

    public void printReservations(List<ReservationDto> reservationDtos) {
        System.out.println("예약시간");
        for (ReservationDto reservationDto : reservationDtos) {
            System.out.println(reservationDto.id() + " " + reservationDto.name() + " " + reservationDto.date() + " "
                    + reservationDto.time().startAt());
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
