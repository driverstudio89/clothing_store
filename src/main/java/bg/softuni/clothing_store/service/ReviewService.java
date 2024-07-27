package bg.softuni.clothing_store.service;

import bg.softuni.clothing_store.web.dto.AddReviewDto;
import bg.softuni.clothing_store.web.dto.ReviewInfoDto;

import java.util.List;
import java.util.Set;

public interface ReviewService {

    Set<ReviewInfoDto> getAllReviewsByProduct(long id);

    boolean addReview(AddReviewDto addReviewDto, long productId);

    boolean removeReview(long id);
}
