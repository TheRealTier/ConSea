package org.consea.actions;

import java.util.ArrayList;

import org.consea.Activator;
import org.consea.ConseaHandler;
import org.consea.backend.SapServerConnection;
import org.consea.gui.DialogWindow;
import org.consea.gui.InputVariableNameToSearchDialog;
import org.consea.gui.ResultViewContent;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {

	DialogWindow dialogWindow;
	
	public SampleAction() {
		this.dialogWindow = new DialogWindow();
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {

		String valueToSearchFor = getUserInputValueToSearchFor();
		if(valueToSearchFor != null) {
			sendActionToView(valueToSearchFor);
		}

		SapServerConnection sapServerConnection = new SapServerConnection(dialogWindow);
		sapServerConnection.connectionToSapServer();

	}

	private String getUserInputValueToSearchFor() {
		InputVariableNameToSearchDialog inputVariableNameToSearchDialog = new InputVariableNameToSearchDialog();
		inputVariableNameToSearchDialog.setBlockOnOpen(true);
		inputVariableNameToSearchDialog.open();
		
		return inputVariableNameToSearchDialog.getValueToSearchFor();
	}

	private void sendActionToView(String variableNameToSearch) {
		// The Service was registered in this bundles activator, so we don't need a service listener,
		// but we should use a service listener, because this feels (and actually is) dirty
		BundleContext ctx = FrameworkUtil.getBundle(Activator.class).getBundleContext();	
		ServiceReference<ResultViewContent> sr = ctx.getServiceReference(ResultViewContent.class);
		ResultViewContent content = ctx.getService(sr);
		
		if(variableNameToSearch.equals("HDW") || variableNameToSearch.equals("'HDW'")  ) {
			ArrayList<String> entries = new ArrayList<String>();
			entries.add("/EAS/HDW_MAIN=>C_HDW_TITLE");
			entries.add("/EAS/HDW_MAIN_MDL=>C_HDW_DIALOG");
			entries.add("Z_CL_TIJOER_TEST=>C_HDW");
			entries.add("Z_CL_TIJOER_TEST_NEU=>C_HDW");
			entries.add("Z_CL_TIJOER_TEST_NEUER=>C_HDW");
			entries.add("Z_CL_TIJOER_TEST_NEU2=>C_HDW");
			content.setEntries(entries);
		} else {
			content.addEntry(variableNameToSearch);
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