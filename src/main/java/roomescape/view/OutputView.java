package roomescape.view;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationTimeResponse;

@Component
public class OutputView {
    public void printMainScreen() {
        System.out.println("""
                *** 시간, 예약 관리 프로그램 1.0 ***
                * 명령어를 입력해주세요 (숫자 0~6) *
                *      0. 프로그램 종료            *
                *      1. 예약 시간 조회           *
                *      2. 예약 시간 추가           *
                *      3. 예약 시간 삭제           *
                *      4. 예약 조회                *
                *      5. 예약 추가                *
                *      6. 예약 삭제                *
                ************************************
                """);
    }

    public void print(final String read) {
        System.out.println(read);
    }

    public void printReservationTimes(final List<ReservationTimeResponse> responses) {
        System.out.println("------------------------------------");
        System.out.println("예약 시간 조회 결과:");
        for (final ReservationTimeResponse response : responses) {
            System.out.println("ID:" + response.id() + " TIME:" + response.startAt());
        }
        System.out.println("------------------------------------");
    }

    public void printDeletedMessage() {
        System.out.println("예약 시간이 삭제되었습니다.");
    }

    public void printCreateReservationTimeResult() {
        System.out.println("------------------------------------");
        System.out.println("예약 시간이 추가되었습니다.");
        System.out.println("------------------------------------");
    }
}
