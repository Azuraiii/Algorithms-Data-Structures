package ie.dit;

/**
 * Created by azkei on 12/04/2016.
 * This algorithm checks if a String has unique character values
 */
public class stringUniqueChar {

    stringUniqueChar(){

    }

    //this function checks if the String str has unique values
    public boolean checkForUnique(String str){


        boolean containsUnique = false;

        for(char c: str.toCharArray()){
            if(str.indexOf(c) == str.lastIndexOf(c)){
                containsUnique = true;
            }else{
                containsUnique = false;
            }
        }
        return containsUnique;
    }
}
