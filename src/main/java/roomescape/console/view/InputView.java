package roomescape.console.view;

import java.util.Scanner;
import org.springframework.stereotype.Component;
import roomescape.console.constant.MenuOption;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;

@Component
public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public MenuOption getMenuOption() {
        System.out.println("원하는 옵션을 선택해주세요");
        System.out.println("1. 예약 추가");
        System.out.println("2. 예약 취소");
        System.out.println("3. 예약 시간 추가");
        System.out.println("4. 예약 시간 삭제");
        System.out.println("5. 종료");
        return MenuOption.findMenuOption(scanner.nextLine().trim());
    }

    public Long getDeleteReservationId() {
        System.out.println("삭제하려하는 예약 번호를 입력해주세요");
        return Long.parseLong(scanner.nextLine().trim());
    }

    public Long getDeleteReservationTimeId() {
        System.out.println("삭제하려하는 시간 번호를 입력해주세요");
        return Long.parseLong(scanner.nextLine().trim());
    }

    public ReservationRequest getReservationAddRequest() {
        System.out.println("예약자 성함을 입력해주세요");
        String name = scanner.nextLine().trim();
        System.out.println("예약 날짜를 입력해주세요(0000-00-00 형식)");
        String date = scanner.nextLine().trim();
        System.out.println("예약 하려는 시간의 번호를 입력해주세요");
        Long timeId = Long.parseLong(scanner.nextLine().trim());
        return ReservationRequest.of(name, date, timeId);
    }

    public ReservationTimeRequest getReservationTimeAddRequest() {
        System.out.println("예약 추가 시간을 입력해주세요(00:00 형식)");
        String time = scanner.nextLine().trim();
        return ReservationTimeRequest.of(time);
    }
}
