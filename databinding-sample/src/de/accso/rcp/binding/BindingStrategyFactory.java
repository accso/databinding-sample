package de.accso.rcp.binding;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import de.accso.rcp.binding.strategy.AbstractBindingStrategy;
import de.accso.rcp.binding.strategy.BindingStrategy;
import de.accso.rcp.binding.strategy.DefaultToButtonBindingStrategy;
import de.accso.rcp.binding.strategy.DefaultToTextBindingStrategy;
import de.accso.rcp.binding.strategy.StringToTextBindingStrategy;
/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
@SuppressWarnings("rawtypes")
public class BindingStrategyFactory {

	private final Map<BindingstrategyKey, AbstractBindingStrategy> binderMap = new ConcurrentHashMap<>();

	protected AnnoBindingContext ctx;

	public BindingStrategyFactory(AnnoBindingContext ctx) {
		this.ctx = ctx;
		putBinder(String.class, Text.class, new StringToTextBindingStrategy());
		putBinder(Integer.class, Text.class, new DefaultToTextBindingStrategy());
	}

	private void putBinder(Class<?> valueTyp,
			Class<? extends Widget> widgetTyp,
			AbstractBindingStrategy binderClass) {
		binderClass.setCtx(ctx);
		binderMap.put(new BindingstrategyKey(valueTyp, widgetTyp), binderClass);
	}

	@SuppressWarnings("unchecked")
	public BindingStrategy find(Class valueTyp, Class widgetTyp) {
		
		AbstractBindingStrategy bindClass = binderMap.get(new BindingstrategyKey(valueTyp, widgetTyp));

		if (bindClass == null) {
			bindClass = getDefault(widgetTyp);
		}
		return bindClass;
	}

	private AbstractBindingStrategy getDefault(Class widgetTyp) {
		if (Button.class.equals(widgetTyp))
			return new DefaultToButtonBindingStrategy(ctx);
		else if(Text.class.equals(widgetTyp))
			return new DefaultToTextBindingStrategy(ctx);
		return null;
	}

	public static class BindingstrategyKey {
		private Class<?> valueTyp;
		private Class<? extends Widget> widgetTyp;

		public BindingstrategyKey(Class<?> valueTyp,
				Class<? extends Widget> widgetTyp) {
			super();
			this.valueTyp = valueTyp;
			this.widgetTyp = widgetTyp;
		}

		public Class<?> getValueTyp() {
			return valueTyp;
		}

		public void setValueTyp(Class<?> valueTyp) {
			this.valueTyp = valueTyp;
		}

		public Class<? extends Widget> getWidgetTyp() {
			return widgetTyp;
		}

		public void setWidgetTyp(Class<? extends Widget> widgetTyp) {
			this.widgetTyp = widgetTyp;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((valueTyp == null) ? 0 : valueTyp.hashCode());
			result = prime * result
					+ ((widgetTyp == null) ? 0 : widgetTyp.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BindingstrategyKey other = (BindingstrategyKey) obj;
			if (valueTyp == null) {
				if (other.valueTyp != null)
					return false;
			} else if (!valueTyp.equals(other.valueTyp))
				return false;
			if (widgetTyp == null) {
				if (other.widgetTyp != null)
					return false;
			} else if (!widgetTyp.equals(other.widgetTyp))
				return false;
			return true;
		}
	}

}
