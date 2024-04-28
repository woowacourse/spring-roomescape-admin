package roomescape.view;

import org.springframework.stereotype.Component;
import roomescape.annotation.ConsoleProperty;

import java.util.Scanner;

@ConsoleProperty
@Component
public class ConsoleInputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleInputView() {}

    public ReservationCommand inputCommand() {
        System.out.println(ReservationCommand.formatMessage() + "중 입력해주세요.");
        return ReservationCommand.from(SCANNER.nextInt());
    }

    public String inputName() {
        System.out.println("예약자 명을 입력해주세요");
        clear();
        return SCANNER.nextLine();
    }

    public String inputDate() {
        System.out.println("에약 날자를 입력해주세요.(EX: 2023-10-03)");
        return SCANNER.nextLine();
    }

    public long inputId() {
        System.out.println("예약 번호를 입력해주세요.");
        return SCANNER.nextLong();
    }


    public String inputTime() {
        System.out.println("예약 시간을 입력해주세요.(EX: 10:00)");
        clear();
        return SCANNER.nextLine();
    }

    public void clear() {
        SCANNER.nextLine();
    }


}
