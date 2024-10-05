package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCountries() {

        List<Country> countries = new ArrayList<>();
        countries.add(Country.builder().code("ARG").name("Argentina").region("Americas").languages(Map.of("es", "Spanish")).build());
        countries.add(Country.builder().code("BRA").name("Brazil").region("Americas").languages(Map.of("pt", "Portuguese")).build());
        countries.add(Country.builder().code("USA").name("United States").region("Americas").languages(Map.of("en", "English")).build());

        List<CountryDTO> expected = new ArrayList<>();
        expected.add(new CountryDTO("ARG", "Argentina", "Americas", Map.of("es", "Spanish")));
        expected.add(new CountryDTO("BRA", "Brazil", "Americas", Map.of("pt", "Portuguese")));
        expected.add(new CountryDTO("USA", "United States", "Americas", Map.of("en", "English")));

        when(countryService.getAllCountries()).thenReturn(countries);
        when(countryService.mapToDTO(countries.get(0))).thenReturn(expected.get(0));
        when(countryService.mapToDTO(countries.get(1))).thenReturn(expected.get(1));
        when(countryService.mapToDTO(countries.get(2))).thenReturn(expected.get(2));

        ResponseEntity<List<CountryDTO>> response = countryController.getAllCountries();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected.size(), response.getBody().size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getCode(), response.getBody().get(i).getCode());
            assertEquals(expected.get(i).getName(), response.getBody().get(i).getName());
        }
    }
}



