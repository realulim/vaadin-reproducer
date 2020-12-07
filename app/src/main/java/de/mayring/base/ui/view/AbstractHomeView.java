package de.mayring.base.ui.view;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import de.mayring.base.auth.SecurityUtils;
import de.mayring.base.da.BaseUser;
import de.mayring.base.ui.BaseLayout;

public abstract class AbstractHomeView extends FlexLayout {

	public AbstractHomeView(BaseLayout layout) {
		BaseUser currentUser = SecurityUtils.getCurrentUser();
		layout.setUser(currentUser.getFullName(), currentUser.getDepartment(), currentUser.getImagePath());
	}

}
