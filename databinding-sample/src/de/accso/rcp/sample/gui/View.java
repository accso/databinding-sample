package de.accso.rcp.sample.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import de.accso.rcp.binding.AnnoBindingContext;
import de.accso.rcp.binding.anno.GUIBinding;
import de.accso.rcp.sample.data.Address;
import de.accso.rcp.sample.data.ConstField;
import de.accso.rcp.sample.data.Person;

/**
 * 
 * @author Jan Mischlich, Accso GmbH
 *
 */
public class View extends ViewPart {
	public static final String ID = "databinding-sample.view";

	/**
	 * Textfield for Person
	 */
	@GUIBinding(ConstField.Person.FIRSTNAME)
	private Text firstNameText;
	@GUIBinding(ConstField.Person.SURNAME)
	private Text surNameText;
	@GUIBinding(ConstField.Person.AGE)
	private Text ageText;

	/**
	 * Composite for Address for showing binding of nested composite classes.
	 */
	@GUIBinding(ConstField.Person.ADDRESS)
	private AddressGroup addressComp;

	/**
	 * Output Fields for showing changes.
	 */
	@GUIBinding(ConstField.Person.FIRSTNAME)
	private Text firstnameOutText;
	@GUIBinding(ConstField.Person.SURNAME)
	private Text surnameOutText;
	@GUIBinding(ConstField.Person.AGE)
	private Text ageOutText;
	@GUIBinding(ConstField.Person.ADDRESS_POSTCODE)
	private Text postcodeOutText;
	@GUIBinding(ConstField.Person.ADDRESS_STREET)
	private Text streetOutText;
	@GUIBinding(ConstField.Person.ADDRESS_LOCATION)
	private Text locationOutText;

	private AnnoBindingContext bindingCtx = new AnnoBindingContext();

	private Person person;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		
		// initialize the widgets
		parent.getShell().setSize(600, 600);
		parent.setLayout(new FillLayout());

		final Group mainGroup = new Group(parent, SWT.FILL);
		mainGroup.setLayout(new GridLayout(1, false));

		final Group personGroup = new Group(mainGroup, SWT.FILL);
		personGroup.setLayout(new GridLayout(2, false));
		personGroup.setText("Person");

		firstNameText = addTextWithLable(personGroup, "Firstname:", 1);
		surNameText = addTextWithLable(personGroup, "Surename:", 1);
		ageText = addTextWithLable(personGroup, "Age:", 1);

		addressComp = new AddressGroup(mainGroup, SWT.FILL);

		createOutputGroup(mainGroup);
		
		// create data object 
		person = createPerson();

		// bind Person to View.
		bindingCtx.bindValues(person, this);
		parent.layout();
	}

	private void createOutputGroup(Group mainGroup) {
		final Group outputGroup = new Group(mainGroup, SWT.FILL);
		outputGroup.setLayout(new GridLayout(2, false));
		outputGroup.setText("Changes");

		firstnameOutText = addText(outputGroup, 1, false);
		surnameOutText = addText(outputGroup, 1, false);
		ageOutText = addText(outputGroup, 2, false);
		streetOutText = addText(outputGroup, 2, false);
		postcodeOutText = addText(outputGroup, 1, false);
		locationOutText = addText(outputGroup, 1, false);
	}

	public static Person createPerson() {
		Person ret = new Person("John", "Doo", 18);
		ret.setAddress(new Address());
		return ret;
	}

	@Override
	public void setFocus() {
		if (this.firstNameText != null)
			this.firstNameText.setFocus();
	}

	@Override
	public void dispose() {
		super.dispose();
		bindingCtx.dispose();
	}
	
	//************************************************************
	// Some helper methods for creating the gui.
	//************************************************************
	public static Text addTextWithLable(final Composite group,
			String labelText, int horizontalSpan) {
		return addTextWithLable(group, labelText, horizontalSpan, 180,
				true);
	}

	public static Text addTextWithLable(final Composite group,
			String labelText, int horizontalSpan, int widthHint, boolean enable) {
		final Label textLabel = new Label(group, SWT.NONE);
		textLabel.setText(labelText);

		Text textObj = new Text(group, SWT.BORDER);
		textObj.setEnabled(enable);
		{
			GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, true, false,
					horizontalSpan, 1);
			gridData.widthHint = widthHint;
			textObj.setLayoutData(gridData);
		}
		return textObj;
	}

	public static Text addText(final Composite group,
			int horizontalSpan) {
		return addText(group, horizontalSpan, 100, true);
	}

	public static Text addText(final Composite group,
			int horizontalSpan, boolean enable) {
		return addText(group, horizontalSpan, 180, enable);
	}

	public static Text addText(final Composite group,
			int horizontalSpan, int widthHint, boolean enable) {

		Text textObj = new Text(group, SWT.BORDER);
		textObj.setEnabled(enable);
		{
			GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, true, false,
					horizontalSpan, 1);
			gridData.widthHint = widthHint;
			textObj.setLayoutData(gridData);
		}
		return textObj;
	}

}