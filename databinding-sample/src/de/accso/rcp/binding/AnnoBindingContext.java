package de.accso.rcp.binding;

import java.lang.reflect.Field;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import de.accso.rcp.binding.anno.GUIBinding;
import de.accso.rcp.binding.strategy.BindingStrategy;
import de.accso.rcp.binding.util.ReflUtil;
/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public class AnnoBindingContext extends DataBindingContext {

	private BindingStrategyFactory bindingFactory;

	public AnnoBindingContext() {
		super();
		this.bindingFactory = new BindingStrategyFactory(this);
	}

	public IObservableList bindValues(final Object modelObj, final Object guiObj) {
		try {
			Class<?> guiClass = guiObj.getClass();
			while (!Object.class.equals(guiClass) && guiClass != null) {
				
				final Field[] allGuiFields = guiClass.getDeclaredFields();
				for (Field guiField : allGuiFields) {
					
					if(!guiField.isAnnotationPresent(GUIBinding.class))
						continue;
					
					Class<?> type = guiField.getType();
					guiField.setAccessible(true);
					Object guiFieldValue = guiField.get(guiObj);
					
					if (guiFieldValue != null) {
						String fieldName = guiField.getAnnotation(GUIBinding.class).value();
						
						if (Composite.class.isAssignableFrom(type)) {
							bindComposite(modelObj, fieldName,(Composite) guiFieldValue);
	
						} else if (Widget.class.isAssignableFrom(type)) {
							bindWidget(modelObj, fieldName, (Widget) guiFieldValue, guiField);
						}
					}
				}
				
				guiClass = guiClass.getSuperclass();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.getBindings();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Binding bindWidget(final Object modelObj, String fieldName,
			Widget widgetObj, Field guiField) {

		final Field valueField = ReflUtil.getField(modelObj.getClass(),
				fieldName);
		
		if (valueField == null || widgetObj == null || widgetObj.isDisposed())
			return null;

		BindingStrategy findBinding = bindingFactory.find(valueField.getType(),	guiField.getType());

		if (findBinding == null)
			throw new RuntimeException("Binding for Control-Typ: "
					+ guiField.getType() + " not supported");

		return findBinding.bind(fieldName, valueField, modelObj,
				widgetObj, guiField);
	}

	private <T> void bindComposite(final T modelObj, String fieldName,	Composite comp) {
		if (comp == null || comp.isDisposed())
			return;

		final Field valueField = ReflUtil.getField(modelObj.getClass(),
				fieldName);

		if (valueField == null)
			return;
		bindValues(ReflUtil.getProperty(modelObj, fieldName), comp);
	}
}
