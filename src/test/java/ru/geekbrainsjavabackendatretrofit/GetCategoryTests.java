package ru.geekbrainsjavabackendatretrofit;

import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Category;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.ELECTRONIC;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.FOOD;
import static ru.geekbrains.javabackendatretrofit.dto.utils.ConfigUtils.getFakeId;

public class GetCategoryTests extends BaseTest {

    @Test
    void getFoodCategoryPositiveTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(FOOD.getId()).execute();

        assertThat(response.code()).isEqualTo(200);
        assert response.body() != null;
        assertThat(response.body().getId()).isEqualTo(FOOD.getId());
        assertThat(response.body().getTitle()).isEqualTo(FOOD.getTitle());
    }

    @Test
    void getElectronicsCategoryPositiveTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(ELECTRONIC.getId()).execute();

        assertThat(response.code()).isEqualTo(200);
        assert response.body() != null;
        assertThat(response.body().getId()).isEqualTo(ELECTRONIC.getId());
        assertThat(response.body().getTitle()).isEqualTo(ELECTRONIC.getTitle());
    }

    @Test
    void getCategoryWithNotExistingIdNegativeTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(getFakeId()).execute();

        assertThat(response.code()).isEqualTo(404);
        assert response.errorBody() != null;
        assertThat(response.errorBody().string()).contains("Unable to find category with id: " + getFakeId());
    }

}
