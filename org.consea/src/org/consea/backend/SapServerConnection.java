package org.consea.backend;

import java.net.URI;

import org.consea.gui.DialogWindow;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.IRestResourceFactory;
import com.sap.adt.communication.resources.ResourceNotFoundException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.checkservice.AdtCheckServiceFactory;
import com.sap.adt.tools.core.checkservice.IAdtCheckService;
import com.sap.adt.tools.core.markers.AdtMarkerServiceFactory;
import com.sap.adt.tools.core.markers.IAdtMarkerService;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

public class SapServerConnection {
	
	private static final String SAMPLE_FLIGHT_RESOURCE_URI = "/consea/restconstants/constants";
	private DialogWindow dialogWindow;

	public SapServerConnection(DialogWindow dialogWindow) {
		this.dialogWindow = dialogWindow;
	}
	
	public void connectionToSapServer() {
		
		IRestResourceFactory restResourceFactory = AdtRestResourceFactory.createRestResourceFactory();
		IProject[] abapProjects = AdtProjectServiceFactory.createProjectService().getAvailableAbapProjects();

		IAbapProject abapProject = (IAbapProject) abapProjects[0].getAdapter(IAbapProject.class);

		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn( abapProject.getDestinationData(),
				PlatformUI.getWorkbench().getProgressService());

		String destination = abapProject.getDestinationId();
		URI flightUri = URI.create(SAMPLE_FLIGHT_RESOURCE_URI);
		IRestResource flightResource = restResourceFactory.createResourceWithStatelessSession(flightUri, destination);
		try {
			System.out.println("--------01------");
			IResponse response = flightResource.get(null, IResponse.class);
			
			System.out.println("--------02------");
			
			dialogWindow.openDialogWindow( "Flight exists! HTTP - status:" + String.valueOf(response.getStatus()),
					"Flight Confirmation");
			System.out.println("--------03------");
		} catch (ResourceNotFoundException e) {
			dialogWindow.displayError("No flight data found" + e.getExceptionLongtext() );
		} catch (RuntimeException e) {
			dialogWindow.displayError(e.getMessage());
		}
		
		createMarker();
	}

	private void createMarker() {
		IAdtMarkerService adtMarkerService = AdtMarkerServiceFactory.createMarkerService();
		IAdtCheckService adtCheckService = AdtCheckServiceFactory.createCheckService();
		
	}
}