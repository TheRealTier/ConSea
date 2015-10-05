package org.consea.backend;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

 public  class DoubleClickInView extends Action {
	private TableViewer viewer;

	public DoubleClickInView(TableViewer viewer) {
		this.viewer = viewer;
	}

	public void run() {
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		
		ConseaSearchResonse clickTarget = (ConseaSearchResonse)obj;
	
		String constantString = clickTarget.getClsname() + "=>" + clickTarget.getCmpname();
		StringSelection stringSelection = new StringSelection(constantString);
		 
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		clipboard.setContents(stringSelection, stringSelection);
	
	}
	

}
