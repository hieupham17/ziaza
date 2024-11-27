package org.example.datn.model;


public class LogInformation {
    private static LogInformation instance;

    private String httpMethod;
    private String requestURI;
    private String hostAddress;
    private String operatingSystem;
    private String browser;
    private String email;
    private String describe;
    private String location;

    private LogInformation() {

    }

    public static synchronized LogInformation getInstance() {
        if (instance == null) {
            instance = new LogInformation();
        }
        return instance;
    }

    public void updateLogInformation(String httpMethod, String requestURI, String hostAddress,
                                     String operatingSystem, String browser, String email, String describe, String location) {
        this.httpMethod = httpMethod;
        this.requestURI = requestURI;
        this.hostAddress = hostAddress;
        this.operatingSystem = operatingSystem;
        this.browser = browser;
        this.email = email;
        this.describe = describe;
        this.location = location;
    }

    @Override
    public String toString() {
        return "LogInformation{" +
                "httpMethod='" + httpMethod + '\'' +
                ", requestURI='" + requestURI + '\'' +
                ", hostAddress='" + hostAddress + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", browser='" + browser + '\'' +
                ", userCredentials='" + email + '\'' +
                '}';
    }

    public static void setInstance(LogInformation instance) {
        LogInformation.instance = instance;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
