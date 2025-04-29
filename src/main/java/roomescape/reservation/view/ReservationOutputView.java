package roomescape.reservation.view;

import roomescape.reservation.domain.dto.ReservationResDto;

import java.util.List;

public class ReservationOutputView {

    public void printAllReservations(List<ReservationResDto> resDtos) {
        System.out.println("아래는 예약 시간 리스트 입니다.");
        for (ReservationResDto resDto : resDtos) {
            printReservation(resDto);
        }

        System.out.println();
    }

    public void printSavedReservation(ReservationResDto resDto) {
        System.out.println("다음과 같이 예약이 추가되었습니다.");
        printReservation(resDto);
        System.out.println();
    }

    public void printDeleteReservationResult() {
        System.out.println("예약 삭제가 정상적으로 진행되었습니다.");
        System.out.println();
    }

    public void printDeleteReservationResult(String errorMessage) {
        System.out.println("[삭제 실패] " + errorMessage);
        System.out.println();
    }

    public void printAddReservationInfo() {
        System.out.println("예약 추가 기능을 고르셨습니다.");
    }

    public void printDeleteReservationInfo() {
        System.out.println("예약 삭제 기능을 고르셨습니다.");
    }

    public void printAllReservationsInfo() {
        System.out.println("예약 리스트 반환 기능을 고르셨습니다.");
    }

    private void printReservation(ReservationResDto dto) {
        System.out.println("  {");
        System.out.println("    \"id\": " + dto.id() + ",");
        System.out.println("    \"name\": \"" + dto.name() + "\",");
        System.out.println("    \"date\": \"" + dto.date() + "\",");
        System.out.println("    \"reservationTimeResDto\": {");
        System.out.println("      \"id\": " + dto.reservationTimeResDto().id() + ",");
        System.out.println("      \"startAt\": \"" + dto.reservationTimeResDto().startAt() + "\"");
        System.out.println("    }");
        System.out.println("  }");
    }
}
