package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.ReviewRepository;
import bg.softuni.clothing_store.data.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Set<ReviewInfoDto> getAllReviewsByProduct(long id) {
        return reviewRepository.findAllByProductId(id).stream().map(r -> {
            return modelMapper.map(r, ReviewInfoDto.class);
        }).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public boolean addReview(AddReviewDto addReviewDto, long productId) {
        if (userHelperService.getUser() == null) {
            return false;
        }
        User user = userHelperService.getUser();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return false;
        }
        Product product = optionalProduct.get();
        user.getReviewedProducts().add(product);

        Review review = modelMapper.map(addReviewDto, Review.class);
        review.setProduct(product);
        review.setAuthor(user);
        review.setCreated(LocalDate.now());

        long stars = product.getStars();
        long voted = product.getVoted();

        stars += review.getRating();
        voted += 1;

        double rating = ((double) stars /voted * 1.0);

        product.setRating(String.format("%.2f", rating));
        product.setStars(stars);
        product.setVoted(voted);

        productRepository.save(product);
        reviewRepository.save(review);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean removeReview(long id) {
        reviewRepository.deleteById(id);
        return true;
    }
}
