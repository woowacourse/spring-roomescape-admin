package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoomEscapeConsoleRunner implements CommandLineRunner {

    private final ConsoleCommandMatcher consoleCommandMatcher;
    private final ConsoleInputScanner consoleInputScanner;

    public RoomEscapeConsoleRunner(ConsoleCommandMatcher consoleCommandMatcher, ConsoleInputScanner consoleInputScanner) {
        this.consoleCommandMatcher = consoleCommandMatcher;
        this.consoleInputScanner = consoleInputScanner;
    }

    @Override
    public void run(String... args) {
        while (true) {
            printMenuMessage();
            String s = consoleInputScanner.getInput();
            consoleCommandMatcher.findConsoleCommand(s).conduct();
        }
    }

    public void printMenuMessage() {
        System.out.println("""
                                
                필요한 메뉴 번호를 입력해주세요.
                1. 예약 가능 시간 확인
                2. 예약 가능 시간 추가
                3. 예약 가능 시간 삭제
                4. 예약 확인
                5. 예약 추가
                6. 예약 삭제
                """);
    }
}
