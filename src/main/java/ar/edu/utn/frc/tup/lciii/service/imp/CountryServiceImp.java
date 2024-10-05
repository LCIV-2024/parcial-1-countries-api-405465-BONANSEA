package ar.edu.utn.frc.tup.lciii.service.imp;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

public class CountryServiceImp {

    private CountryService countryService;


    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries().stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList()));
    }

    public List<CountryDTO> getAllCountries( String name,  String code) {
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

    public List<CountryDTO> getCountriesByRegion( String region) {
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

    public List<CountryDTO> getCountriesByLanguage( String language) {

        List<CountryDTO> countries = countryService.getAllCountries().stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());

        if (language != null) {
            countries = countries.stream()
                    .filter(country -> country.getLanguages().containsKey(language))
                    .collect(Collectors.toList());
        }

        if (countries.isEmpty()) {
            throw new RuntimeException("No se encontraron paises con los parametros ingresados");
        }

        return countries;
    }

    public ResponseEntity<List<CountryDTO>> saveCountries( CountryRequestDTO request) {
        List<CountryDTO> countries = countryService.getAllCountries().stream()
                .limit(request.getAmountOfCountryToSave())
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(countries);
    }
}
