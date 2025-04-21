package roomescape.common.domain;

public class DomainEntityId {

    private final Long value;

    private DomainEntityId(final Long value) {
        this.value = value;
    }

    public static DomainEntityId from(final Long id) {
        return new DomainEntityId(id);
    }

    public static DomainEntityId notAssigned() {
        return new DomainEntityId(null);
    }

    public Long getValue() {
        if (isSaved()) {
            return value;
        }
        throw new IllegalStateException();
    }

    private boolean isSaved() {
        return value != null;
    }
}
