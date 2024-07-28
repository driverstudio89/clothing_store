package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddReviewDto {

    @NotNull(message = "You need to type a title!")
    @Size(min = 3, max = 50, message = "Tittle must be between 3 and 50 characters!")
    private String title;

    @NotNull(message = "You need to type content of the review!")
    @Size(min = 10, max = 500, message = "Tittle must be between 10 and 500 characters!")
    private String content;

    @NotNull(message = "You need to select rating!")
    @Min(value = 1, message = "You need to select rating!")
    @Max(value = 5, message = "You need to select rating!")
    private int rating;
}
