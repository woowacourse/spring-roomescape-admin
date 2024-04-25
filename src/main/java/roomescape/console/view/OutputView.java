package roomescape.console.view;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservation.dto.response.ReservationTimeResponse;

@Component
public class OutputView {

    public void outputAdminMenu() {
        System.out.println("""
                
                <사용자 관리 명령어>
                1. 시간 관리 기능
                2. 예약 관리 기능
                
                """
        );
    }

    public void outputReservationTimeMenu() {
        System.out.println("""
                
                <사용자 관리 명령어>
                - 시간등록: POST hh:mm 
                    예시) POST 10:00
                - 시간조회: GET
                - 시간삭제: DELETE 1 
                    예시) DELETE 1
                    
                """
        );
    }

    public void outputReservationMenu() {
        System.out.println("""
                <사용자 관리 명령어>
                - 예약등록: POST name, yyyy-MM-dd, {id} 
                    예시) POST 브라운, 2023-08-05, 1
                - 예약조회: GET
                - 예약삭제: DELETE 1 
                    예시) DELETE 1
                """
        );
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

}
