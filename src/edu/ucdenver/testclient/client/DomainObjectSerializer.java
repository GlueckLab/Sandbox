package edu.ucdenver.testclient.client;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;


import com.google.gwt.core.client.GWT;

import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.ClusterNode;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceInterval;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.PowerResultList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public class DomainObjectSerializer {
    
//    // initialize all of the available codecs
//    BetaScaleCodec betaScaleCodec = GWT.create(BetaScaleCodec.class);
//    BetweenParticipantFactorCodec betweenParticipantFactorCodec = GWT.create(BetweenParticipantFactorCodec.class);
//    CategoryCodec categoryCodec = GWT.create(CategoryCodec.class);
//    ClusterNodeCodec clusterNodeCodec = GWT.create(ClusterNodeCodec.class);
//    ConfidenceIntervalCodec  confidenceIntervalCodec = GWT.create(ConfidenceIntervalCodec.class);
    
    public interface StudyDesignReader extends JsonReader<StudyDesign> {}
    public static final StudyDesignReader studyDesignReader = GWT.create(StudyDesignReader.class);

    public interface StudyDesignWriter extends JsonWriter<StudyDesign> {}
    public static final StudyDesignWriter studyDesignWriter = GWT.create(StudyDesignWriter.class);
    
    public DomainObjectSerializer() {}
    
    public String toJSON(StudyDesign design) {
        
        String json = studyDesignWriter.toJson(design);
        return json;
    }
    
    public String toJSON(NamedMatrix matrix) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"id\":" + matrix.getId()+ ",");
        buffer.append("\"name\":" + matrix.getName()+ ",");
        buffer.append("\"rows\":" + matrix.getRows()+ ",");
        buffer.append("\"columns\":" + matrix.getColumns()+ ",");
        buffer.append("\"data\":{\"data\":[" );
        double[][] data = matrix.getDataAsArray();
        for(int r = 0; r < matrix.getRows(); r++) {
            buffer.append("[");
            for(int c = 0; c < matrix.getColumns(); c++) {
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
    
    
    public PowerResultList powerResultListFromJSON(String jsonString) {
        PowerResultList results = new PowerResultList();
        
        return results;
    }
}
