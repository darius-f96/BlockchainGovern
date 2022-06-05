package floread.backendapi.services;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String>{

    @Override
    public boolean test(String s){
        //validate email
        return true;
    }
}
