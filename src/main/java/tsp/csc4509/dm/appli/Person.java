package tsp.csc4509.dm.appli;

import java.io.Serializable;

/**
 * pour test function sendObject() and receiveObject()
 * @author Shihui HUANG.
 *
 */
public class Person implements Serializable {

    private String name;
    private int age;

    Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

}
