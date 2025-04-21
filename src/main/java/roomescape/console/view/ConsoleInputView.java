package roomescape.console.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;

public class ConsoleInputView {
    private final Scanner scanner = new Scanner(System.in);

    public boolean readContinue() {
        System.out.print("계속 입력하시겠습니까? (y/n): ");
        String input = scanner.nextLine();
        return input.equals("y");
    }

    public ReservationTimeCreateRequest readTime() {
        System.out.print("시간을 입력해주십시오 (HH:mm): ");
        String input = scanner.nextLine();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(input, timeFormatter);
        return new ReservationTimeCreateRequest(time);
    }

    public ReservationCreateRequest readCreateReservation() {
        System.out.println("이름을 입력해주세요.");
        String name = scanner.nextLine();

        System.out.println("날짜를 입력해주세요. (yyyy-MM-dd)");
        String inputDate = scanner.nextLine();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, dateFormatter);

        System.out.println("원하는 시간의 ID를 입력해주세요.");
        String inputTimeId = scanner.nextLine();
        long timeId = Long.parseLong(inputTimeId);

        return new ReservationCreateRequest(name, date, timeId);
    }
}
