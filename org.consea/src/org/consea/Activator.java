package org.consea;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.IRestResourceFactory;
import com.sap.adt.communication.resources.ResourceNotFoundException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	
	private static final String SAMPLE_FLIGHT_RESOURCE_URI = "/consea/discovery";
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("------- Initialisation ConSea-----------------");
		System.out.println("PHP 1");
		
		IRestResourceFactory restResourceFactory = AdtRestResourceFactory
				.createRestResourceFactory();
		System.out.println("PHP 2");
		IProject[] abapProjects = AdtProjectServiceFactory
				.createProjectService().getAvailableAbapProjects();
		System.out.println("PHP 3");
		IAbapProject abapProject = (IAbapProject) abapProjects[0]
				.getAdapter(IAbapProject.class);
		System.out.println("PHP 4");
		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(
				abapProject.getDestinationData(),
				PlatformUI.getWorkbench().getProgressService());
		System.out.println("PHP 5");
		String destination = abapProject.getDestinationId();
		
		
		
		URI flightUri = URI.create(SAMPLE_FLIGHT_RESOURCE_URI);
		System.out.println(destination);
		IRestResource flightResource = restResourceFactory
				.createResourceWithStatelessSession(flightUri, destination);
		System.out.println("PHP 7");
		try {
			
			IResponse response = flightResource.get(null, IResponse.class);
			System.out.println("Request erfolgreich:" + response.getStatus() );
			
		} catch (ResourceNotFoundException e) {
			System.out.println("Error");	
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	
	protected void openDialogWindow(String dialogText, String dialogTitle) {
		String[] DIALOG_BUTTON_LABELS = new String[] { IDialogConstants.OK_LABEL };
		MessageDialog dialog = new MessageDialog(getShell(), dialogTitle, null,
				dialogText, MessageDialog.INFORMATION, DIALOG_BUTTON_LABELS, 0);
		dialog.open();
	}
	
	protected Shell getShell() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return shell;
	}

}
