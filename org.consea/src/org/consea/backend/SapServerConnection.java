package org.consea.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;

import org.consea.gui.DialogWindow;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.PlatformUI;
import org.json.simple.parser.JSONParser;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IQueryParameter;
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

import java.util.Scanner;

public class SapServerConnection {

	private static final String SAMPLE_FLIGHT_RESOURCE_URI = "/consea/restconstants/constants";
	private DialogWindow dialogWindow;

	public SapServerConnection(DialogWindow dialogWindow) {
		this.dialogWindow = dialogWindow;
	}

	public void connectionToSapServer() {
		
//		IRestResourceFactory restResourceFactory = AdtRestResourceFactory.createRestResourceFactory();
//		IProject[] abapProjects = AdtProjectServiceFactory.createProjectService().getAvailableAbapProjects();
//
//		IAbapProject abapProject = (IAbapProject) abapProjects[0].getAdapter(IAbapProject.class);
//
//		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn( abapProject.getDestinationData(),
//				PlatformUI.getWorkbench().getProgressService());
//
//		String destination = abapProject.getDestinationId();
//		URI flightUri = URI.create(SAMPLE_FLIGHT_RESOURCE_URI);
//		IRestResource flightResource = restResourceFactory.createResourceWithStatelessSession(flightUri, destination);
//		
//		try {
//			System.out.println("--------01------");
//			IResponse response = flightResource.get(null, IResponse.class, new IQueryParameter() {
//
//				@Override
//				public String getKey() {
//					return "search_pattern";
//				}
//
//				@Override
//				public String getValue() {
//					return "EWE";
//				}
//				
//			});
//			
//			System.out.println("--------02------");
//			
//			InputStream inputStream = response.getBody().getContent();
//			
//			String inputStreamString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
//			inputStream.close();
//		    System.out.println(inputStreamString);
//		    
//		    JSONParser jsonParser = new JSONParser();
//		    jsonParser.parse(new StringReader(inputStreamString));
//		    
//			//dialogWindow.openDialogWindow( "Flight exists! HTTP - status:" + String.valueOf(response.getStatus()),
//			//		"Flight Confirmation");
//			System.out.println("--------03------");
//		} catch (ResourceNotFoundException e) {
//			dialogWindow.displayError("No flight data found" + e.getExceptionLongtext() );
//		} catch (RuntimeException e) {
//			dialogWindow.displayError(e.getMessage());
//		} catch (IOException e) {
//			dialogWindow.displayError(e.getMessage());
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		createMarker();
	}

	private void createMarker() {
		//IAdtMarkerService adtMarkerService = AdtMarkerServiceFactory.createMarkerService();
		//IAdtCheckService adtCheckService = AdtCheckServiceFactory.createCheckService();
	}
}