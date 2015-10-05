package org.consea.gui;

import java.util.ArrayList;

import org.consea.backend.ConseaSearchResonse;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * TODO This class is also registered as a service in the activator. It has a reference to the 
 * view (parent) to refresh it, after content was added. This is a horrible way to do this.
 * Split this class, write a good service, and split it from the view logic.
 */
public class ResultViewContent extends LabelProvider implements
		IStructuredContentProvider, ITableLabelProvider, ServiceFactory<ResultViewContent>  {
	
	private ArrayList<ConseaSearchResonse> results = new ArrayList<>();
	private TableViewer parent;
	private Composite composite;
	private static ResultViewContent instance = new ResultViewContent();
	
	public ResultViewContent() {

	}
	
	public void addEntry(ConseaSearchResonse entry) {
		this.results.add(entry);
		if(this.parent != null) {
			this.parent.refresh();
		}
	}
	
	public void setEntries(ArrayList<ConseaSearchResonse> entries) {
		this.results = entries;
		if(this.parent != null && this.results != null) {
			this.parent.refresh();
		}
	}
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Image getImage(Object obj) {
		return null;
		//return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
	
	public Object[] getElements(Object parent) {
		return this.results.toArray();
	}

	public String getColumnText(Object obj, int index) {
		
		if (!(obj instanceof ConseaSearchResonse)) {
			return null;
		}
		
		ConseaSearchResonse conseaSearchResonse = (ConseaSearchResonse)obj;
		
		switch (index) {
		case 0:
			return conseaSearchResonse.getClsname();
			
		case 1:
			return conseaSearchResonse.getCmpname();
		
		case 2:
			return conseaSearchResonse.getAttvalue();

		case 3:
			return conseaSearchResonse.getType();

		default:
			break;
		}
		return null;
	}

	public Image getColumnImage(Object obj, int index) {
		//return null;
		return getImage(obj);
	}


	@Override
	public ResultViewContent getService(Bundle bundle, ServiceRegistration<ResultViewContent> registration) {
		return ResultViewContent.instance;
	}

	@Override
	public void ungetService(Bundle bundle, ServiceRegistration<ResultViewContent> registration,
			ResultViewContent service) {
		//nothing to be done
	}

	public void setParent(TableViewer viewer) {
		this.parent = viewer;
	}

	public void clear() {
		this.results = new ArrayList<ConseaSearchResonse>();
		
		if(this.parent != null) {
			this.parent.refresh(true, true);
		}
	}

	public void setComposite(Composite composite) {
		this.composite = composite;
	}
}
