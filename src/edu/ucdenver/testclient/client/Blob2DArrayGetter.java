package edu.ucdenver.testclient.client;

import java.util.ArrayList;
import java.util.List;

import edu.ucdenver.bios.webservice.common.domain.Blob2DArray;
import name.pehl.piriti.property.client.PropertyGetter;

public class Blob2DArrayGetter implements PropertyGetter<Blob2DArray, List<double[]>> {

    @Override
    public List<double[]> get(Blob2DArray model) {
        double[][] data = model.getData();
        ArrayList<double[]> rowOrderData = new ArrayList<double[]>();
        int rows = data.length;
        int columns = data[0].length;
        for(int r = 0; r < rows; r++) {
           double[] rowData = new double[columns];
            for(int c = 0; c < columns; c++) {
                rowData[c] = data[r][c];
            }
            rowOrderData.add(rowData);
        }
        return rowOrderData;
    }
}
