package de.accso.rcp.binding.strategy;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public class StringToTextBindingStrategy extends AbstractBindingStrategy<Text>{

	@Override
	public <T> Binding bind(String fieldName, Field modelField, T modelObj,
			Text widgetObj, Field widgetField) {
		
		Column columnObj = modelField.getAnnotation(Column.class);
		if(columnObj != null && columnObj.length() > 0){
			widgetObj.setTextLimit(columnObj.length());
		}
			
		
		IObservableValue observeValue = BeanProperties.value(
				modelObj.getClass(), fieldName).observe(modelObj);

		ISWTObservableValue observeText = SWTObservables.observeText(widgetObj,
				SWT.Modify);
		
		Binding bindValue = ctx.bindValue(observeText, observeValue);
		
		ControlDecorationSupport.create(bindValue, SWT.TOP | SWT.LEFT);
		return bindValue;
	}

}
