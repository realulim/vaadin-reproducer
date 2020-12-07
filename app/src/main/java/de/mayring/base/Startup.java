package de.mayring.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Startup {

	@Autowired
	private BaseProperties baseProps;

    @EventListener(ApplicationReadyEvent.class)
	public void doWheneverApplicationIsReady() {
		if (StringUtils.isBlank(baseProps.getUrl())) {
			throw new IllegalStateException("Please configure base.url in your application.properties");
		}
		log.info("base.url=" + baseProps.getUrl());
		if (StringUtils.isBlank(baseProps.getMandant())) {
			throw new IllegalStateException("Please configure base.mandant in your application.properties");
		}
		log.info("base.mandant=" + baseProps.getMandant());
	}

}
