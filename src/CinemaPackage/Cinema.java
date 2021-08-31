package CinemaPackage;

import People.Customer;
import People.Employee;
import auditorium.Auditorium;

import java.util.ArrayList;

public class Cinema  {
    String cinemaName;
    ArrayList<Employee> employeesList;
    ArrayList<Customer> customerList;
    Movie[] moviesArray;
    Auditorium[] auditoriumArray;

    public Cinema(String cinemaName, int moviesArrayLegnth, int auditoriumArrayLength) {
        this.cinemaName = cinemaName;
        this.employeesList = new ArrayList<Employee>();
        this.customerList = new ArrayList<Customer>();
        this.moviesArray = new Movie[moviesArrayLegnth];
        this.auditoriumArray = new Auditorium[auditoriumArrayLength];
    }

    public void addNewMovie(Movie movieToAdd){
        // Check if movie exist in our DBs. -1 for false, otherwise true
        if(isMovieExist(movieToAdd.movieName) != -1){
            System.out.println("Movie already exist, nothing to add. Exiting....");
            return;
        }
        // get the latest available index in the movie list.
        int availableMovieIndex = returnMovieLastIndex();

        if (availableMovieIndex == -1){
            System.out.println("Movie array is full! please delete a movie and try to add a movie again, later. Exiting...");
            return;
        }
        //
        long ratio = roundUp(moviesArray.length, auditoriumArray.length);
        int indexToAdd = -1;
        int i;
        for (i = 0; i <= ratio; i++){
            // add a new movie to the first avaible auditorium:
            // if we got -1 that mean we cannot add this movie.
            indexToAdd = returnAuditoriumLatestIndex();
            if (indexToAdd == -1){
                System.out.format("Movie already added %d times out of maximum %d, no more available auditorium. Exiting", i, ratio);
                return;
            }
            // If we got here, we still have index we can add to the auditorium List:
            System.out.format("Adding to auditorium number %d movie %d", indexToAdd, movieToAdd.getMovieName());
            auditoriumArray[indexToAdd].setmovieDisplayed(movieToAdd);
        }
        System.out.format("Done adding movies. added %d movies total. ", i);
    }

    public void updateMovie(String oldMovieName, Movie updatedMovie){
        // Check if movie exist - if not, exit

        int movieExistIndex = isMovieExist(oldMovieName);
        if (movieExistIndex == -1){
            System.out.format("Movie name %d does not exist in our DBs. Please try again! Exiting.... ", oldMovieName);
            return;
        }
        // Movie exist. we need to update the movie list and update all relevant auditoriums.
        moviesArray[movieExistIndex] = updatedMovie;
        for (Auditorium auditorium : auditoriumArray) {
            if (auditorium.getMovieDisplayed().movieName.equals(oldMovieName)){
                System.out.println("Updated auditorium number " + auditorium.getAuditoriumNum());
                auditorium.setmovieDisplayed(updatedMovie);
            }
        }
        System.out.println("Done updating auditoriums. Exit... ");

    }

    public void addEmployee(Employee employee){
        if (isEmployeeExist(employee)){ // check if employee already exist ...
            System.out.println("Employee with the same ID already exist in our DBs. Exiting.... ");
            return;
        }
        employeesList.add(employee);
        System.out.println("Employee added successfully: " + employee.getName());
    }

    public boolean isEmployeeExist(Employee newEmployee){
        for (Employee employee : employeesList) {
            if (employee.equals(newEmployee)){
                return true;
            }
        }
        return false;
    }


    public int returnMovieLastIndex(){
        int index = 0;
        // If the list is empty return 0 as first index available
        if (moviesArray[0] == null){
            return 0;
        }

        // Search for the first null appearance
        for (Movie movie : moviesArray) {
            if (movie == null){
                return index;
            }
            index++;
        }

        // If the list is full, return non exist index as false:
        return -1;

    }

    public int isMovieExist(String movieToCheck){
        if (moviesArray[0] == null){ // check if there is movies. if not return -1 as false.
            return -1;
        }
        int index = 0; // cant return index in forEach loop, but rather use it as more readable.
        for (Movie movie : moviesArray) {
            if (movie.movieName.equals(movieToCheck)){
                return index;
            }
            index++;
        }
        // no movie found at the list. return -1 as false.
        return -1;
    }

    public int returnAuditoriumLatestIndex(){
        int index = 0;

        // If the list is empty return 0 as first index available
        if (auditoriumArray[0] == null){
            return 0;
        }
        // // Search and return the first auditorium without movie name
        for (Auditorium auditorium : auditoriumArray) {
            if (auditorium.getMovieDisplayed().movieName.equals("")){
                return index;
            }
            index++;
        }

        // All the auditorium in our auditorium array has movies name, which means there is no availble auditorium. return -1 as false
        return -1;
    }

    public static long roundUp(long num, long divisor) {
        return (num + divisor - 1) / divisor;
    }







}
