package bg.softuni.clothing_store.model.enums;

public enum DeliveryType {

    SPEEDY ("Speedy"),
    ECONT ("Econt"),
    SAME_DAY ("Same day");

    private final String value;


    DeliveryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
