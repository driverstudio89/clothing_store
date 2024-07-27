package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.ReviewRepository;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.Review;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.service.ReviewService;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.AddReviewDto;
import bg.softuni.clothing_store.web.dto.ReviewInfoDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final ProductRepository productRepository;

    @Override
    public Set<ReviewInfoDto> getAllReviewsByProduct(long id) {
        return reviewRepository.findAllByProductId(id).stream().map(r -> {
            return modelMapper.map(r, ReviewInfoDto.class);
        }).collect(Collectors.toSet());
    }

    @Override
    public boolean addReview(AddReviewDto addReviewDto, long productId) {
        User user = userHelperService.getUser();
        Product product = productRepository.findById(productId).get();

        if (user == null) {
            return false;
        }


        Review review = modelMapper.map(addReviewDto, Review.class);
        review.setProduct(product);
        review.setAuthor(user);
        review.setCreated(LocalDate.now());

        long stars = product.getStars();
        long voted = product.getVoted();

        stars += review.getRating();
        voted += 1;

        double rating = product.getRating();

        rating = ((double) stars /voted * 1.0);

        product.setRating(rating);
        product.setStars(stars);
        product.setVoted(voted);

        productRepository.save(product);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public boolean removeReview(long id) {
        reviewRepository.deleteById(id);
        return true;
    }
}
