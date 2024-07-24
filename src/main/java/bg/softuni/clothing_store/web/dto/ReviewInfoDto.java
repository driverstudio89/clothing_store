package bg.softuni.clothing_store.web.dto;

import bg.softuni.clothing_store.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReviewInfoDto {

    private long id;

    private String title;

    private User author;

    private String content;

    private LocalDate created;

    private int rating;
}
