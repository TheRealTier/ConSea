package org.consea.actions;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.IRestResourceFactory;
import com.sap.adt.communication.resources.ResourceNotFoundException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	
	private static final String SAMPLE_FLIGHT_RESOURCE_URI = "/consea/rest_consea";
	
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		MessageDialog.openInformation(
			window.getShell(),
			"Consea",
			"Hello, Eclipse world");
		
		System.out.println("------------------------------------asdasd--------");
		openDialogWindow("Test","Test");
		
		IRestResourceFactory restResourceFactory = AdtRestResourceFactory
				.createRestResourceFactory();
		IProject[] abapProjects = AdtProjectServiceFactory
				.createProjectService().getAvailableAbapProjects();

		IAbapProject abapProject = (IAbapProject) abapProjects[0]
				.getAdapter(IAbapProject.class);

		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(
				abapProject.getDestinationData(),
				PlatformUI.getWorkbench().getProgressService());

		String destination = abapProject.getDestinationId();
		URI flightUri = URI.create(SAMPLE_FLIGHT_RESOURCE_URI);
		IRestResource flightResource = restResourceFactory
				.createResourceWithStatelessSession(flightUri, destination);
		try {
			IResponse response = flightResource.get(null, IResponse.class);
			openDialogWindow(
					"Flight exists! HTTP - status:"
							+ String.valueOf(response.getStatus()),
					"Flight Confirmation");
		} catch (ResourceNotFoundException e) {
			displayError("No flight data found");
		} catch (RuntimeException e) {
			displayError(e.getMessage());
		}
	
		
		System.out.println("------------------------------------asdasd--------");
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
	private void displayError(String messageText) {
		String dialogTitle = "Flight Exception";
		openDialogWindow(messageText, dialogTitle);
	}

	protected void openDialogWindow(String dialogText, String dialogTitle) {
		String[] DIALOG_BUTTON_LABELS = new String[] { IDialogConstants.OK_LABEL };
		MessageDialog dialog = new MessageDialog(getShell(), dialogTitle, null,
				dialogText, MessageDialog.INFORMATION, DIALOG_BUTTON_LABELS, 0);
		dialog.open();
	}

	protected Shell getShell() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		return shell;
	}
	
}