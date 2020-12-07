package de.mayring.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="base")
public class BaseProperties {

	private String url;
	private String mandant;

}
