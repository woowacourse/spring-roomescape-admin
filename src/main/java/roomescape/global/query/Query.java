package roomescape.global.query;

public abstract class Query implements Assemblable {
    protected Query() {
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        assemble(builder);
        return builder.toString();
    }
}
