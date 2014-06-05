package de.accso.rcp.sample.gui;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import de.accso.rcp.binding.anno.GUIBinding;
import de.accso.rcp.sample.data.ConstField;

/**
 * 
 * @author Jan Mischlich, Accso GmbH
 *
 */
public class AddressGroup extends Group{

	@GUIBinding(ConstField.Address.POSTCODE)
	private Text postcodeText;
	@GUIBinding(ConstField.Address.STREET)
	private Text streetText;
	@GUIBinding(ConstField.Address.LOCATION)
	private Text locationText;
	
	public AddressGroup(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new GridLayout(4, false));
		this.setText("Address");
		
		streetText = View.addTextWithLable(this, "Street: ", 3);
		postcodeText = View.addTextWithLable(this, "Postcode: ", 1);
		locationText = View.addTextWithLable(this, "Location: ", 1);
	}

	@Override
	protected void checkSubclass() {
	}
}
