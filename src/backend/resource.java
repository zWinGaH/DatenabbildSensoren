/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author Peaq PNB P2015
 */
public class resource {
    public int id;
    public String name;
    public String protocol;
    public String adress;
    public String attribute;
    public int queryFrquzy;
    public String datatype;
    public int output;
    public int upperLimit;
    public int lowerLimit;
    
    public resource(int id, String name, String protocol, String adress, String attribute, int queryFrquzy, String datatype, 
            int output, int upperLimit, int lowerLimit){
        this.id = id;
        this.name = name;
        this.protocol = protocol;
        this.adress = adress;
        this.attribute = attribute;
        this.queryFrquzy = queryFrquzy;
        this.datatype = datatype;
        this.output = output;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }
}
