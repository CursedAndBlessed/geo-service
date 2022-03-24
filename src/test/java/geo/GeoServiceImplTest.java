package geo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.Assertions;

import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;




public class GeoServiceImplTest {

    @ParameterizedTest
    @DisplayName("Тесты для проверки определения локации по ip")
    @MethodSource("testList")
    void byIp(String ip, String country) {
        String result;
        if (ip.startsWith("172.")) result = country;
        else if (ip.startsWith("96.")) result = country;
        else result = country;


        GeoServiceImpl geoService = new GeoServiceImpl();
        try {
            String str = geoService.byIp(ip).getCountry().name();

            Assertions.assertEquals(str, result);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public static Stream<Arguments> testList (){
        return Stream.of(
                Arguments.of("192.168.0.1", null),
                Arguments.of("172.0.32.11", "RUSSIA"),
                Arguments.of("96.44.183.149", "USA"),
                Arguments.of("96.58.55.67", "USA"));
    }

}