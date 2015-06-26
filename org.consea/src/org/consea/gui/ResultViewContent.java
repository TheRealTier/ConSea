package org.consea.gui;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

public class ResultViewContent extends LabelProvider implements
		IStructuredContentProvider, ITableLabelProvider, ServiceFactory<ResultViewContent>  {
	
	private ArrayList<String> results = new ArrayList<String>();
	private TableViewer parent;
	private static ResultViewContent instance = new ResultViewContent();
	
	public ResultViewContent() {

	}
	
	public void addEntry(String entry) {
		this.results.add(entry);
		this.parent.refresh();
	}
	
	public void setEntries(ArrayList<String> entries) {
		this.results = entries;
		if(this.parent != null) {
			this.parent.refresh();
		}
	}
	
	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		return this.results.toArray();
	}

	public String getColumnText(Object obj, int index) {
		return getText(obj);
	}

	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	public Image getImage(Object obj) {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
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
}
