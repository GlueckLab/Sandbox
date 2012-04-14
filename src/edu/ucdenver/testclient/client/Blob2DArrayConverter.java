package edu.ucdenver.testclient.client;

import com.google.gwt.core.client.GWT;

import name.pehl.piriti.converter.client.AbstractConverter;
import edu.ucdenver.bios.webservice.common.domain.Blob2DArray;

public class Blob2DArrayConverter extends AbstractConverter<Blob2DArray>
{
    @Override
    public Blob2DArray convert(String value)
    {
        if (isValid(value))
        {
            Blob2DArray blob = new Blob2DArray();
            return blob;
        }
        return null;
    }

    @Override
    public String serialize(Blob2DArray model)
    {
        GWT.log("ENTERED");
        StringBuffer buffer = new StringBuffer();
        buffer.append("\"data\":{\"data\":[" );
        double[][] data = model.getData();
        int rows = data.length;
        int columns = data[0].length;
        for(int r = 0; r < rows; r++) {
            buffer.append("[");
            for(int c = 0; c < columns; c++) {
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
