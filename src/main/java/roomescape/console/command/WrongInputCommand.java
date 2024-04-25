package roomescape.console.command;

import org.springframework.stereotype.Component;

@Component
public class WrongInputCommand implements ConsoleCommand {

    @Override
    public void conduct() {
        System.out.println("ERROR: 잘못된 명령입니다.");
    }
}
