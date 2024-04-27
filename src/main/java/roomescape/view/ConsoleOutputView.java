package roomescape.view;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimesResponse;
import roomescape.dto.response.ReservationsResponse;

@Component
@Profile("console")
public class ConsoleOutputView {
    private ConsoleOutputView() {}

    public void printReservations(final ReservationsResponse reservationsResponse) {
        System.out.println("현재 예약 목록 조회");
        reservationsResponse.reservations()
                            .forEach(System.out::println);
    }

    public void printReservationCreate(final ReservationCreateResponse reservationCreateResponse) {
        System.out.println("예약이 생성되었습니다.");
        System.out.println(reservationCreateResponse);
    }

    public void printReservationTimes(final ReservationTimesResponse reservationTimesResponse) {
        System.out.println("현재 예약 시간 목록 조회");
        reservationTimesResponse.times()
                                .forEach(System.out::println);
    }

    public void printReservationTimeCreate(final ReservationTimeCreateResponse reservationTimeCreateResponse) {
        System.out.println("예약 시간이 생성되었습니다");
        System.out.println(reservationTimeCreateResponse);
    }

    public void printErrorMessage(final String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
    }


}
