package roomescape.console.controller;

import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.dto.request.TimesRequest;
import roomescape.dto.response.TimesResponse;
import roomescape.service.RoomescapeService;

import java.util.List;

public class MainController {
    private final InputView inputView;
    private final OutputView outputView;
    private final RoomescapeService roomescapeService;

    public MainController(InputView inputView, OutputView outputView, RoomescapeService roomescapeService) {
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
        if (menu.equals("3")) {
            handleDeleteTime();
            return;
        }
        if (menu.equals("5")) {
            handleCheckTime();
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
        int count = roomescapeService.removeTime(id);
        outputView.printDelete(count);
    }
}
