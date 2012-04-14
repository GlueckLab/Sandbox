package edu.ucdenver.testclient.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import name.pehl.piriti.commons.client.ModelWriteHandler;
import name.pehl.piriti.json.client.JsonWriter;

public class NamedMatrixWriter implements JsonWriter<NamedMatrix> {

    @Override
    public HandlerRegistration addModelWriteHandler(
            ModelWriteHandler<NamedMatrix> handler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String toJson(List<NamedMatrix> models, String arrayKey) {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for(NamedMatrix model: models) {
            buffer.append(toJson(model));
        }
        return buffer.toString();
    }

    @Override
    public String toJson(NamedMatrix model) {
        GWT.log("ENTEREDE!!!!!");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"id\":" + model.getId()+ ",");
        buffer.append("\"name\":" + model.getName()+ ",");
        buffer.append("\"rows\":" + model.getRows()+ ",");
        buffer.append("\"columns\":" + model.getColumns()+ ",");
        buffer.append("\"data\":{\"data\":[" );
        double[][] data = model.getDataAsArray();
        for(int r = 0; r < model.getRows(); r++) {
            buffer.append("[");
            for(int c = 0; c < model.getColumns(); c++) {
                if (c > 0) {
                    buffer.append(",");
                }
                buffer.append(data[r][c]);
            }
            buffer.append("]");
        }
        buffer.append("]}}");
       
        return buffer.toString();
    }
}
