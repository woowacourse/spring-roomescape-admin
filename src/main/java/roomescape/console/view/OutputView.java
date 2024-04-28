package roomescape.console.view;

import roomescape.dto.response.ReservationsResponse;
import roomescape.dto.response.TimesResponse;

import java.util.List;

public class OutputView {

    public void printTime(TimesResponse timesResponse) {
        System.out.println("추가된 시간");
        printTimeInfo(timesResponse);
    }

    public void printTimes(List<TimesResponse> allTimes) {
        System.out.println("추가된 시간");
        for (TimesResponse allTime : allTimes) {
            printTimeInfo(allTime);
        }
    }

    private void printTimeInfo(TimesResponse timesResponse) {
        System.out.println("ID : " + timesResponse.id());
        System.out.println("시간 : " + timesResponse.startAt());
        System.out.println();
    }

    public void printDelete(int count) {
        System.out.println(count + "개의 데이터가 성공적으로 삭제되었습니다.");
        System.out.println();
    }

    public void printAddedReservation(ReservationsResponse reservationsResponse) {
        System.out.println("예약이 성공적으로 추가되었습니다.");
        System.out.println();
        printReservation(reservationsResponse);
    }

    private void printReservation(ReservationsResponse reservationsResponse) {
        System.out.println("ID : " + reservationsResponse.id());
        System.out.println("예약자 : " + reservationsResponse.name());
        System.out.println("날짜 : " + reservationsResponse.date());
        System.out.println("시간 : " + reservationsResponse.time().getStartAt());
        System.out.println();
    }

    public void printReservations(List<ReservationsResponse> allReservations) {
        System.out.println("""
                예약자 리스트
                =======================
                """);
        allReservations.forEach(this::printReservation);
    }
}
