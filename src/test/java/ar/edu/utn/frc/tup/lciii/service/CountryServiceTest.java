package ar.edu.utn.frc.tup.lciii.service;



    import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
    import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class CountryServiceTest {

        @Mock
        private CountryRepository countryRepository;

        @Mock
        private RestTemplate restTemplate;

        @InjectMocks
        private CountryService countryService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void getAllCountries() {
            String url = "https://restcountries.com/v3.1/all";
            List<Map<String, Object>> mockResponse = List.of(
                    Map.of(
                            "cca3", "ARG",
                            "name", Map.of("common", "Argentina"),
                            "population", 45195777,
                            "area", 2780400.0,
                            "region", "Americas",
                            "languages", Map.of("spa", "Spanish"),
                            "borders", List.of("BRA", "CHL", "BOL", "PRY", "URY")
                    )
            );

            when(restTemplate.getForObject(url, List.class)).thenReturn(mockResponse);

            List<Country> countries = countryService.getAllCountries();

            assertNotNull(countries);
            assertEquals(1, countries.size());
            assertEquals("ARG", countries.get(0).getCode());
            assertEquals("Argentina", countries.get(0).getName());
        }

}