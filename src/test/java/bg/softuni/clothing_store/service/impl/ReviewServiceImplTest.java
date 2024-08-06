package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.ProductRepository;
import bg.softuni.clothing_store.data.ReviewRepository;
import bg.softuni.clothing_store.data.UserRepository;
import bg.softuni.clothing_store.model.Product;
import bg.softuni.clothing_store.model.Review;
import bg.softuni.clothing_store.model.User;
import bg.softuni.clothing_store.service.session.UserHelperService;
import bg.softuni.clothing_store.web.dto.AddReviewDto;
import bg.softuni.clothing_store.web.dto.ReviewInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    private ReviewServiceImpl toTest;

    private final ModelMapper modelMapper = new ModelMapper();

    @Captor
    private ArgumentCaptor<Review> reviewCaptor;

    @Mock
    private ReviewRepository mockReviewRepository;

    @Mock
    private UserHelperService mockUserHelperService;

    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    User mockUser = new User();

    @Mock
    private Product mockProduct = new Product()
            .setId(11L);

    private Review testReview = new Review()
            .setId(22L)
            .setTitle("testTitle")
            .setContent("testContent")
            .setRating(4)
            .setAuthor(mockUser)
            .setCreated(LocalDate.now())
            .setProduct(mockProduct);


    @BeforeEach
    void setUp() {
        toTest = new ReviewServiceImpl(
                mockReviewRepository,
                modelMapper,
                mockUserHelperService,
                mockProductRepository,
                mockUserRepository
        );
    }

    @Test
    void testAddReview_success() throws Exception {
        AddReviewDto addReviewDto = createAddReviewDto();
        Long id = mockProduct.getId();

        when(mockUserHelperService.getUser()).thenReturn(mockUser);
        when(mockProductRepository.findById(id)).thenReturn(Optional.ofNullable(mockProduct));

        toTest.addReview(addReviewDto, id);

        verify(mockReviewRepository).save(reviewCaptor.capture());

        Review review = reviewCaptor.getValue();

        Assertions.assertEquals(addReviewDto.getTitle(), review.getTitle());
        Assertions.assertEquals(addReviewDto.getContent(), review.getContent());
        Assertions.assertEquals(addReviewDto.getRating(), review.getRating());
        Assertions.assertTrue(toTest.addReview(addReviewDto, id));
    }

    @Test
    void testAddReview_Fail_UserIsNull() throws Exception {
        AddReviewDto addReviewDto = createAddReviewDto();
        Long id = mockProduct.getId();

        when(mockUserHelperService.getUser()).thenReturn(null);
        Assertions.assertFalse(toTest.addReview(addReviewDto, id));
    }

    @Test
    void testAddReview_Fail_ProductIsNull() throws Exception {
        AddReviewDto addReviewDto = createAddReviewDto();
        Long id = mockProduct.getId();

        when(mockUserHelperService.getUser()).thenReturn(mockUser);
        when(mockProductRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertFalse(toTest.addReview(addReviewDto, id));
    }

    @Test
    void testGetAllReviewsByProduct() throws Exception {
        Long id = mockProduct.getId();
        when(mockReviewRepository.findAllByProductId(id)).thenReturn(Set.of(testReview));

        Set<ReviewInfoDto> getAllReviewsByProduct = toTest.getAllReviewsByProduct(id);

        Assertions.assertEquals(testReview.getContent(), getAllReviewsByProduct.stream().findFirst().get().getContent());
        Assertions.assertEquals(testReview.getTitle(), getAllReviewsByProduct.stream().findFirst().get().getTitle());
        Assertions.assertEquals(testReview.getRating(), getAllReviewsByProduct.stream().findFirst().get().getRating());
        Assertions.assertEquals(testReview.getCreated(), getAllReviewsByProduct.stream().findFirst().get().getCreated());
        Assertions.assertEquals(testReview.getAuthor(), getAllReviewsByProduct.stream().findFirst().get().getAuthor());
    }



    private static AddReviewDto createAddReviewDto() {
        return new AddReviewDto()
                .setTitle("testTitle")
                .setContent("testContent")
                .setRating(4);
    }

}
