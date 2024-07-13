package bg.softuni.clothing_store.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstInit implements CommandLineRunner {
    private final InitRoles initRoles;
    private final InitUsers initUsers;
    private final InitColor initColor;
    private final InitSize initSize;
    private final InitCategories initCategories;
    private final InitSubCategories initSubCategories;
    private final InitProducts initProducts;
    private final InitStatus initStatus;

    public FirstInit(InitRoles initRoles, InitUsers initUsers, InitColor initColor, InitSize initSize, InitCategories initCategories, InitSubCategories initSubCategories, InitProducts initProducts, InitStatus initStatus) {
        this.initRoles = initRoles;
        this.initUsers = initUsers;
        this.initColor = initColor;
        this.initSize = initSize;
        this.initCategories = initCategories;
        this.initSubCategories = initSubCategories;
        this.initProducts = initProducts;
        this.initStatus = initStatus;
    }

    @Override
    public void run(String... args) throws Exception {

        initColor.run();
        initSize.run();
        initRoles.run();
        initCategories.run();
        initSubCategories.run();
        initStatus.run();
        initProducts.run();
        initUsers.run();


    }
}
