package roomescape.controller;

import java.time.Clock;
import java.util.function.Supplier;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.Command;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class ConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleController() {
        inputView = new InputView();
        outputView = new OutputView();
        MemoryReservationTimeRepository memoryReservationTimeRepository = new MemoryReservationTimeRepository();
        MemoryReservationRepository memoryReservationRepository = new MemoryReservationRepository();
        reservationService = new ReservationService(
                memoryReservationRepository,
                memoryReservationTimeRepository,
                Clock.systemDefaultZone()
        );
        reservationTimeService = new ReservationTimeService(memoryReservationTimeRepository);
    }


    public void run() {
        Command command = null;
        while (command != Command.QUIT) {
            command = retry(inputView::readCommand);
            processCommand(command);
        }
    }

    private void processCommand(Command command) {
        if (command == Command.TIME_GET) {
            outputView.printReservationTimes(reservationTimeService.findAllReservationTime());
            return;
        }
        if (command == Command.TIME_ADD) {
            retry(() -> {
                CreateReservationTimeDto createReservationTimeDto = inputView.readReservationTimeDto();
                reservationTimeService.createReservationTime(createReservationTimeDto);
            });
            outputView.printSuccessMessage();
            return;
        }
        if (command == Command.TIME_DELETE) {
            retry(() -> {
                Long id = inputView.readReservationTimeId();
                reservationTimeService.deleteReservationTime(id);
            });
            outputView.printSuccessMessage();
            return;
        }
        if (command == Command.RESERVATION_GET) {
            outputView.printReservations(reservationService.findAllReservations());
            return;
        }
        if (command == Command.RESERVATION_ADD) {
            retry(() -> {
                CreateReservationDto createReservationDto = inputView.readReservationDto();
                reservationService.createReservation(createReservationDto);
            });
            outputView.printSuccessMessage();
            return;
        }
        if (command == Command.RESERVATION_DELETE) {
            retry(() -> {
                Long id = inputView.readReservationId();
                reservationService.deleteReservation(id);
            });
            outputView.printSuccessMessage();
        }
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    private void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
            }
        }
    }
}
