package roomescape.console.request;

import java.util.Arrays;
import java.util.List;

public class ConsoleRequest {
    private final String method;
    private final String command;
    private final List<String> contents;

    public ConsoleRequest(String[] inputs) {
        this(inputs[0], inputs[1], List.of(Arrays.copyOfRange(inputs, 2, inputs.length)));
    }

    private ConsoleRequest(String method, String command, List<String> contents) {
        this.method = method;
        this.command = command;
        this.contents = contents;
    }

    public String getMethod() {
        return method;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getContents() {
        return contents;
    }

    public boolean isEnded() {
        return "end".equals(method);
    }
}
