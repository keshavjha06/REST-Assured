package com.rest.pojo.workspace;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

@JsonIgnoreProperties(value = {"id", "i"} , allowSetters = true)
/*if we want to use properties conditionally, we can use @JsonIgnoreProperties
we can use @JsonIgnoreProperties for multiple properties like {"id", "i"}
For serialization we can use getter, for deserialization we can use setter*/
public class Workspace {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int i;
    private String id;
    //We can also use JsonIgnore, It will ignore everything.
    @JsonIgnore
    private HashMap<String , String> myHashMap;
    private String name;
    private String type;
    private String description;

    public Workspace() {

    }

    public Workspace(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;

    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public HashMap<String, String> getMyHashMap() {
        return myHashMap;
    }

    public void setMyHashMap(HashMap<String, String> myHashMap) {
        this.myHashMap = myHashMap;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
