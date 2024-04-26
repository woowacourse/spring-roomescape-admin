package roomescape.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationTimeRequest;

public class ConsoleInputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationRequest inputReservation() {
        System.out.println("예약 정보를 입력해주세요. (형식 : 날짜, 예약자, 시간 ID)");
        System.out.println("ex) 2023-08-10, 브리, 1");

        List<String> reservation = List.of(input().split(DELIMITER));
        LocalDate date = LocalDate.parse(reservation.get(0).trim(), DATE_FORMATTER);
        String name = reservation.get(1).trim();
        Long timeId = Long.parseLong(reservation.get(2).trim());

        return new ReservationRequest(date, name, timeId);
    }

    public ReservationTimeRequest inputReservationTime() {
        System.out.println("예약 시간 정보를 입력해주세요. (형식 : 시간");
        System.out.println("ex) 09:50");

        String reservationTime = input();
        LocalTime startAt = LocalTime.parse(reservationTime.trim(), TIME_FORMATTER);

        return new ReservationTimeRequest(startAt);
    }

    private String input() {
        return SCANNER.nextLine();
    }
}
