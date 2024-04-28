package roomescape.console;

import org.springframework.boot.CommandLineRunner;

public class RoomEscapeConsoleRunner implements CommandLineRunner {

    private static final String MEMU = """
                                
                필요한 메뉴 번호를 입력해주세요.
                1. 예약 가능 시간 확인
                2. 예약 가능 시간 추가
                3. 예약 가능 시간 삭제
                4. 예약 확인
                5. 예약 추가
                6. 예약 삭제
                """;

    private final ConsoleCommandMatcher consoleCommandMatcher;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public RoomEscapeConsoleRunner(ConsoleCommandMatcher consoleCommandMatcher, ConsoleInputView consoleInputView,
                                   ConsoleOutputView consoleOutputView) {
        this.consoleCommandMatcher = consoleCommandMatcher;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    @Override
    public void run(String... args) {
        while (true) {
            consoleOutputView.printMessage(MEMU);
            String commandInput = consoleInputView.getInput();
            consoleCommandMatcher.findConsoleCommand(commandInput).conduct();
        }
    }
}
