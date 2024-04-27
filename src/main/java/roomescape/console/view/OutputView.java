package roomescape.console.view;

public class OutputView {
    public void printResponse(ConsoleResponse dispatch) {
        System.out.println(dispatch.getContent());
    }

    public void printErrorMessage(RuntimeException e) {
        System.out.println(e.getMessage());
    }
}
