package bg.softuni.clothing_store.init;

import bg.softuni.clothing_store.data.StatusRepository;
import bg.softuni.clothing_store.model.Status;
import bg.softuni.clothing_store.model.enums.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitStatus {

    private final StatusRepository statusRepository;

    public void run(String... args) throws Exception {
        if (statusRepository.count() > 0) {
            return;
        }
        for (StatusType statusType : StatusType.values()) {
            Status status = new Status(statusType);
            statusRepository.save(status);
        }
    }
}
