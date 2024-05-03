package roomescape.view.console;

import java.util.Scanner;

public class ConsoleInputView {

    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine();
    }
}
