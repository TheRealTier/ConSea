package org.consea.actions;

import java.util.ArrayList;

import org.consea.Activator;
import org.consea.backend.ConseaSearchResonse;
import org.consea.backend.SapServerConnection;
import org.consea.gui.DialogWindow;
import org.consea.gui.InputVariableNameToSearchDialog;
import org.consea.gui.ResultViewContent;
import org.consea.marker.Kappamarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;


public class ConseaMenuButton implements IWorkbenchWindowActionDelegate {

	DialogWindow dialogWindow;

	public ConseaMenuButton() {
		this.dialogWindow = new DialogWindow();
	}

	public void run(IAction action) {

		Kappamarker coolmarker = new Kappamarker();
		coolmarker.newMarker();
		
		String valueToSearchFor = getUserInputValueToSearchFor();
		if (valueToSearchFor != null) {
			SapServerConnection sapServerConnection = new SapServerConnection(dialogWindow);
			ArrayList<ConseaSearchResonse> conseaEntries = sapServerConnection.conseaSearch(valueToSearchFor);
			sendActionToView(conseaEntries);
		}
		
	}

	private String getUserInputValueToSearchFor() {
		String selectedText = null;

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		ISelection selection = page.getSelection();
		if (selection instanceof TextSelection) {
			final TextSelection textSel = (TextSelection) selection;
			selectedText = textSel.getText().isEmpty() ? "":textSel.getText();
		}

		InputVariableNameToSearchDialog inputVariableNameToSearchDialog = new InputVariableNameToSearchDialog(selectedText);
		inputVariableNameToSearchDialog.setBlockOnOpen(true);
		inputVariableNameToSearchDialog.open();

		return inputVariableNameToSearchDialog.getValueToSearchFor();
	}

	private void sendActionToView(ArrayList<ConseaSearchResonse> conseaEntries) {
		// The Service was registered in this bundles activator, so we don't
		// need a service listener,
		// but we should use a service listener, because this feels (and
		// actually is) dirty
		BundleContext ctx = FrameworkUtil.getBundle(Activator.class).getBundleContext();
		ServiceReference<ResultViewContent> sr = ctx.getServiceReference(ResultViewContent.class);
		ResultViewContent content = ctx.getService(sr);		
		
		for(ConseaSearchResonse entry : conseaEntries) {
			content.addEntry(entry);
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.dialogWindow.setWindow(window);
	}

}