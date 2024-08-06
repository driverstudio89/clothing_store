package bg.softuni.clothing_store.service.impl;

import bg.softuni.clothing_store.data.*;
import bg.softuni.clothing_store.model.*;
import bg.softuni.clothing_store.model.enums.CategoryType;
import bg.softuni.clothing_store.model.enums.ColorName;
import bg.softuni.clothing_store.model.enums.SizeName;
import bg.softuni.clothing_store.model.enums.SubCategoryType;
import bg.softuni.clothing_store.service.CloudinaryService;
import bg.softuni.clothing_store.service.exception.ObjectNotFoundException;
import bg.softuni.clothing_store.web.dto.ProductShortInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    private ProductServiceImpl toTest;

    private final ModelMapper modelMapper = new ModelMapper();

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private SubCategoryRepository mockSubCategoryRepository;

    @Mock
    private CloudinaryService mockCloudinaryService;

    @Mock
    private SizeRepository mockSizeRepository;

    @Mock
    private ColorRepository mockColorRepository;

    Size size = new Size().setSizeName(SizeName.M);
    Color color = new Color().setColorName(ColorName.BLUE);
    Category category = new Category().setCategory(CategoryType.MEN);
    SubCategory subCategory = new SubCategory().setSubCategory(SubCategoryType.JACKET);

    @Mock
    Product mockProduct = new Product()
            .setId(11L)
            .setInStock(true);

    Product testProduct = new Product()
            .setId(11L)
            .setName("testProduct")
            .setPrice(BigDecimal.valueOf(15.55))
            .setDescription("testDescription")
            .setSize(Set.of(size))
            .setColor(Set.of(color))
            .setQuantity(2)
            .setImages(List.of("firstImage", "secondImage"))
            .setCreated(LocalDate.now())
            .setModified(LocalDate.now())
            .setCategory(category)
            .setSubCategory(subCategory)
            .setRating("4.44")
            .setStars(40)
            .setVoted(8)
            .setInStock(true);

    @BeforeEach
    public void setUp() {
        toTest = new ProductServiceImpl(
                mockProductRepository,
                modelMapper,
                mockCategoryRepository,
                mockSubCategoryRepository,
                mockCloudinaryService,
                mockSizeRepository,
                mockColorRepository
        );
    }

    @Test
    void testOutOfStock_Success() {

        Long id = testProduct.getId();

        when(mockProductRepository.findById(id)).thenReturn(Optional.of(testProduct));

        toTest.outOfStock(id);

        verify(mockProductRepository).save(productCaptor.capture());

        Product product = productCaptor.getValue();

        Assertions.assertEquals(testProduct.isInStock(), product.isInStock());
        System.out.println();
    }

    @Test
    void testOutOfStock_Throws() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> toTest.outOfStock(testProduct.getId()));
    }

    @Test
    void testGetProducts_ByCategory() {
        CategoryType categoryType = CategoryType.MEN;
        when(mockCategoryRepository.findByCategory(categoryType)).thenReturn(category);
        when(mockProductRepository.findAllByCategory(category)).thenReturn(Set.of(testProduct));
        Set<ProductShortInfoDto> products = toTest.getProducts(categoryType);
        for (ProductShortInfoDto actualProduct : products) {

            Assertions.assertEquals(testProduct.getId(), actualProduct.getId());
            Assertions.assertEquals(testProduct.getName(), actualProduct.getName());
            Assertions.assertEquals(testProduct.getPrice(), actualProduct.getPrice());
            Assertions.assertEquals(testProduct.getDescription(), actualProduct.getDescription());
            Assertions.assertEquals(testProduct.getSize(), actualProduct.getSize());
            Assertions.assertEquals(testProduct.getColor(), actualProduct.getColor());
            Assertions.assertEquals(testProduct.getQuantity(), actualProduct.getQuantity());
            Assertions.assertEquals(testProduct.getImages(), actualProduct.getImages());
            Assertions.assertEquals(testProduct.getCategory(), actualProduct.getCategory());
            Assertions.assertEquals(testProduct.getSubCategory(), actualProduct.getSubCategory());
            Assertions.assertEquals(testProduct.getRating(), actualProduct.getRating());
            Assertions.assertEquals(testProduct.isInStock(), actualProduct.isInStock());
        }
    }

    @Test
    void testGetProducts_ByCategory_And_SubCategory() {
        CategoryType categoryType = CategoryType.MEN;
        SubCategoryType subCategoryType = SubCategoryType.JACKET;

        when(mockCategoryRepository.findByCategory(categoryType)).thenReturn(category);
        when(mockSubCategoryRepository.findBySubCategory(subCategoryType)).thenReturn(subCategory);

        when(mockProductRepository.findAllByCategoryAndSubCategory(category, subCategory)).thenReturn(Set.of(testProduct));
        Set<ProductShortInfoDto> products = toTest.getProducts(categoryType, subCategoryType);

        for (ProductShortInfoDto actualProduct : products) {

            Assertions.assertEquals(testProduct.getId(), actualProduct.getId());
            Assertions.assertEquals(testProduct.getName(), actualProduct.getName());
            Assertions.assertEquals(testProduct.getPrice(), actualProduct.getPrice());
            Assertions.assertEquals(testProduct.getDescription(), actualProduct.getDescription());
            Assertions.assertEquals(testProduct.getSize(), actualProduct.getSize());
            Assertions.assertEquals(testProduct.getColor(), actualProduct.getColor());
            Assertions.assertEquals(testProduct.getQuantity(), actualProduct.getQuantity());
            Assertions.assertEquals(testProduct.getImages(), actualProduct.getImages());
            Assertions.assertEquals(testProduct.getCategory(), actualProduct.getCategory());
            Assertions.assertEquals(testProduct.getSubCategory(), actualProduct.getSubCategory());
            Assertions.assertEquals(testProduct.getRating(), actualProduct.getRating());
            Assertions.assertEquals(testProduct.isInStock(), actualProduct.isInStock());
        }
    }

    @Test
    void testGetProductImage_Successful() {
        Long id = testProduct.getId();
        when(mockProductRepository.findById(id)).thenReturn(Optional.of(testProduct));

        String image = toTest.getProductImage(id);

        Assertions.assertEquals(testProduct.getImages().stream().findFirst().get(), image);
    }

    @Test
    void testGetProductImage_Throws() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> toTest.getProductImage(testProduct.getId()));
    }

    @Test
    void testGetRating() {
        Long id = testProduct.getId();
        when(mockProductRepository.findById(id)).thenReturn(Optional.of(testProduct));

        String rating = toTest.getRating(id);
        Assertions.assertEquals(testProduct.getRating(), rating);
    }

    @Test
    void testGetRating_Throws() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> toTest.getRating(testProduct.getId()));
    }



}
