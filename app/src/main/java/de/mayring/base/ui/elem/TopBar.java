package de.mayring.base.ui.elem;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

public class TopBar extends HorizontalLayout {

	public TopBar(Class homeView, Component... customComponents) {
		setAlignItems(Alignment.CENTER);
		setWidth("100%");

		Image baseLogo = new Image("frontend/img/base_logo.png", "Base Logo");
		baseLogo.addClassNames("base-logo", "display-only-when-not-mobile");

		RouterLink homeLink = new RouterLink("", homeView);
		homeLink.add(baseLogo);
		homeLink.getElement().getClassList().add("base-logo-link");

		Div gutter = new Div();
		gutter.addClassName("gutter");

		Icon logout = new Icon(VaadinIcon.SIGN_OUT);
		logout.addClassName("logout");
		logout.setSize("36px");
		logout.addClickListener(event -> {
			VaadinSession.getCurrent().getSession().invalidate();
			UI.getCurrent().getPage().executeJavaScript("window.location.href=''");
		});

		add(homeLink);
		for (Component customComponent : customComponents) {
			add(customComponent);
		}
		add(gutter, logout);
	}

}
