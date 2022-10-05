package CampusMersys.CampusModel;

import org.apache.commons.lang3.RandomStringUtils;

public class Cauntry {
    String id;
    String name;
    String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Campus{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
    public static String generateCountryName() {


        return RandomStringUtils.randomAlphabetic(10);
    }
    public static String generateCountryCode() {


        return RandomStringUtils.randomAlphabetic(6);
    }

}
