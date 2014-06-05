package de.accso.rcp.binding.strategy;

import java.lang.reflect.Field;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import de.accso.rcp.binding.AnnoBindingContext;
/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public class DefaultToTextBindingStrategy extends AbstractBindingStrategy<Text> {

	public DefaultToTextBindingStrategy() {
	}
	
	public DefaultToTextBindingStrategy(AnnoBindingContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public <T> Binding bind(String fieldName, Field modelField, T modelObj,
			Text widgetObj, Field widgetField) {
		IObservableValue observeValue = BeanProperties.value(
				modelObj.getClass(), fieldName).observe(modelObj);

		ISWTObservableValue observeText = SWTObservables.observeText(widgetObj,
				SWT.Modify);
		
		Binding bindValue = ctx.bindValue(observeText, observeValue);
		
		ControlDecorationSupport.create(bindValue, SWT.TOP | SWT.LEFT);
		return bindValue;
	}
}
