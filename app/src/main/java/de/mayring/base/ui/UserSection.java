package de.mayring.base.ui;

import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.artur.Avataaar;

public class UserSection extends Composite<Div> {

	public UserSection(String fullName, String department, String imagePath) {
        LeftHeaderItem avatarData = new LeftHeaderItem(fullName, department, imagePath);
        if (StringUtils.isBlank(imagePath)) {
            // create random Avatar if no image is given
            Avataaar avatarImage = new Avataaar(fullName);
            avatarImage.getElement().getClassList().add("avatar-image");
            avatarImage.getElement().getStyle().set("padding", "var(--app-layout-menu-header-padding)");
    		getContent().add(avatarImage);
        }
        avatarData.getElement().getClassList().add("avatar-data");
		getContent().add(avatarData);
	}

}
