package edu.ucdenver.testclient.client;

import org.restlet.client.Uniform;
import org.restlet.client.data.MediaType;
import org.restlet.client.data.Preference;
import org.restlet.client.resource.ClientResource;
import org.restlet.client.resource.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public class PowerSvcConnector {
    
    /**
     * Create a new connector to the power service
     */
    public PowerSvcConnector() {

    }   

    
    public void getPower(StudyDesign studyDesign, Uniform handler) {
        
        PowerResourceProxy resource = GWT.create(PowerResourceProxy.class);
        
        resource.getClientResource().setReference("http://localhost:8080/power/power");
        resource.getClientResource().getClientInfo().getAcceptedMediaTypes().add(new
        Preference<MediaType>(MediaType.APPLICATION_JAVA_OBJECT_GWT));

      NamedMatrix matrix = new NamedMatrix("test");
      double[][] data = {{1,2},{3,4}};
      matrix.setRows(2);
      matrix.setColumns(2);
      matrix.setData(data);
        
        // Retrieve the contact
        resource.timesThree(matrix, new Result<NamedMatrix>() {
            public void onFailure(Throwable caught) {
                // Handle the error
                Window.alert("Failed ");
            }

            public void onSuccess(NamedMatrix matrix) {
                // Handle the contact, for example by updating the GWT interface
                // Contact fields
                Window.alert(matrix.getName());
            }
        });
        
        
        
        
        
        
//       // Client resource for Power Web Service
//        ClientResource r = new ClientResource("http://localhost:8080/power/power/");
//        // Set the callback object invoked when the response is received.
//        r.setOnResponse(handler);
//        
//        NamedMatrix matrix = new NamedMatrix("test");
//        double[][] data = {{1,2},{3,4}};
//        matrix.setRows(2);
//        matrix.setColumns(2);
//        matrix.setData(data);
//        
//        r.put(matrix,MediaType.APPLICATION_JAVA_OBJECT_GWT);
//        
//        try{
//            // Translate the JSON String to a Person bean
//            Person p = (Person)JsonizerParser.parse(jsonizer, json);
//          }catch(JsonizerException e){
//            Window.alert('JSON Translation Error!');
//          }
        
    }
    
    
    
    
}
