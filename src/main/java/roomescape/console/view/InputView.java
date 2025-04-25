package roomescape.console.view;

import java.util.Scanner;
import org.springframework.stereotype.Component;
import roomescape.presentation.dto.CreateReservationDto;
import roomescape.presentation.dto.CreateReservationTimeDto;

@Component
public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public int selectAdminFunction() {
        System.out.println("이용하려는 기능을 선택해 주세요.");
        System.out.println("1. 예약 관리");
        System.out.println("2. 예약 시간 관리");
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public int selectReservationFunction() {
        System.out.println("예약 관리 기능입니다. 실행할 기능을 선택해 주세요.");
        System.out.println("1. 예약 추가");
        System.out.println("2. 예약 목록 조회");
        System.out.println("3. 예약 삭제");
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public CreateReservationTimeDto inputCreateReservationTime() {
        System.out.println("예약 시간을 생성합니다.");
        System.out.println("생성할 예약 시간을 입력해 주세요. (ex: 20:30) ");
        return new CreateReservationTimeDto(scanner.nextLine());
    }

    public Long inputDeleteReservationTimeId() {
        System.out.println("예약 시간을 삭제합니다.");
        System.out.println("삭제할 예약 시간의 id를 입력해 주세요. (ex: 1) ");
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    public int selectReservationTimeFunction() {
        System.out.println("예약 시간 관리 기능입니다. 실행할 기능을 선택해 주세요.");
        System.out.println("1. 예약 시간 추가");
        System.out.println("2. 예약 시간 목록 조회");
        System.out.println("3. 예약 시간 삭제");
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public CreateReservationDto inputCreateReservation() {
        System.out.println("예약을 생성합니다.");
        System.out.println("생성할 예약의 예약자 이름, 예약 날짜, 생성할 시간 id를 입력해 주세요.");
        System.out.println("(ex: 피글렛, 2025-04-24, 1)");
        String value = scanner.nextLine();

        String[] parameters = value.split(",");
        return new CreateReservationDto(
                parameters[0].trim(),
                parameters[1].trim(),
                Long.parseLong(parameters[2].trim())
        );
    }

    public Long inputDeleteReservationId() {
        System.out.println("예약을 삭제합니다.");
        System.out.println("삭제할 예약의 id를 입력해 주세요. (ex: 1) ");
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }
}
