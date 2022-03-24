package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTest {

    @ParameterizedTest
    @DisplayName("Проверка метода отправки сообщений")
    @MethodSource("testList")
    void send(String ip, Location location, String greeting) {
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);
        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(greeting);
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-real-ip", ip);
        String actual = messageSender.send(headers);

        String expected = null;
        if (ip.startsWith("172.")) expected = "Добро пожаловать";
        else if (ip.equals("127.0.0.1")) expected = "Localhost";
        else expected = "Welcome";

        Assertions.assertEquals(actual, expected);

    }

    public static Stream<Arguments> testList() {
        return Stream.of(
                Arguments.of("172.3.2.1", new Location("Moscow", Country.RUSSIA, null, 0), "Добро пожаловать"),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32), "Welcome"),
                Arguments.of("127.0.0.1", new Location("Moscow", Country.RUSSIA, "Lenina", 15), "Localhost"),
                Arguments.of("100.44.183.149", new Location("New York", Country.USA, "null", 32), "Welcome"));
    }
}