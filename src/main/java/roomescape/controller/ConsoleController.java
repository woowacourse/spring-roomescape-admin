package roomescape.controller;

import java.time.Clock;
import java.util.function.Supplier;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
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
        ReservationTimeRepository memoryReservationTimeRepository = new MemoryReservationTimeRepository();
        ReservationRepository memoryReservationRepository = new MemoryReservationRepository();
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
        switch (command) {
            case TIME_GET -> getAllTimes();
            case TIME_ADD -> addNewTime();
            case TIME_DELETE -> deleteTime();
            case RESERVATION_GET -> getAllReservations();
            case RESERVATION_ADD -> addNewReservation();
            case RESERVATION_DELETE -> deleteReservation();
        }
    }

    private void deleteReservation() {
        retry(() -> {
            Long id = inputView.readReservationId();
            reservationService.deleteReservation(id);
        });
        outputView.printSuccessMessage();
    }

    private void addNewReservation() {
        retry(() -> {
            ReservationRequest reservationRequest = inputView.readReservationDto();
            reservationService.createReservation(reservationRequest);
        });
        outputView.printSuccessMessage();
    }

    private void getAllReservations() {
        outputView.printReservations(reservationService.findAllReservations());
    }

    private void getAllTimes() {
        outputView.printReservationTimes(reservationTimeService.findAllReservationTime());
    }

    private void deleteTime() {
        retry(() -> {
            Long id = inputView.readReservationTimeId();
            reservationTimeService.deleteReservationTime(id);
        });
        outputView.printSuccessMessage();
    }

    private void addNewTime() {
        retry(() -> {
            ReservationTimeRequest reservationTimeRequest = inputView.readReservationTimeDto();
            reservationTimeService.createReservationTime(reservationTimeRequest);
        });
        outputView.printSuccessMessage();
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
