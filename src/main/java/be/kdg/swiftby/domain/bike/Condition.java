package be.kdg.swiftby.domain.bike;

import lombok.Getter;

@Getter
public enum Condition {
    VERY_BAD("--"),
    BAD("-"),
    NEUTRAL(" "),
    GOOD("+"),
    VERY_GOOD("++"),
    NOT_APPLICABLE("nvt");

    private final String label;

    Condition(String label) {
        this.label = label;
    }

}
