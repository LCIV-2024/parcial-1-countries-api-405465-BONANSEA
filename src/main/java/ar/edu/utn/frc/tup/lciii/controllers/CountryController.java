package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries().stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList()));
    }
    @GetMapping("/codeOrName")
    public List<CountryDTO> getAllCountries(@RequestParam(required = false) String name, @RequestParam(required = false) String code) {
        List<CountryDTO> countries = countryService.getAllCountries().stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());

        if (name != null) {
            countries = countries.stream()
                    //aca se filtra por nombre e ignoro mayusculas y minusculas
                    //https://www.w3api.com/Java/String/equalsIgnoreCase/
                    .filter(country -> country.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
        }

        if (code != null) {
            countries = countries.stream()
                    .filter(country -> country.getCode().equalsIgnoreCase(code))
                    .collect(Collectors.toList());
        }

        if(countries.isEmpty()){
            throw new RuntimeException("No se encontraron paises con los parametros ingresados");
        }

        return countries;
    }

    @GetMapping("/region")
    public List<CountryDTO> getCountriesByRegion(@RequestParam(required = false) String region) {
        List<CountryDTO> countries = countryService.getAllCountries().stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());

        if (region != null) {
            countries = countries.stream()
                    .collect(Collectors.toList());

            if (region != null) {
                countries = countries.stream()
                        .filter(country -> country.getRegion().equalsIgnoreCase(region))
                        .collect(Collectors.toList());
            }


            if (countries.isEmpty()) {
                throw new RuntimeException("No se encontraron paises con los parametros ingresados");
            }

            return countries;
        }
        return countries;
    }

    @GetMapping("/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(@PathVariable String language) {
        List<CountryDTO> countries = countryService.getCountriesByLanguage(language);
        return ResponseEntity.ok(countries);
    }

    @PostMapping
    public ResponseEntity<List<CountryDTO>> saveCountries(@RequestBody CountryRequestDTO request) {
        List<CountryDTO> countries = countryService.getAllCountries().stream()
                .limit(request.getAmountOfCountryToSave())
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(countries);
    }
}


