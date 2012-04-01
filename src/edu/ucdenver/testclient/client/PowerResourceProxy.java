package edu.ucdenver.testclient.client;

import java.util.ArrayList;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Post;
import org.restlet.client.resource.Put;
import org.restlet.client.resource.Result;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.PowerResult;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public interface PowerResourceProxy extends ClientProxy {
    /**
     * Calculate power for the specified study design.
     *
     * @param studyDesign study design object
     * @return List of power objects for the study design
     */
    @Post
    void getPower(StudyDesign studyDesign, Result<ArrayList<PowerResult>> handler);
    
    @Put
    void timesThree(NamedMatrix matrix, Result<NamedMatrix> handler);
}
