package roomescape.console.controller;

import roomescape.console.view.InputView;
import roomescape.console.view.Menu;
import roomescape.console.view.OutputView;

public class RoomescapeController {

    private InputView inputView;
    private OutputView outputView;

    public RoomescapeController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        while (true) {
            Menu menu = inputView.inputMenu();
            if (menu == Menu.RESERVATION) {
            }
            if (menu == Menu.RESERVATION_TIME) {

            }
            if (menu == Menu.END) {
                break;
            }
        }
    }
}
