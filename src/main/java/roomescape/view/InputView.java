package roomescape.view;

import roomescape.domain.reservation.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public int readOptions() {
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식의 옵션입니다.");
        }
    }

    public LocalTime readTime() {
        try {
            System.out.println("[INFO] 원하는 예약 시간을 입력해주세요 ex) 19:00:00");
            System.out.println("[INFO] 방탈출 예약 가능 시간은" + ReservationTime.startTime() +
                    "부터 " + ReservationTime.endTime() + "까지입니다.");
            return LocalTime.parse(SCANNER.nextLine());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식의 시간입니다.");
        }
    }

    public LocalDate readDate() {
        try {
            System.out.println("[INFO] 원하는 예약 시간을 입력해주세요 ex) 19:00:00");
            System.out.println("[INFO] 방탈출 예약 가능 시간은" + ReservationTime.startTime() +
                    "부터 " + ReservationTime.endTime() + "까지입니다.");
            return LocalDate.parse(SCANNER.nextLine());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식의 시간입니다.");
        }
    }

    public Long readId() {
        try {
            System.out.println("[INFO] 해당 id를 입력해주세요 ex) 1");
            return Long.valueOf(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식의 id입니다.");
        }
    }

    public String readName() {
        System.out.println("[INFO] 예약자 성명을 입력해주세요 ex) sudal");
        return SCANNER.nextLine();
    }
}
