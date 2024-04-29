package roomescape.console;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
public class ConsoleOutputView {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printErrorMessage(String message) {
        printMessage("ERROR: " + message);
    }

    public void printResult(ResponseEntity<?> responseEntity) {
        System.out.println(responseEntity);
    }

    public void printCollection(Collection<?> collection) {
        System.out.println(collection);
    }
}
