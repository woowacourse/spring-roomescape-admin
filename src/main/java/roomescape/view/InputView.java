package roomescape.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.CreateReservationTimeDto;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public Command readCommand() {
        String message = """
                원하는 기능을 선택하시오.
                1. 시간 추가
                2. 시간 조회
                3. 시간 삭제
                4. 예약 추가
                5. 예약 조회
                6. 예약 삭제
                7. 종료
                """;
        System.out.print(message);
        String input = scanner.nextLine();
        return Command.find(input);
    }

    public CreateReservationTimeDto readReservationTimeDto() {
        System.out.println("예약 시간을 입력하시오. ex) 10:00");
        String input = scanner.nextLine();
        LocalTime startAt = LocalTime.parse(input);
        return new CreateReservationTimeDto(startAt);
    }

    public CreateReservationDto readReservationDto() {
        System.out.println("이름을 입력하시오.");
        String name = scanner.nextLine();
        System.out.println("날짜를 입력하시오. ex) 2025-01-01");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("예약시간 ID를 입력하시오.");
        Long timeId = Long.parseLong(scanner.nextLine());
        return new CreateReservationDto(name, date, timeId);
    }

    public Long readReservationTimeId() {
        System.out.println("삭제할 시간 ID를 입력하시오.");
        return Long.parseLong(scanner.nextLine());
    }

    public Long readReservationId() {
        System.out.println("삭제할 예약 ID를 입력하시오.");
        return Long.parseLong(scanner.nextLine());
    }
}
