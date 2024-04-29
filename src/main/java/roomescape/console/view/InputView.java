package roomescape.console.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;
import roomescape.core.dto.ReservationRequest;
import roomescape.core.dto.ReservationTimeRequest;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public int readSelectMenu() {
        System.out.println(
                """
                \n[방탈출] 원하시는 항목을 선택해 주세요. (ex.1)
                1. 예약 추가
                2. 예약 삭제
                3. 예약 시간 추가
                4. 예약 시간 삭제
                5. 프로그램 종료
                """
                );
        String input = SCANNER.nextLine();
        return Integer.parseInt(input);
    }

    public ReservationRequest readReservationCreate() {
        System.out.println("<예약 추가>를 선택하셨습니다.");
        String name = readName();
        LocalDate date = readDate();
        Long timeId = readTimeId();
        return new ReservationRequest(name, date, timeId);
    }

    public Long readReservationDelete() {
        System.out.println("\n<예약 삭제>를 선택하셨습니다.");
        System.out.println("예약 현황을 참고하여 삭제할 ID를 선택해 주세요.");
        String input = SCANNER.nextLine();
        return Long.parseLong(input);
    }

    private String readName() {
        System.out.println("예약자 성함을 입력해 주세요.");
        return SCANNER.nextLine();
    }

    private LocalDate readDate() {
        System.out.println("예약자 방문 날짜를 입력해 주세요.");
        return LocalDate.parse(SCANNER.nextLine());
    }

    private Long readTimeId() {
        System.out.println("예약 시간 현황을 참고하여 예약할 시간 ID를 선택해 주세요.");
        return Long.parseLong(SCANNER.nextLine());
    }

    public ReservationTimeRequest readReservationTimeCreate() {
        System.out.println("\n<예약 시간 추가>를 선택하셨습니다.");
        System.out.println("추가할 예약 시간을 입력해 주세요.");
        LocalTime localTime = LocalTime.parse(SCANNER.nextLine());
        return new ReservationTimeRequest(localTime);
    }

    public Long readReservationTimeDelete() {
        System.out.println("\n<예약 시간 삭제>를 선택하셨습니다.");
        System.out.println("예약 시간 현황을 참고하여 삭제할 ID를 선택해 주세요.");
        String input = SCANNER.nextLine();
        return Long.parseLong(input);
    }
}
