package edu.ucdenver.testclient.client;

import name.pehl.piriti.commons.client.Mapping;
import name.pehl.piriti.commons.client.Mappings;
import name.pehl.piriti.converter.client.Convert;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import com.google.gwt.core.client.GWT;

import edu.ucdenver.bios.webservice.common.domain.Blob2DArray;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.PowerResultList;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
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
    
    public interface NamedMatrixReader extends JsonReader<NamedMatrix> {}
    public static final NamedMatrixReader namedMatrixReader = GWT.create(NamedMatrixReader.class);
    
    public interface NamedMatrixWriter extends JsonWriter<NamedMatrix> {}
    public static final NamedMatrixWriter namedMatrixWriter = GWT.create(NamedMatrixWriter.class);    
    
    public interface Blob2DArrayWriter extends JsonWriter<Blob2DArray> {}
    public static final Blob2DArrayWriter blob2DArrayWriter = new Blob2DArrayWriterImpl();    
    
    public interface SampleSizeReader extends JsonReader<SampleSize> {}
    public static final SampleSizeReader sampleSizeReader = GWT.create(SampleSizeReader.class);

    public interface SampleSizeWriter extends JsonWriter<SampleSize> {}
    public static final SampleSizeWriter sampleSizeWriter = GWT.create(SampleSizeWriter.class);
    
    
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
