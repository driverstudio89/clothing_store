package bg.softuni.clothing_store.model.enums;

public enum SubCategoryType {
    SHIRT("nav_bar_sub_category_shirt"),
    HOODIE("nav_bar_sub_category_hoodie"),
    SHORTS("nav_bar_sub_category_pants"),
    JEANS("nav_bar_sub_category_jeans"),
    SUIT("nav_bar_sub_category_suits"),
    SPORT("nav_bar_sub_category_sports"),
    TOP("nav_bar_sub_category_top"),
    DRESS("nav_bar_sub_category_dress"),
    SKIRT("nav_bar_sub_category_skirt"),
    BLOUSE("nav_bar_sub_category_blouse"),
    JACKET("nav_bar_sub_category_jackets"),
    ;

    private final String value;


    SubCategoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
