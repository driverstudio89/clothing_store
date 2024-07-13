package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.CategoryRepository;
import bg.softuni.clothing_store.model.Category;
import bg.softuni.clothing_store.model.enums.CategoryType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitCategories {

    private final Map<CategoryType, String> categoriesDescription = Map.of(
            CategoryType.MEN, "clothing for men",
            CategoryType.WOMEN, "clothing for women",
            CategoryType.CHILDREN, "clothing for children"
    );

    private final CategoryRepository categoryRepository;

    public InitCategories(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void run(String... args) throws Exception {
        long count = this.categoryRepository.count();

        if (count > 0) {
            return;
        }

        for (CategoryType categoryType : categoriesDescription.keySet()) {
            Category category = new Category(categoryType, categoriesDescription.get(categoryType));

            this.categoryRepository.saveAndFlush(category);
        }

    }
}
