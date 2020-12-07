package de.mayring.reproducer.ui;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import de.mayring.base.BaseProperties;
import de.mayring.base.ui.BaseLayout;
import de.mayring.reproducer.ui.view.HomeView;

import lombok.Getter;

import org.springframework.cache.CacheManager;

@Getter
@SpringComponent
@UIScope
@JsModule("./styles/reproducer-styles.js")
public class ReproducerLayout extends BaseLayout {

	public ReproducerLayout(BaseProperties baseProps, CacheManager cacheManager) {
		super(HomeView.class);

        Image logo = new Image("frontend/img/reproducer_logo.png", "Reproducer Logo");
		logo.addClassNames("reproducer-logo", "display-only-when-not-mobile");

		RouterLink homeLink = new RouterLink("", HomeView.class);
		homeLink.add(logo);
		homeLink.getElement().getClassList().add("reproducer-logo-link");

        super.addTopBarItems(homeLink);
	}

}
