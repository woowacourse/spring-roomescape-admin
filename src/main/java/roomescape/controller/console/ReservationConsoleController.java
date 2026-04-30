package roomescape.controller.console;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;
import roomescape.servcie.ReservationService;
import roomescape.servcie.ReservationTimeService;

@Component
@Profile("console")
public class ReservationConsoleController implements CommandLineRunner {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationConsoleController(ReservationService reservationService,
                                        ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    private static void printInvalidMenu() {
        ConsoleView.printError("잘못된 선택입니다.");
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String menu = ConsoleView.readMenu();
            if (menu.equals("q")) break;

            try {
                handleMenu(menu);
            } catch (Exception e) {
                ConsoleView.printError("에러 발생: " + e.getMessage());
            }
        }
        ConsoleView.printMessage("프로그램을 종료합니다.");
        System.exit(0);
    }

    private void handleMenu(String menu) {
        switch (menu) {
            case "1" -> printAllReservations();
            case "2" -> reserve();
            case "3" -> cancelAllReservation();
            case "4" -> printAllReservationTimes();
            case "5" -> registerReservationTime();
            case "6" -> removeReservationTime();
            default -> printInvalidMenu();
        }
    }

    private void printAllReservations() {
        ConsoleView.printReservations(reservationService.getAllReservations());
    }

    private void reserve() {
        String name = ConsoleView.readInput("이름: ");
        String date = ConsoleView.readInput("날짜(YYYY-MM-DD): ");
        Long timeId = Long.parseLong(ConsoleView.readInput("시간 ID: "));

        reservationService.reserve(new ReservationRequest(name, LocalDate.parse(date), timeId));
        ConsoleView.printMessage("예약이 완료되었습니다.");
    }

    private void cancelAllReservation() {
        Long id = Long.parseLong(ConsoleView.readInput("삭제할 예약 ID: "));
        reservationService.cancelAllReservation(id);
        ConsoleView.printMessage("예약이 삭제되었습니다.");
    }

    private void printAllReservationTimes() {
        ConsoleView.printTimes(reservationTimeService.getAllReservationTimes());
    }

    private void registerReservationTime() {
        String startAt = ConsoleView.readInput("추가할 시간(HH:mm): ");
        reservationTimeService.register(new ReservationTimeRequest(LocalTime.parse(startAt)));
        ConsoleView.printMessage("시간이 등록되었습니다.");
    }

    private void removeReservationTime() {
        Long id = Long.parseLong(ConsoleView.readInput("삭제할 시간 ID: "));
        reservationTimeService.remove(id);
        ConsoleView.printMessage("시간이 삭제되었습니다.");
    }
}
