package roomescape.controller;

import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.dto.request.ReservationsRequest;
import roomescape.dto.request.TimesRequest;
import roomescape.dto.response.ReservationsResponse;
import roomescape.dto.response.TimesResponse;
import roomescape.service.RoomescapeService;

import java.util.List;

public class ConsoleMainController {

    private final InputView inputView;
    private final OutputView outputView;
    private final RoomescapeService roomescapeService;

    public ConsoleMainController(InputView inputView, OutputView outputView, RoomescapeService roomescapeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.roomescapeService = roomescapeService;
    }

    public void run() {
        while (true) {
            String menu = inputView.readMenu();
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
        String startAt = inputView.readTime();
        TimesRequest timesRequest = new TimesRequest(startAt);
        TimesResponse timesResponse = roomescapeService.addTime(timesRequest);
        outputView.printTime(timesResponse);
    }

    private void handleCheckTime() {
        List<TimesResponse> allTimes = roomescapeService.findAllTimes();
        outputView.printTimes(allTimes);
    }

    private void handleDeleteTime() {
        Long id = inputView.readRemoveTimeId();
        int removedCount = roomescapeService.removeTime(id);
        outputView.printDelete(removedCount);
    }

    private void handleReservationAdd() {
        String name = inputView.readName();
        String date = inputView.readDate();
        Long timeId = inputView.readTimeId();
        ReservationsRequest request = new ReservationsRequest(name, date, timeId);
        ReservationsResponse reservation = roomescapeService.addReservation(request);
        outputView.printAddedReservation(reservation);
    }

    private void handleCheckReservation() {
        List<ReservationsResponse> allReservations = roomescapeService.finaAllReservations();
        outputView.printReservations(allReservations);
    }

    private void handleDeleteReservation() {
        Long id = inputView.readRemoveReservationId();
        int removedCount = roomescapeService.removeReservation(id);
        outputView.printDelete(removedCount);
    }
}
