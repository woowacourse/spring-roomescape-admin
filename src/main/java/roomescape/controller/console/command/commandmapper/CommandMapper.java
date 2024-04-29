package roomescape.controller.console.command.commandmapper;

import roomescape.controller.console.command.Command;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public interface CommandMapper {
    Command mapTo(InputView inputView, OutputView outputView);
}
