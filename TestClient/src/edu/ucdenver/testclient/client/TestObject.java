package edu.ucdenver.testclient.client;

import java.io.Serializable;

public class TestObject implements Serializable {
    
    double value;
    String name;
    
    public TestObject(String name, double value) {
        
    }
    public String toJSON() {
        return "foo";
    }
}
