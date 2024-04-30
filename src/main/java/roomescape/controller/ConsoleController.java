package roomescape.controller;

import roomescape.console.view.ConsoleInputView;
import roomescape.console.view.ConsoleOutputView;
import roomescape.dto.request.ReservationsRequest;
import roomescape.dto.request.TimesRequest;
import roomescape.dto.response.ReservationsResponse;
import roomescape.dto.response.TimesResponse;
import roomescape.service.RoomescapeService;

import java.util.List;

public class ConsoleController {

    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;
    private final RoomescapeService roomescapeService;

    public ConsoleController(ConsoleInputView consoleInputView, ConsoleOutputView consoleOutputView, RoomescapeService roomescapeService) {
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
        this.roomescapeService = roomescapeService;
    }

    public void run() {
        try {
            proceed();
        } catch (IllegalArgumentException e) {
            consoleOutputView.printException(e);
        }
    }

    private void proceed() {
        while (true) {
            String menu = consoleInputView.readMenu();
            handleMyMenu(menu);
            if (menu.equals("exit")) {
                break;
            }
        }
    }

    private void handleMyMenu(String menu) {
        if (menu.equals("1")) {
            handleTimeAdd();
            return;
        }
        if (menu.equals("2")) {
            handleReservationAdd();
            return;
        }
        if (menu.equals("3")) {
            handleCheckTime();
            return;
        }
        if (menu.equals("4")) {
            handleCheckReservation();
            return;
        }
        if (menu.equals("5")) {
            handleDeleteTime();
            return;
        }
        if (menu.equals("6")) {
            handleDeleteReservation();
            return;
        }
    }

    private void handleTimeAdd() {
        String startAt = consoleInputView.readTime();
        TimesRequest timesRequest = new TimesRequest(startAt);
        TimesResponse timesResponse = roomescapeService.addTime(timesRequest);
        consoleOutputView.printTime(timesResponse);
    }

    private void handleCheckTime() {
        List<TimesResponse> allTimes = roomescapeService.findAllTimes();
        consoleOutputView.printTimes(allTimes);
    }

    private void handleDeleteTime() {
        Long id = consoleInputView.readRemoveTimeId();
        boolean isDeleted = roomescapeService.removeTime(id);
        if (isDeleted) {
            consoleOutputView.printDeleteSuccess();
            return;
        }
        consoleOutputView.printDeleteFail();
    }

    private void handleReservationAdd() {
        String name = consoleInputView.readName();
        String date = consoleInputView.readDate();
        Long timeId = consoleInputView.readTimeId();
        ReservationsRequest request = new ReservationsRequest(name, date, timeId);
        ReservationsResponse reservation = roomescapeService.addReservation(request);
        consoleOutputView.printAddedReservation(reservation);
    }

    private void handleCheckReservation() {
        List<ReservationsResponse> allReservations = roomescapeService.finaAllReservations();
        consoleOutputView.printReservations(allReservations);
    }

    private void handleDeleteReservation() {
        Long id = consoleInputView.readRemoveReservationId();
        boolean isDeleted = roomescapeService.removeReservation(id);
        if (isDeleted) {
            consoleOutputView.printDeleteSuccess();
            return;
        }
        consoleOutputView.printDeleteFail();
    }
}
