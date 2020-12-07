package de.mayring.reproducer.ui.view;

import com.vaadin.flow.router.Route;
import de.mayring.base.ui.view.AbstractHomeView;
import de.mayring.reproducer.authorization.AccessControl;
import de.mayring.reproducer.authorization.Protected;

import de.mayring.reproducer.ui.ReproducerLayout;

@Route(value = "", layout = ReproducerLayout.class)
@Protected(AccessControl.READ)
public class HomeView extends AbstractHomeView {

	public HomeView(ReproducerLayout reproducerLayout) {
		super(reproducerLayout);
	}

}
