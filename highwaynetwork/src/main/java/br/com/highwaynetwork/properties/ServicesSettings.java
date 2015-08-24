package br.com.highwaynetwork.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="highway")
public class ServicesSettings {
	
	private String highwaypath;

    public String getHighwaypath() {
        return highwaypath;
    }

    public void setHighwaypath(String highwaypath) {
        this.highwaypath = highwaypath;
    }


}
