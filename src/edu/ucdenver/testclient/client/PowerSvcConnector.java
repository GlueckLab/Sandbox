package edu.ucdenver.testclient.client;

import java.util.ArrayList;
import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;

import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NominalPower;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.StatisticalTestTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.StudyDesignViewTypeEnum;

public class PowerSvcConnector {
    
    private DomainObjectSerializer serializer = new DomainObjectSerializer();
    
    /**
     * Create a new connector to the power service
     */
    public PowerSvcConnector() {

    }   
    
    public void getPower() {
        String ttestStudyDesign = "{\"name\":\"Two Sample T-Test\",\"gaussianCovariate\":false,\"solutionTypeEnum"
            +"\":\"POWER\",\"viewTypeEnum\":\"MATRIX_MODE\",\"alphaList\":[{\"id\":0,\"alphaValue\":0.05}],"
            +"\"betaScaleList\":[{\"id\":0,\"value\":0.5}],\"sigmaScaleList\":[{\"id\":0,\"value\":2.0}],"
            +"\"sampleSizeList\":[{\"id\":0,\"value\":10},{\"id\":0,\"value\":20},{\"id\":0,\"value\":40}],"
            +"\"statisticalTestList\":[{\"id\":0,\"type\":\"UNIREP\"}],\"matrixSet\":[{\"id\":0,\"name\":\"thetaNull\","
            +"\"rows\":1,\"columns\":1,\"data\":{\"data\":[[0.0]]}},{\"id\":0,\"name\":\"beta\",\"rows\":2,\"columns\":1,"
            +"\"data\":{\"data\":[[0.0],[1.0]]}},{\"id\":0,\"name\":\"design\",\"rows\":2,\"columns\":2,\"data\":{\"data\":"
            +"[[1.0,0.0],[0.0,1.0]]}},{\"id\":0,\"name\":\"sigmaError\",\"rows\":1,\"columns\":1,\"data\":{\"data\":[[1.0]]}},"
            +"{\"id\":0,\"name\":\"betweenSubjectContrast\",\"rows\":1,\"columns\":2,\"data\":{\"data\":[[1.0,-1.0]]}}]}";
        
        String test = "[{\"nominalPower\":{\"id\":0,\"value\":0.562006757627453},\"actualPower\":0.562006757627453,\"totalSampleSize\":20,\"alpha\":{\"id\":0,\"alphaValue\":0.05},\"betaScale\":{\"id\":0,\"value\":1.0},\"sigmaScale\":{\"id\":0,\"value\":1.0},\"test\":{\"id\":0,\"type\":\"UNIREP\"},\"powerMethod\":{\"id\":0,\"powerMethodEnum\":\"CONDITIONAL\"},\"quantile\":null,\"confidenceInterval\":null},{\"nominalPower\":{\"id\":0,\"value\":0.562006757627453},\"actualPower\":0.562006757627453,\"totalSampleSize\":20,\"alpha\":{\"id\":0,\"alphaValue\":0.05},\"betaScale\":{\"id\":0,\"value\":1.0},\"sigmaScale\":{\"id\":0,\"value\":1.0},\"test\":{\"id\":0,\"type\":\"UNIREP\"},\"powerMethod\":{\"id\":0,\"powerMethodEnum\":\"CONDITIONAL\"},\"quantile\":null,\"confidenceInterval\":null},{\"nominalPower\":{\"id\":0,\"value\":0.562006757627453},\"actualPower\":0.562006757627453,\"totalSampleSize\":20,\"alpha\":{\"id\":0,\"alphaValue\":0.05},\"betaScale\":{\"id\":0,\"value\":1.0},\"sigmaScale\":{\"id\":0,\"value\":1.0},\"test\":{\"id\":0,\"type\":\"UNIREP\"},\"powerMethod\":{\"id\":0,\"powerMethodEnum\":\"CONDITIONAL\"},\"quantile\":null,\"confidenceInterval\":null}]";



        serializer.powerResultListFromJSON(test);
//        serializer.bsListFromJSON(test);
        
        
        StudyDesign design = this.buildUnivariateMatrixDesign(SolutionTypeEnum.POWER);
        List<BetweenParticipantFactor> betweenParticipantFactorList = new ArrayList<BetweenParticipantFactor>();        
        BetweenParticipantFactor betweenParticipantFactor = new BetweenParticipantFactor();             
            betweenParticipantFactor.setPredictorName("Medicine");
                ArrayList<Category> categoryList = new ArrayList<Category>();
                categoryList.add(new Category("A"));
                categoryList.add(new Category("B"));
            /*betweenParticipantFactor.setCategoryList(categoryList);*/
        betweenParticipantFactorList.add(betweenParticipantFactor); 
            betweenParticipantFactor = new BetweenParticipantFactor();                          
            
                betweenParticipantFactor.setPredictorName("Natural Food");
                    categoryList = new ArrayList<Category>();
                    categoryList.add(new Category("Fruits"));
                    categoryList.add(new Category("Grains"));
                /*betweenParticipantFactor.setCategoryList(categoryList);*/ 
        betweenParticipantFactorList.add(betweenParticipantFactor);
        
        design.setBetweenParticipantFactorList(betweenParticipantFactorList);
        
        
        try 
        {
           
            String entity = serializer.toJSON(design);
            Window.alert(entity);
            
            RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/power/power");

            builder.setHeader("Content-Type", "application/json");
            builder.sendRequest(ttestStudyDesign, new RequestCallback() {

                @Override
                public void onResponseReceived(
                        com.google.gwt.http.client.Request request,
                        com.google.gwt.http.client.Response response) {
                    // TODO Auto-generated method stub
                    Window.alert("Success: " + response.getText());
                }

                @Override
                public void onError(com.google.gwt.http.client.Request request,
                        Throwable exception) {
                    // TODO Auto-generated method stub
                    Window.alert("Error: " + exception.getMessage());
                }
            });
        } 
        catch (Exception e) 
        {
           Window.alert("Failed to send the request: " + e.getMessage());
        }     

        
    }
    
    private StudyDesign buildUnivariateMatrixDesign(SolutionTypeEnum solvingFor)
    {
        StudyDesign studyDesign = new StudyDesign();
        
        BetweenParticipantFactor betweenParticipantFactor = new BetweenParticipantFactor();       
        List<BetweenParticipantFactor> betweenParticipantFactorList = new ArrayList<BetweenParticipantFactor>();        
        betweenParticipantFactor.setPredictorName("Medicine");
            ArrayList<Category> categoryList = new ArrayList<Category>();
            categoryList.add(new Category("A"));
            categoryList.add(new Category("B"));
        /*betweenParticipantFactor.setCategoryList(categoryList);*/
    betweenParticipantFactorList.add(betweenParticipantFactor); 
        betweenParticipantFactor = new BetweenParticipantFactor();                          
        
            betweenParticipantFactor.setPredictorName("Natural Food");
                categoryList = new ArrayList<Category>();
                categoryList.add(new Category("Fruits"));
                categoryList.add(new Category("Grains"));
            /*betweenParticipantFactor.setCategoryList(categoryList);*/ 
    betweenParticipantFactorList.add(betweenParticipantFactor);
        
        
        
        studyDesign.setViewTypeEnum(StudyDesignViewTypeEnum.MATRIX_MODE);
        studyDesign.setSolutionTypeEnum(solvingFor);
        studyDesign.setName("Two Sample T-Test");

        if (solvingFor == SolutionTypeEnum.POWER
                || solvingFor == SolutionTypeEnum.SAMPLE_SIZE) {
            // add beta scale values
            ArrayList<BetaScale> betaScaleList = new ArrayList<BetaScale>();
            betaScaleList.add(new BetaScale(0.5));
            //            betaScaleList.add(new BetaScale(1.0));
            //            betaScaleList.add(new BetaScale(2.0));
            studyDesign.setBetaScaleList(betaScaleList);
        }

        if (solvingFor == SolutionTypeEnum.POWER
                || solvingFor == SolutionTypeEnum.DETECTABLE_DIFFERENCE) {
            // add per group sample sizes
            ArrayList<SampleSize> sampleSizeList = new ArrayList<SampleSize>();
            sampleSizeList.add(new SampleSize(10));
            sampleSizeList.add(new SampleSize(20));
            sampleSizeList.add(new SampleSize(40));
            studyDesign.setSampleSizeList(sampleSizeList);
        }

        if (solvingFor == SolutionTypeEnum.SAMPLE_SIZE
                || solvingFor == SolutionTypeEnum.DETECTABLE_DIFFERENCE) {
            // add nominal power values
            ArrayList<NominalPower> nominalPowerList = new ArrayList<NominalPower>();
            nominalPowerList.add(new NominalPower(0.8));
            nominalPowerList.add(new NominalPower(0.9));
            nominalPowerList.add(new NominalPower(0.975));
            studyDesign.setNominalPowerList(nominalPowerList);
        }

        // add a test 
        ArrayList<StatisticalTest> testList = new ArrayList<StatisticalTest>();
        testList.add(new StatisticalTest(StatisticalTestTypeEnum.UNIREP));
        studyDesign.setStatisticalTestList(testList);

        // add alpha values
        ArrayList<TypeIError> alphaList = new ArrayList<TypeIError>();
        alphaList.add(new TypeIError(0.05));
        //        alphaList.add(new TypeIError(0.01));
        studyDesign.setAlphaList(alphaList);

        // add sigma scale values
        ArrayList<SigmaScale> sigmaScaleList = new ArrayList<SigmaScale>();
        //        sigmaScaleList.add(new SigmaScale(0.5));
        //        sigmaScaleList.add(new SigmaScale(1.0));
        sigmaScaleList.add(new SigmaScale(2.0));
        studyDesign.setSigmaScaleList(sigmaScaleList);

        // build the design eseence matrix
        studyDesign.setNamedMatrix(new NamedMatrix("design"));

        // build between subject contrast
        double [][] betweenData = {{1,-1}};
        NamedMatrix betweenContrast = new NamedMatrix("C");
        betweenContrast.setDataFromArray(betweenData);
        betweenContrast.setRows(1);
        betweenContrast.setColumns(2);
        studyDesign.setNamedMatrix(betweenContrast);

        // build beta matrix
        double [][] betaData = {{0},{1}};
        NamedMatrix beta = new NamedMatrix("beta");
        beta.setDataFromArray(betaData);
        beta.setRows(2);
        beta.setColumns(1);
        studyDesign.setNamedMatrix(beta);

        // build theta null matrix
        double [][] thetaNullData = {{0}};
        NamedMatrix thetaNull = new NamedMatrix("theta");
        thetaNull.setDataFromArray(thetaNullData);
        thetaNull.setRows(1);
        thetaNull.setColumns(1);
        studyDesign.setNamedMatrix(thetaNull);

        // build sigma matrix
        double [][] sigmaData = {{1}};
        NamedMatrix sigmaError = new NamedMatrix("sigmaE");
        sigmaError.setDataFromArray(sigmaData);
        sigmaError.setRows(1);
        sigmaError.setColumns(1);
        studyDesign.setNamedMatrix(sigmaError);
        
        return studyDesign;
    }
    
    
}
