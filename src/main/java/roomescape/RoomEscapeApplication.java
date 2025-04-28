package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.reservation.controller.CommandDispatcher;
import roomescape.reservation.controller.ConsoleReservationController;
import roomescape.reservation.repository.MemoryReservationRepository;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.view.ReservationInputView;
import roomescape.reservation.view.ReservationOutputView;
import roomescape.reservationTime.controller.ConsoleReservationTimeController;
import roomescape.reservationTime.repository.MemoryReservationTimeRepository;
import roomescape.reservationTime.repository.ReservationTimeRepository;
import roomescape.reservationTime.service.ReservationTimeService;
import roomescape.reservationTime.view.ReservationTimeInputView;
import roomescape.reservationTime.view.ReservationTimeOutputView;

@SpringBootApplication
public class RoomEscapeApplication {

    public static void main(String[] args) {
        System.out.println("실행 방식을 선택하세요:");
        System.out.println("1. 콘솔");
        System.out.println("2. 웹");
        System.out.println("입력 예시는 다음과 같습니다.");
        System.out.println("ex) 1");
        System.out.print("> ");
        String mode = new java.util.Scanner(System.in).nextLine();

        if ("1".equals(mode)) {
            runConsoleMode();
        } else if ("2".equals(mode)) {
            SpringApplication.run(RoomEscapeApplication.class, args);
        } else {
            System.out.println("잘못된 입력입니다. 프로그램을 종료합니다.");
        }
    }

    private static void runConsoleMode() {
        ReservationInputView reservationInputView = new ReservationInputView();
        ReservationOutputView reservationOutputView = new ReservationOutputView();

        ReservationTimeInputView reservationTimeInputView = new ReservationTimeInputView();
        ReservationTimeOutputView reservationTimeOutputView = new ReservationTimeOutputView();

        ReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationTimeRepository reservationTimeRepository = new MemoryReservationTimeRepository();

        ReservationService reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

        ConsoleReservationController reservationController = new ConsoleReservationController(reservationInputView, reservationOutputView, reservationTimeOutputView, reservationService, reservationTimeService);
        ConsoleReservationTimeController reservationTimeController = new ConsoleReservationTimeController(reservationTimeInputView, reservationTimeOutputView, reservationTimeService);

        CommandDispatcher dispatcher = new CommandDispatcher(reservationController, reservationTimeController);

        while (true) {
            String command = reservationInputView.readCommandInput();
            dispatcher.dispatch(command);
        }
    }
}
