package roomescape.reservationTime.view;

import roomescape.reservationTime.domain.dto.ReservationTimeResDto;

import java.util.List;

public class ReservationTimeOutputView {

    public void printAllReservationTimes(List<ReservationTimeResDto> resDtos) {
        System.out.println("아래는 예약 시간 리스트 입니다.");
        for (ReservationTimeResDto resDto : resDtos) {
            printReservationTime(resDto);
        }
    }

    public void printSavedReservationTime(ReservationTimeResDto resDto) {
        System.out.println("다음과 같이 예약 시간이 추가되었습니다.");
        printReservationTime(resDto);
    }

    public void printDeleteReservationTimeResult() {
        System.out.println("예약 시간 삭제가 정상적으로 진행되었습니다.");
    }

    public void printAllReservationTimesInfo() {
        System.out.println("예약 시간 리스트 반환 기능을 고르셨습니다.");
    }

    public void printAddReservationTimeInfo() {
        System.out.println("예약 시간 추가 기능을 고르셨습니다.");
    }

    public void printDeleteReservationTimeInfo() {
        System.out.println("예약이 정상적으로 삭제되었습니다.");
    }

    private void printReservationTime(ReservationTimeResDto dto) {
        System.out.println("  {");
        System.out.println("    \"id\": " + dto.id() + ",");
        System.out.println("    \"startAt\": \"" + dto.startAt() + "\"");
        System.out.println("  }");
    }
}
