package roomescape.view;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public int selectAdminFunction() {
        System.out.println("이용하려는 기능을 선택해 주세요.");
        System.out.println("1. 예약 시간 관리");
        System.out.println("2. 예약 관리");
        return scanner.nextInt();
    }

    public int selectReservationTimeFunction() {
        System.out.println("예약 시간 관리 기능입니다. 실행할 기능을 선택해 주세요.");
        System.out.println("1. 예약 시간 추가");
        System.out.println("2. 예약 시간 목록 조회");
        System.out.println("3. 예약 시간 삭제");
        return scanner.nextInt();
    }

    public int selectReservationFunction() {
        System.out.println("예약 관리 기능입니다. 실행할 기능을 선택해 주세요.");
        System.out.println("1. 예약 추가");
        System.out.println("2. 예약 목록 조회");
        System.out.println("3. 예약 삭제");
        return scanner.nextInt();
    }
}
