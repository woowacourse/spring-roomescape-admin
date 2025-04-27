package roomescape.console.view;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import roomescape.domain.ReservationDateTimeFormatter;
import roomescape.presentation.dto.ReservationRequestDto;
import roomescape.presentation.dto.ReservationResponseDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;

public final class View {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern COMMAND_PATTERN = Pattern.compile("\\d+");

    private View() {
    }

    public static void printWelcomeMessage() {
        System.out.println("예약 시스템에 오신 것을 환영합니다!");
    }

    public static void printCommands() {
        System.out.println("""
                
                작업 목록
                1. 예약하기
                2. 예약 확인하기
                3. 예약 취소하기
                4. 예약 시간 추가하기
                5. 예약 시간 확인하기
                6. 예약 시간 삭제하기
                7. 종료하기""");
    }

    public static Command readCommand() {
        System.out.println("실행할 작업을 선택하세요(1~7):");
        String input = scanner.nextLine();
        if (!COMMAND_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("잘못된 입력입니다. 숫자만 입력하세요.");
        }
        int choice = Integer.parseInt(input);
        return Command.from(choice);
    }

    public static ReservationRequestDto readReservation(List<ReservationTimeResponseDto> availableTimes) {
        System.out.println("예약자명을 입력하세요:");
        String name = scanner.nextLine();
        System.out.println("예약 날짜를 입력하세요 (YYYY-MM-DD):");
        String date = scanner.nextLine();
        displayAvailableTimes(availableTimes);
        System.out.println("예약 시간 ID를 입력하세요:");
        Long timeId = scanner.nextLong();
        return new ReservationRequestDto(name, ReservationDateTimeFormatter.parseDate(date), timeId);
    }

    private static void displayAvailableTimes(List<ReservationTimeResponseDto> availableTimes) {
        System.out.println("예약 가능한 시간 목록:");
        availableTimes.stream()
                .map(time -> String.format("ID: %d, 시작 시간: %s", time.id(), time.startAt()))
                .forEach(System.out::println);
    }

    public static void printReservation(ReservationResponseDto reservationResponseDto) {
        System.out.print("\n예약이 완료되었습니다. - ");
        System.out.printf("예약자명: %s 예약 날짜: %s 예약 시간: %s%n", reservationResponseDto.name(),
                reservationResponseDto.date(), reservationResponseDto.time().startAt());
    }

    public static void printReservations(List<ReservationResponseDto> allReservations) {
        System.out.println("예약 목록:");
        allReservations.forEach(reservation -> {
            System.out.printf("ID: %d, 예약자명: %s, 예약 날짜: %s, 예약 시간: %s%n",
                    reservation.id(), reservation.name(), reservation.date(), reservation.time().startAt());
        });
    }

    public static Long readCancelReservationId() {
        System.out.println("취소할 예약의 ID를 입력하세요:");
        return scanner.nextLong();
    }

    public static void printReservationDeleteSuccess(Long id) {
        System.out.printf("예약 ID %d가 취소되었습니다.%n", id);
    }

    public static void printReservationTimes(List<ReservationTimeResponseDto> allReservationTimes) {
        System.out.println("예약 시간 목록:");
        allReservationTimes.forEach(reservationTime
                -> System.out.printf("ID: %d, 시작 시간: %s%n", reservationTime.id(), reservationTime.startAt()));
    }

    public static LocalTime readReservationTime() {
        System.out.println("예약 시간을 입력하세요 (HH:MM):");
        return ReservationDateTimeFormatter.parseTime(scanner.nextLine());
    }

    public static void printReservationTime(ReservationTimeResponseDto reservationTimeResponseDto) {
        System.out.print("\n예약 시간이 추가되었습니다 - ");
        System.out.printf("ID: %d, 예약 시간: %s%n", reservationTimeResponseDto.id(), reservationTimeResponseDto.startAt());
    }

    public static Long readDeleteReservationTimeId() {
        System.out.println("삭제할 예약 시간의 ID를 입력하세요:");
        return scanner.nextLong();
    }

    public static void printReservationTimeDeleteSuccess(Long id) {
        System.out.printf("예약 시간 ID %d가 삭제되었습니다.%n", id);
    }

    public static void printGoodbyeMessage() {
        System.out.println("예약 시스템을 종료합니다.");
    }

    public static void printErrorMessage(String message) {
        System.out.println("오류: " + message);
    }
}
