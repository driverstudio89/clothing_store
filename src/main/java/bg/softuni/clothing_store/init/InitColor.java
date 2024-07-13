package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.ColorRepository;
import bg.softuni.clothing_store.model.Color;
import bg.softuni.clothing_store.model.enums.ColorName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitColor {

    private final ColorRepository colorRepository;

    public void run(String... args) throws Exception {
        if (colorRepository.count() > 0) {
            return;
        }
        for (ColorName colorName : ColorName.values()) {
            Color color = new Color(colorName);
            colorRepository.save(color);
        }
    }
}
