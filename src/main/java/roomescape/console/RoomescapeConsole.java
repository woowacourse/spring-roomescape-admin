package roomescape.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationCreateCommand;
import roomescape.service.dto.ReservationTimeCreateCommand;

public class RoomescapeConsole {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;
    private final Scanner scanner = new Scanner(System.in);

    public RoomescapeConsole(
            ReservationService reservationService,
            ReservationTimeService reservationTimeService
    ) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        printHelp();
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            try {
                String[] parts = line.split("\\s+");
                String command = parts[0];

                switch (command) {
                    case "help" -> printHelp();
                    case "time-add" -> handleTimeAdd(parts);
                    case "time-list" -> handleTimeList();
                    case "time-delete" -> handleTimeDelete(parts);
                    case "reservation-add" -> handleReservationAdd(parts);
                    case "reservation-list" -> handleReservationList();
                    case "reservation-delete" -> handleReservationDelete(parts);
                    case "exit" -> {
                        System.out.println("종료합니다.");
                        return;
                    }
                    default -> System.out.println("알 수 없는 명령. 'help' 입력.");
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                사용 가능한 명령:
                  time-add <startAt>                       예: time-add 10:00
                  time-list                                시간 목록
                  time-delete <id>                         예: time-delete 1
                  reservation-add <name> <date> <timeId>   예: reservation-add 브라운 2023-08-05 1
                  reservation-list                         예약 목록
                  reservation-delete <id>                  예: reservation-delete 1
                  help                                     도움말
                  exit                                     종료
                """);
    }

    private void handleTimeAdd(String[] parts) {
        ReservationTimeCreateCommand command = new ReservationTimeCreateCommand(LocalTime.parse(parts[1]));
        ReservationTime saved = reservationTimeService.create(command);
        System.out.printf("등록됨: id=%d, startAt=%s%n", saved.getId(), saved.getStartAt());
    }

    private void handleTimeList() {
        List<ReservationTime> times = reservationTimeService.findAll();
        if (times.isEmpty()) {
            System.out.println("(없음)");
            return;
        }
        for (ReservationTime t : times) {
            System.out.printf("  %d: %s%n", t.getId(), t.getStartAt());
        }
    }

    private void handleTimeDelete(String[] parts) {
        reservationTimeService.delete(Long.parseLong(parts[1]));
        System.out.println("삭제됨.");
    }

    private void handleReservationAdd(String[] parts) {
        ReservationCreateCommand command = new ReservationCreateCommand(
                parts[1], LocalDate.parse(parts[2]), Long.parseLong(parts[3])
        );

        Reservation saved = reservationService.create(command);
        System.out.printf("등록됨: id=%d, name=%s, date=%s, time=%s%n",
                saved.getId(), saved.getName(), saved.getDate(), saved.getTime().getStartAt());
    }

    private void handleReservationList() {
        List<Reservation> reservations = reservationService.findAll();
        if (reservations.isEmpty()) {
            System.out.println("(없음)");
            return;
        }
        for (Reservation r : reservations) {
            System.out.printf("  %d: %s, %s, %s%n",
                    r.getId(), r.getName(), r.getDate(), r.getTime().getStartAt());
        }
    }

    private void handleReservationDelete(String[] parts) {
        reservationService.delete(Long.parseLong(parts[1]));
        System.out.println("삭제됨.");
    }
}
