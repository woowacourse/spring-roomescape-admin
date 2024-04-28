package roomescape.console.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import roomescape.domain.Name;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Command readCommand() {
        System.out.println("원하는 기능의 번호를 입력하세요. ex - 4");
        String input = scanner.nextLine();
        return Command.from(input);
    }

    public ReservationRequest readReservationRequest() {
        System.out.println("생성하려는 예약 정보를 \"이름,날짜(yyyy-mm-dd),시간id\" 형태로 입력하세요.");
        System.out.println("ex - 엘라,2024-06-01,1");
        String input = scanner.nextLine();
        List<String> splitRequest = List.of(input.split(","));
        validateReservationRequest(splitRequest);
        return new ReservationRequest(
                splitRequest.get(0),
                splitRequest.get(1),
                Long.parseLong(splitRequest.get(2))
        );
    }

    public Long readDeleteReservationId() {
        System.out.println("삭제하려는 예약의 id를 입력하세요. ex - 1");
        String input = scanner.nextLine();
        return Long.parseLong(input);
    }

    public ReservationTimeRequest readReservationTimeRequest() {
        System.out.println("생성하려는 예약 시간 정보를 \"HH:mm\" 형태로 입력하세요.");
        System.out.println("ex- 10:00");
        String input = scanner.nextLine();
        validateReservationTimeRequest(input);
        return new ReservationTimeRequest(input);
    }

    public Long readDeleteReservationTimeId() {
        System.out.println("삭제하려는 예약 시간의 id를 입력하세요. ex - 1");
        String input = scanner.nextLine();
        return Long.parseLong(input);
    }

    private void validateReservationRequest(List<String> splitRequest) {
        if (splitRequest.size() != 3) {
            throw new IllegalArgumentException("올바르지 않은 예약 생성 요청입니다. 입력 형태를 확인하세요.");
        }
        new Name(splitRequest.get(0));
        try {
            LocalDate.parse(splitRequest.get(1));
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("올바르지 않은 예약 날짜입니다. 날짜 형태를 확인하세요");
        }
    }

    private void validateReservationTimeRequest(String input) {
        try {
            LocalTime.parse(input);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("올바르지 않은 예약 시간입니다. 시간 형태를 확인하세요");
        }
    }
}
