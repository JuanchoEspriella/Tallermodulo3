package co.edu.unbosque.tallersicseven.dto;

public class CountriesDTO {

    private String name;
    private String capital;
    private String timezones;
    private String languages;
    private long population;
    private String flag;
    private String region;
    private String subregion;
    private String currencies;

    public CountriesDTO() {
    }

    public CountriesDTO(String name, String capital, String timezones, String languages,
            long population, String flag, String region, String subregion, String currencies) {
        this.name = name;
        this.capital = capital;
        this.timezones = timezones;
        this.languages = languages;
        this.population = population;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.currencies = currencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getTimezones() {
        return timezones;
    }

    public void setTimezones(String timezones) {
        this.timezones = timezones;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "CountriesDTO [name=" + name
                + ", capital=" + capital
                + ", timezones=" + timezones
                + ", languages=" + languages
                + ", population=" + population
                + ", flag=" + flag
                + ", region=" + region
                + ", subregion=" + subregion
                + ", currencies=" + currencies + "]";
    }
}
