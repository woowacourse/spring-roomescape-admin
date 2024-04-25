package roomescape.consoleview.command;

public enum CommandType {
    HELP("help"),
    SHOW("show"),
    CREATE("create"),
    DELETE("delete"),
    ;

    private final String prefix;

    CommandType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
