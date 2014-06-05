package de.accso.rcp.binding.strategy;

import java.lang.reflect.Field;

import org.eclipse.core.databinding.Binding;
import org.eclipse.swt.widgets.Widget;
/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public interface BindingStrategy<U extends Widget> {
	
	public<T> Binding bind(String modelFieldName, Field modelField, T modelObj, U widgetObj, Field widgetField);
}