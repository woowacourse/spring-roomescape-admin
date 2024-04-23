package roomescape.console.view;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public ConsoleCommand readCommand() {
        System.out.println("""
                원하는 기능을 형식에 맞게 입력하세요.
                1. 예약 조회 - ex) GET/reservations
                2. 예약 추가 - ex) POST/reservations {name},{date},{timeId}
                3. 예약 삭제 ID - ex) DELETE/reservation {id}
                4. 예약 시간 조회 - ex) GET/times
                5. 예약 시간 추가 - ex) POST/times {startAt}
                6. 예약 시간 삭제 - ex) DELETE/times {id}
                """);

        String input = SCANNER.nextLine();

        return ConsoleCommand.from(input);
    }
}
