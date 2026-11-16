package co.edu.unbosque.tallersicseven.dto;

public class CountriesDTO {

    private String name;
    private String capital;
    private String timezones;
    private String languages;
    private long population;
    private String flag;
    private String continents;
    private String region;
    private String currencies;

    public CountriesDTO() {
        super();
    }

    public CountriesDTO(String name, String capital,
            String timezones, String languages,
            long population, String flag,
            String continents, String region,
            String currencies) {

        super();
        this.name = name;
        this.capital = capital;
        this.timezones = timezones;
        this.languages = languages;
        this.population = population;
        this.flag = flag;
        this.continents = continents;
        this.region = region;
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

    public String getContinents() {
        return continents;
    }

    public void setContinents(String continents) {
        this.continents = continents;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
                + ", continents=" + continents
                + ", region=" + region
                + ", currencies=" + currencies + "]";
    }
}
