package ru.geekbrains.javabackendatretrofit.dto.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class ConfigUtils {
    Properties prop = new Properties();
    private static InputStream configFile;

    static {
        try {
            configFile = new FileInputStream("src/test/resources/application.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public String getBaseUrl() {
        prop.load(configFile);
        return prop.getProperty("base.url");
    }

    @SneakyThrows
    public Integer getFakeId() {
        prop.load(configFile);
        return Integer.valueOf(prop.getProperty("fakeId"));
    }

    @SneakyThrows
    public Integer getNegativeNum() {
        prop.load(configFile);
        return Integer.valueOf(prop.getProperty("negativeNum"));
    }

}
