package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.SizeRepository;
import bg.softuni.clothing_store.model.Size;
import bg.softuni.clothing_store.model.enums.SizeName;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitSize  {

    private final SizeRepository sizeRepository;

    public void run(String... args) throws Exception {
        if (sizeRepository.count() > 0) {
            return;
        }

        for (SizeName sizeName : SizeName.values()) {
            Size size = new Size(sizeName);
            sizeRepository.save(size);
        }

    }
}
