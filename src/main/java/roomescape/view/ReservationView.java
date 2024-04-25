package roomescape.view;

import java.time.LocalDate;
import java.util.Scanner;

public class ReservationView {
    private final Scanner scanner = new Scanner(System.in);

    public String readName() {
        System.out.println("[INFO] 방탈출 예약을 진행합니다. 예약자 이름을 입력해주세요.");
        return scanner.nextLine();
    }

    public LocalDate readDate() {
        System.out.println("[INFO] 예약할 날짜를 선택해주세요. (ex. 2023-01-01)");
        return LocalDate.parse(scanner.nextLine());
    }

    public void printSuccessfullyAdded() {
        System.out.println("[INFO] 예약이 추가되었습니다.");
    }
}
