package bg.softuni.clothing_store.model.enums;

public enum SubCategoryType {
    SHIRT("Shirt"),
    HOODIE("Hoodie"),
    SHORTS("Shorts"),
    JEANS("Jeans"),
    SUIT("Suit"),
    SPORT("Sport"),
    TOP("Top"),
    DRESS("Dress"),
    SKIRT("Skirt"),
    BLOUSE("Blouse"),
    JACKET("Jacket"),
    ;

    private final String value;


    SubCategoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
