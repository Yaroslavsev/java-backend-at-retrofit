package ru.geekbrainsjavabackendatretrofit;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;
import ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories;
import ru.geekbrains.javabackendatretrofit.dto.service.CategoryService;
import ru.geekbrains.javabackendatretrofit.dto.service.ProductService;
import ru.geekbrains.javabackendatretrofit.dto.utils.RetrofitUtil;

public class BaseTest {

    static ProductService productService;
    static CategoryService categoryService;

    Product product;
    Faker faker = new Faker();
    Response<Product> response;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {

        productService = RetrofitUtil.getRetrofit().create(ProductService.class);
        categoryService = RetrofitUtil.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    public Response<Product> createProductTestData(){

        return productService.createProduct(prepareProductTestData()).execute();
    }

    public Product prepareProductTestData() {

        return product = new Product()
                .withCategoryTitle(Categories.FOOD.getTitle())
                .withTitle(faker.beer().name())
                .withPrice((int) (Math.random()*1000+1));
    }

}
