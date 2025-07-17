package clothes.hsf302_group3_project.enums;

public enum Size {
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large");

    private final String displayName;

    Size(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}