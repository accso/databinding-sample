package de.accso.rcp.binding.strategy;

import java.lang.reflect.Field;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Button;

import de.accso.rcp.binding.AnnoBindingContext;
/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public class DefaultToButtonBindingStrategy extends
		AbstractBindingStrategy<Button> {

	public DefaultToButtonBindingStrategy() {
	}
	
	public DefaultToButtonBindingStrategy(AnnoBindingContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public <T> Binding bind(String fieldName, Field modelField, T modelObj,
			Button widgetObj, Field widgetField) {
		return ctx.bindValue(SWTObservables.observeSelection(widgetObj),
				BeanProperties.value(modelObj.getClass(),fieldName)
						.observe(modelObj));
	}

}
