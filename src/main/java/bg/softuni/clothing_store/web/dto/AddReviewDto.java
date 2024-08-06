package bg.softuni.clothing_store.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    //########################################

    public @NotNull(message = "You need to type a title!") @Size(min = 3, max = 50, message = "Tittle must be between 3 and 50 characters!") String getTitle() {
        return title;
    }

    public AddReviewDto setTitle(@NotNull(message = "You need to type a title!") @Size(min = 3, max = 50, message = "Tittle must be between 3 and 50 characters!") String title) {
        this.title = title;
        return this;
    }

    public @NotNull(message = "You need to type content of the review!") @Size(min = 10, max = 500, message = "Tittle must be between 10 and 500 characters!") String getContent() {
        return content;
    }

    public AddReviewDto setContent(@NotNull(message = "You need to type content of the review!") @Size(min = 10, max = 500, message = "Tittle must be between 10 and 500 characters!") String content) {
        this.content = content;
        return this;
    }

    @NotNull(message = "You need to select rating!")
    @Min(value = 1, message = "You need to select rating!")
    @Max(value = 5, message = "You need to select rating!")
    public int getRating() {
        return rating;
    }

    public AddReviewDto setRating(@NotNull(message = "You need to select rating!") @Min(value = 1, message = "You need to select rating!") @Max(value = 5, message = "You need to select rating!") int rating) {
        this.rating = rating;
        return this;
    }
}
