package ru.netology.i18n;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    @ParameterizedTest
    @DisplayName("Тесты для проверки возвращаемого текста")
    @MethodSource("testList")
    void locale(Country country, String message) {
        String str = message;
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String countryName = localizationService.locale(country);

        assertEquals(countryName, str);
    }

    public static Stream<Arguments> testList(){
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}