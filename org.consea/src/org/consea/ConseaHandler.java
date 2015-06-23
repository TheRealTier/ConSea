package org.consea;

import java.net.URI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.IRestResourceFactory;
import com.sap.adt.communication.resources.ResourceNotFoundException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

public class ConseaHandler extends AbstractHandler {

	private static final String SAMPLE_FLIGHT_RESOURCE_URI = "/consea/rest_consea";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
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
		return null;
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
