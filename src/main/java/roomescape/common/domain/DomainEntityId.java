package roomescape.common.domain;

public class DomainEntityId {

    private final Long id;

    private DomainEntityId(final Long value) {
        this.id = value;
    }

    public static DomainEntityId from(final Long id) {
        return new DomainEntityId(id);
    }

    public Long getValue() {
        if (isSaved()) {
            return id;
        }
        throw new IllegalStateException();
    }

    private boolean isSaved() {
        return id != null;
    }
}
