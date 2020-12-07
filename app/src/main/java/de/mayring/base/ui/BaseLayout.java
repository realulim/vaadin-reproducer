package de.mayring.base.ui;

import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts.LeftResponsiveHybrid;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import de.mayring.base.ui.elem.TopBar;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.github.appreciated.app.layout.entity.Section.HEADER;

@Slf4j
@Getter
@CssImport("./styles/base-styles.css")
@StyleSheet("https://fonts.googleapis.com/css?family=Raleway:200,200i,400,400i,600,600i,800,800i&display=swap")
@SpringComponent
@UIScope
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public abstract class BaseLayout extends AppLayoutRouterLayout<LeftResponsiveHybrid> {

	private final LeftResponsiveHybrid appLayout;
	private final List<Component> customTopBarComponents = new ArrayList<>();
	private final List<Component> customAppMenuComponents = new ArrayList<>();

	private Component brandingSection = null;
	private UserSection userSection = null;

	private final Class homeView;

	public BaseLayout(Class homeView) {
		this.homeView = homeView;
		this.appLayout = AppLayoutBuilder.get(LeftResponsiveHybrid.class)
				.withTitle(createTopBar())
				.withAppBar(AppBarBuilder.get()
						.build())
				.withAppMenu(createAppMenu())
				.build();
		init(appLayout);
	}

	public void setBrandingSection(Component customBrandingSection) {
		this.brandingSection = customBrandingSection;
	}

	public void setUser(String fullName, String department, String imagePath) {
		this.userSection = new UserSection(fullName, department, imagePath);
		this.appLayout.setAppMenu(createAppMenu());
	}

	public void addTopBarItems(Component... customComponents) {
		for (Component customComponent : customComponents) {
			this.customTopBarComponents.add(customComponent);
		}
		this.appLayout.setTitleComponent(createTopBar());
	}

	public void addAppMenuItem(Component component) {
		this.customAppMenuComponents.add(component);
		this.appLayout.setAppMenu(createAppMenu());
	}

	public void removeAppMenuItem(Component component) {
		this.customAppMenuComponents.remove(component);
		this.appLayout.setAppMenu(createAppMenu());
	}

	private TopBar createTopBar() {
		return new TopBar(homeView, this.customTopBarComponents.toArray(new Component[this.customTopBarComponents.size()]));
	}

	private Component createAppMenu() {
		LeftAppMenuBuilder builder = LeftAppMenuBuilder.get();
		if (this.brandingSection != null) {
			builder.addToSection(HEADER, brandingSection);
		}
		if (this.userSection != null) {
			builder.addToSection(HEADER, userSection);
		}
		for (Component customComponent : customAppMenuComponents) {
			builder.add(customComponent);
		}
		return builder.build();
	}

}
