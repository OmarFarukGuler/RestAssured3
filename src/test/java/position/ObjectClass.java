package position;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.RandomStringUtils;

public class ObjectClass {

    private  String username;
    private  String password;
    private  String rememberMe;
    private  String name;
    private  String shortName;
    private  String tenantId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    public static String RandomString(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public static String RandomInt(){
        return RandomStringUtils.randomNumeric(6);
    }
public static String RandomAlphaNumeric(){
        return RandomStringUtils.randomAlphanumeric(24);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "ObjectClass{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rememberMe='" + rememberMe + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }


}
