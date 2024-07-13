package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.SubCategoryRepository;
import bg.softuni.clothing_store.model.SubCategory;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitSubCategories {

    private final SubCategoryRepository subCategoryRepository;

    private final Map<SubCategoryType, String> subCategoryDescription = Map.ofEntries(
            Map.entry(SubCategoryType.SHIRT, "A shirt is a cloth garment for the upper body from the neck to the waist"),
            Map.entry(SubCategoryType.HOODIE, "A hoodie is a type of sweatshirt with a hood that partially or fully covers the wearer's head or face"),
            Map.entry(SubCategoryType.SHORTS, "Shorts are a type of clothing worn on the lower part of the body, covering the legs from the waist to the thighs or knees."),
            Map.entry(SubCategoryType.JEANS, "Jeans are a type of pants traditionally made from denim - a kind of cotton fabric. The word most commonly refers to denim blue jeans."),
            Map.entry(SubCategoryType.SUIT, "A set of outer clothes made of the same fabric and designed to be worn together"),
            Map.entry(SubCategoryType.SPORT, "Sportswear is athletic clothing, including footwear, worn for sports activity or physical exercise."),
            Map.entry(SubCategoryType.TOP, "A top is an item of clothing that covers at least the chest, but which usually covers most of the upper human body between the neck and the waistline"),
            Map.entry(SubCategoryType.DRESS, "A dress is a garment traditionally worn by women or girls consisting of a skirt with an attached bodice"),
            Map.entry(SubCategoryType.SKIRT, "A piece of women's clothing that hangs from the waist and does not have material between the legs, or the part of a dress below the waist"),
            Map.entry(SubCategoryType.BLOUSE, "A blouse is a loose-fitting upper garment that may be worn by workmen, artists, women, and children. It is typically gathered at the waist or hips"),
            Map.entry(SubCategoryType.JACKET, "an outer garment extending either to the waist or the hips, typically having sleeves and a fastening down the front.")
    );

    public InitSubCategories(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public void run(String... args) throws Exception {
        long count = this.subCategoryRepository.count();

        if (count > 0) {
            return;
        }

        for (SubCategoryType subCategoryType : subCategoryDescription.keySet()) {
            SubCategory subCategory = new SubCategory(subCategoryType, subCategoryDescription.get(subCategoryType));

            this.subCategoryRepository.saveAndFlush(subCategory);
        }

    }
}
