package roomescape.console.view;

import java.util.List;
import roomescape.web.dto.response.ReservationResponse;
import roomescape.web.dto.response.ReservationTimeResponse;

public class OutputView {

    private static final OutputView INSTANCE = new OutputView();

    private OutputView() {
    }

    public void outputPostReservation(ReservationResponse reservationResponse) {
        System.out.println("예약이 성공적으로 등록되었습니다.");
        System.out.println(formatReservation(reservationResponse));
    }

    public void outputPostReservationTime(ReservationTimeResponse reservationTimeResponse) {
        System.out.println("시간이 성공적으로 등록되었습니다.");
        System.out.println(formatReservationTime(reservationTimeResponse));
    }

    public void outputGetReservations(List<ReservationResponse> reservationResponses) {
        System.out.println("<예약 정보 목록>");
        for (ReservationResponse reservationResponse : reservationResponses) {
            System.out.println("- " + formatReservation(reservationResponse));
        }
    }

    public void outputGetReservationTimes(List<ReservationTimeResponse> reservationTimeResponses) {
        System.out.println("<시간 등록 목록>");
        for (ReservationTimeResponse response : reservationTimeResponses) {
            System.out.println("- " + formatReservationTime(response));
        }
    }

    public void outputDeleteReservation() {
        System.out.println("예약이 성공적으로 삭제되었습니다.");
    }

    public void outputDeleteReservationTime(Long id) {
        System.out.printf("%d번 예약이 성공적으로 삭제되었습니다.\n", id);
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private String formatReservation(ReservationResponse reservationResponse) {
        return String.format(
                """
                        예약 번호: %d
                        예약 이름: %s
                        예약 날짜: %s
                        예약 시간: %s""",
                reservationResponse.id(),
                reservationResponse.name(),
                reservationResponse.date(),
                reservationResponse.time().startAt()
        );
    }

    private String formatReservationTime(ReservationTimeResponse reservationTimeResponse) {
        return String.format(
                "시간 번호: %d 시간: %s",
                reservationTimeResponse.id(),
                reservationTimeResponse.startAt()
        );
    }

    public static OutputView getInstance() {
        return INSTANCE;
    }
}
