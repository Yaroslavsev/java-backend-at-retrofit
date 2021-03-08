package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.FOOD;
import static ru.geekbrains.javabackendatretrofit.dto.utils.ConfigUtils.getFakeId;
import static ru.geekbrains.javabackendatretrofit.dto.utils.DbUtils.selectProductById;

public class PutProductNegativeTests extends BaseTest {

    @SneakyThrows
    @Test
    void modifyNotExistingProductPositiveTest() {

        Response<Product> response = productService.modifyProduct(new Product()
                .withId(getFakeId())
                .withTitle(faker.lebowski().actor())
                .withPrice((int) (Math.random()*1000+100000))
                .withCategoryTitle(FOOD.getTitle()))
                .execute();

        assertThat(response.code()).isEqualTo(400);
        assert response.errorBody() != null;
        assertThat(response.errorBody().string()).contains("Product with id: " + getFakeId() + " doesn't exist");

        assertThat(selectProductById(getFakeId())).isNull();
    }

}
