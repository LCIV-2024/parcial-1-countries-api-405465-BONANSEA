package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Entity(name = "countries")
public class CountryEntity {
    @Id
    private String code;
    private String name;
    private long population;
    private double area;
    private String region;
    //private List<String> borders;
     @JoinTable(name = "languages")
     @ManyToOne
    private LanguageEntity languages;

}
