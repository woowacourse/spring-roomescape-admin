package roomescape.consoleview;

import java.util.Scanner;

public class InputView {

    private static final InputView INSTANCE = new InputView();

    private final Scanner scanner;

    private InputView() {
        this.scanner = new Scanner(System.in);
    }

    public static InputView getInstance() {
        return INSTANCE;
    }

    public String readCommand() {
        System.out.println("명령어를 입력해 주세요. help를 입력하면 명령어 목록을 볼 수 있습니다.");
        return scanner.nextLine();
    }
}
