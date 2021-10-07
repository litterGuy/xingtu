
package com.qihoo.wzws.rzb.util.osbrowser;


public class UserAgent {
    private boolean isMobile;
    private String browserVersion;
    private String platform;
    private String brand;
    private String device;
    private String browser;
    private String osVersion;


    public String getPlatform() {

        return this.platform;

    }


    public void setPlatform(String platform) {

        this.platform = platform;

    }


    public String getBrand() {

        return this.brand;

    }


    public void setBrand(String brand) {

        this.brand = brand;

    }


    public String getDevice() {

        return this.device;

    }


    public void setDevice(String device) {

        this.device = device;

    }


    public String getBrowser() {

        return this.browser;

    }


    public void setBrowser(String browser) {

        this.browser = browser;

    }


    public String getOsVersion() {

        return this.osVersion;

    }


    public void setOsVersion(String osVersion) {

        this.osVersion = osVersion;

    }


    public String toString() {

        return "UserAgent [platform=" + this.platform + ", brand=" + this.brand + ", device=" + this.device + ", browser=" + this.browser + ", osVersion=" + this.osVersion + "]";

    }


    public String output() {

        return "|" + this.platform + "|" + ((this.osVersion == null) ? "" : this.osVersion) + "|" + ((this.brand == null) ? "" : this.brand) + "|" + ((this.device == null) ? "" : this.device.trim()) + "|" + ((this.browser == null) ? "" : this.browser);

    }


    public String outputDevice() {

        return "|" + this.platform + "|" + ((this.brand == null) ? "" : this.brand) + "|" + ((this.device == null) ? "" : this.device.trim());

    }


    public String outputBrowser() {

        return this.platform + "|" + ((this.browser == null) ? "" : this.browser.trim());

    }


    public String outputBrowserForMobile() {

        return "|MO|" + this.platform + "|" + ((this.browser == null) ? "" : this.browser.trim());

    }


    public String outputBrowserForPC() {

        return "|PC|" + ((this.platform == null) ? "" : this.platform) + "|" + ((this.osVersion == null) ? "" : this.osVersion) + "|" + ((this.browser == null) ? "" : this.browser) + "|" + ((this.browserVersion == null) ? "" : this.browserVersion);

    }


    public boolean isMobile() {

        return this.isMobile;

    }


    public void setMobile(boolean isMobile) {

        this.isMobile = isMobile;

    }


    public String getBrowserVersion() {

        return this.browserVersion;

    }


    public void setBrowserVersion(String browserVersion) {

        this.browserVersion = browserVersion;

    }

}