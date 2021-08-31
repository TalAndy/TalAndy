package CinemaPackage;

import People.Customer;

public class thrillMovie extends Movie {
    public thrillMovie(String movieName, int duration) {
        super(movieName, duration, "Thrill");
    }

    public boolean viewRestriction(Customer customer){
        return customer.getAge() >= 16;
    }


}
