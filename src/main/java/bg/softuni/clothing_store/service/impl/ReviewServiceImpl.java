package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.ReviewRepository;
import bg.softuni.clothing_store.service.ReviewService;
import bg.softuni.clothing_store.web.dto.ReviewInfoDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Override
    public Set<ReviewInfoDto> getAllReviewsByProduct(long id) {
        return reviewRepository.findAllByProductId(id).stream().map(r -> {
            return modelMapper.map(r, ReviewInfoDto.class);
        }).collect(Collectors.toSet());
    }
}
