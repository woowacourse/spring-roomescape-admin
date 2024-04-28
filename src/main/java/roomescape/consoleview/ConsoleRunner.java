package roomescape.consoleview;

import static roomescape.consoleview.command.CommandType.CREATE;
import static roomescape.consoleview.command.CommandType.DELETE;
import static roomescape.consoleview.command.CommandType.HELP;
import static roomescape.consoleview.command.CommandType.SHOW;

import roomescape.consoleview.command.Command;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.controller.dto.SaveReservationRequest;
import roomescape.controller.dto.SaveReservationTimeRequest;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleRunner {

    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();
    private static final ReservationController RESERVATION_CONTROLLER = new ReservationController(
        new ReservationService(MemoryReservationRepository.getInstance(),
            MemoryReservationTimeRepository.getInstance()));
    private static final ReservationTimeController RESERVATION_TIME_CONTROLLER = new ReservationTimeController(
        new ReservationTimeService(MemoryReservationTimeRepository.getInstance()));

    public static void main(String[] args) {
        while (true) {
            try {
                Command command = Command.from(INPUT_VIEW.readCommand());
                execute(command);
            } catch (RuntimeException exception) {
                OUTPUT_VIEW.printError(exception.getMessage());
            }
        }
    }

    private static void execute(Command command) {
        if (command.type() == HELP) {
            OUTPUT_VIEW.printHelp();
        }
        if (command.type() == SHOW) {
            if (command.argumentOf(0).equals("reservation")) {
                OUTPUT_VIEW.printReservations(RESERVATION_CONTROLLER.findAll());
            }
            if (command.argumentOf(0).equals("time")) {
                OUTPUT_VIEW.printTimes(RESERVATION_TIME_CONTROLLER.findAll());
            }
        }
        if (command.type() == CREATE) {
            if (command.argumentOf(0).equals("reservation")) {
                String name = command.argumentOf(1);
                String date = command.argumentOf(2);
                Long timeId = Long.parseLong(command.argumentOf(3));
                RESERVATION_CONTROLLER.save(new SaveReservationRequest(date, name, timeId));
            }
            if (command.argumentOf(0).equals("time")) {
                String startAt = command.argumentOf(1);
                RESERVATION_TIME_CONTROLLER.save(new SaveReservationTimeRequest(startAt));
            }
        }
        if (command.type() == DELETE) {
            if (command.argumentOf(0).equals("reservation")) {
                long reservationId = Long.parseLong(command.argumentOf(1));
                RESERVATION_CONTROLLER.delete(reservationId);
            }
            if (command.argumentOf(0).equals("time")) {
                long timeId = Long.parseLong(command.argumentOf(1));
                RESERVATION_TIME_CONTROLLER.delete(timeId);
            }
        }
    }
}
