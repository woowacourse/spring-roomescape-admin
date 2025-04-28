package roomescape.reservation.controller;

import roomescape.reservationTime.controller.ConsoleReservationTimeController;

import java.util.Map;

public class CommandDispatcher {

    private final Map<String, Runnable> commandMap;

    public CommandDispatcher(ConsoleReservationController reservationController, ConsoleReservationTimeController reservationTimeController) {
        commandMap = Map.of(
                "1", reservationController::readAllReservation,
                "2", reservationController::addReservation,
                "3", reservationController::deleteReservation,
                "4", reservationTimeController::readAllReservationTimes,
                "5", reservationTimeController::addReservationTime,
                "6", reservationTimeController::deleteReservationTime,
                "Q", () -> System.exit(0)
        );
    }

    public void dispatch(String command) {
        commandMap.getOrDefault(command, () -> System.out.println("잘못된 명령입니다."))
                .run();
    }
}
