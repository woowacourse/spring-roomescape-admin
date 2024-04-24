package roomescape.controller.console;

import java.util.EnumMap;
import java.util.Map;
import roomescape.view.InputView;
import roomescape.view.command.ManagementCommand;

public abstract class ManagementController {

    protected final Map<ManagementCommand, CommandExecutor> commandExecutors;

    protected ManagementController() {
        this.commandExecutors = new EnumMap<>(ManagementCommand.class);
        prepareCommandExecutors();
    }

    public void main() {
        ManagementCommand command;
        do {
            showAllResults();
            command = InputView.getManagementCommand();
            commandExecutors.get(command).execute();
        } while (!command.isBack());
    }

    protected abstract void showAllResults();

    public abstract void create();

    public abstract void delete();

    private void prepareCommandExecutors() {
        commandExecutors.putAll(Map.of(
                ManagementCommand.CREATE, this::create,
                ManagementCommand.DELETE, this::delete,
                ManagementCommand.BACK, () -> {}
        ));
    }
}
