package roomescape.view;

import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        System.out.println("""
                
                1. 시간 저장
                2. 시간 삭제
                3. 예약 저장
                4. 예약 삭제
                5. 종료
                원하는 동작의 번호를 입력해주세요. 예 - 1
                """);
        return scanner.nextLine();
    }

    public ReservationTimeRequestDto readTimeToAdd() {
        System.out.println("\n추가를 원하는 시간을 입력해주세요. 형식은 [시]:[분]으로 통일해주세요. 예 - 10:30");
        String input = scanner.nextLine();
         LocalTime timeValue = LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm"));
        return new ReservationTimeRequestDto(timeValue);
    }

    public ReservationRequestDto readReservationToAdd() {
        System.out.println("\n추가를 원하는 예약을 입력해주세요. 형식은 [예약자],[년-월-일],[시간번호]으로 통일해주세요. 예 - 에버,2024-04-24,1");
        String input = scanner.nextLine();
        String[] inputs = input.split(",");
        String name = inputs[0];
        LocalDate date = LocalDate.parse(inputs[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        long timeId = Long.parseLong(inputs[2]);
        return new ReservationRequestDto(name, date, timeId);
    }

    public long readTimeToDelete() {
        System.out.println("\n삭제를 원하는 시간의 id를 입력해주세요. 예 - 1");
        return Long.parseLong(scanner.nextLine());
    }

    public long readReservationToDelete() {
        System.out.println("\n삭제를 원하는 예약의 id를 입력해주세요. 예 - 1");
        return Long.parseLong(scanner.nextLine());
    }

    public void printAllTimes(List<ReservationTimeResponseDto> times) {
        System.out.println("\n===시간 목록===");
        if (times.isEmpty()) {
            System.out.println("존재하지 않습니다. 시간을 생성해주세요.");
            return;
        }
        for (ReservationTimeResponseDto time : times) {
            System.out.println(time.getId() + " | " + time.getStartAt());
        }
    }

    public void printAllReservations(List<ReservationResponseDto> reservations) {
        System.out.println("\n===예약 목록===");
        if (reservations.isEmpty()) {
            System.out.println("존재하지 않습니다. 예약을 생성해주세요.");
            return;
        }
        for (ReservationResponseDto reservation : reservations) {
            System.out.println(reservation.getId() + " | " + reservation.getName() + " | " + reservation.getDate() + " | " + reservation.getTime().getStartAt());
        }
    }
}
