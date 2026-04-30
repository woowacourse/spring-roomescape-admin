package roomescape.util;

import roomescape.view.OutputView;

public class Retry {

    private Retry() {
    }

    public static <T> T untilSuccess(final SupplierWithEx<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    @FunctionalInterface
    public interface SupplierWithEx<T> {
        T get();
    }
}
