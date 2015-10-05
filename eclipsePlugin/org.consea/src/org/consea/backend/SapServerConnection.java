package org.consea.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.consea.gui.DialogWindow;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.PlatformUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IQueryParameter;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.IRestResourceFactory;
import com.sap.adt.communication.resources.ResourceNotFoundException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

public class SapServerConnection {

	private static final String SAMPLE_FLIGHT_RESOURCE_URI = "/consea/restconstants/constants";
	private DialogWindow dialogWindow;
	private IRestResource restResource;

	public SapServerConnection(DialogWindow dialogWindow) {
		this.dialogWindow = dialogWindow;
	}

	public ArrayList<ConseaSearchResonse> conseaSearch(String conseaSearch) {
		InputStream response = sendSearchToServer(conseaSearch);
		return parseJson(response);
	}

	@SuppressWarnings("resource")
	ArrayList<ConseaSearchResonse> parseJson(InputStream response) {
		Object obj = null;
		Scanner scanner = null;
		ArrayList<ConseaSearchResonse> results = new ArrayList<>();

		try {
			scanner = new Scanner(response, "UTF-8");
			scanner = scanner.useDelimiter("\\A");
			String inputStreamString = scanner.next();
		
			response.close();
			JSONParser jsonParser = new JSONParser();
			obj = jsonParser.parse(new StringReader(inputStreamString));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(scanner != null) {
				scanner.close();
			}
		} 
		
		JSONArray jsonArray = (JSONArray) obj;
		if(jsonArray!= null && jsonArray.isEmpty() == true) {
			return null;
		}

		for (@SuppressWarnings("unchecked")Iterator<JSONObject> iterator = jsonArray.iterator(); iterator.hasNext();) {
			JSONObject jsonObject = iterator.next();
			
			ConseaSearchResonse conseaSearchResponse = new ConseaSearchResonse(jsonObject.get("attvalue").toString(), 
			                                                                   jsonObject.get("author").toString(),  
									                                           jsonObject.get("changedby").toString(),
									                                           jsonObject.get("changedon").toString(),
									                                           jsonObject.get("clsname").toString(), 
									                                           jsonObject.get("cmpname").toString(),  
									                                           jsonObject.get("createdon").toString(),
									                                           jsonObject.get("descript").toString(), 
									                                           jsonObject.get("type").toString());
			
			results.add(conseaSearchResponse); 
		}

		return results;
	}
	
	public InputStream sendSearchToServer(final String conseaSearch) {
		if(this.restResource == null) {
			restResource = createConnectionToABAPServer();
		}
		try {
			IResponse response = restResource.get(null, IResponse.class,
					new IQueryParameter() {

						@Override
						public String getKey() {
							return "search_pattern"; //TODO Use constant
						}

						@Override
						public String getValue() {
							return conseaSearch;
						}
					});
			return response.getBody().getContent();
		} catch (ResourceNotFoundException e) {
			dialogWindow.displayError("No flight data found" + e.getExceptionLongtext());
		} catch (RuntimeException e) {
			dialogWindow.displayError(e.getMessage());
		} catch (IOException e) {
			dialogWindow.displayError(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Exception handling
		return null;
	}

	private IRestResource createConnectionToABAPServer() {
		IRestResourceFactory restResourceFactory = AdtRestResourceFactory.createRestResourceFactory();
		IProject[] abapProjects = AdtProjectServiceFactory.createProjectService().getAvailableAbapProjects();

		IAbapProject abapProject = (IAbapProject) abapProjects[0].getAdapter(IAbapProject.class);

		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(
			abapProject.getDestinationData(),
			PlatformUI.getWorkbench().getProgressService());

		String destination = abapProject.getDestinationId();
		URI uri = URI.create(SAMPLE_FLIGHT_RESOURCE_URI);
		IRestResource restResource = restResourceFactory.createResourceWithStatelessSession(uri, destination);
		return restResource;
	}
}