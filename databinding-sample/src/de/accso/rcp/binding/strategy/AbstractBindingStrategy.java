package de.accso.rcp.binding.strategy;

import org.eclipse.swt.widgets.Widget;

import de.accso.rcp.binding.AnnoBindingContext;
/**
 * 
 * @author Jan Mischlich, Accso GmbH
 * 
 */
public abstract class AbstractBindingStrategy<U extends Widget> implements
		BindingStrategy<U> {

	protected AnnoBindingContext ctx;

	public void setCtx(AnnoBindingContext ctx) {
		this.ctx = ctx;
	}

}