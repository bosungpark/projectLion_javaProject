package basic;

import java.util.ArrayList;
import java.util.List;

public class BasicMain {
    public static void main(String[] args) {
        Person person= new Student("kim",26);
        Person person2= new Student("choi",27);

        Person person3= new Lecturer("Lee",30);

        List<Person> everyone= new ArrayList<>();
        everyone.add(person);
        everyone.add(person2);
        everyone.add(person3);

        for (Person p : everyone) {
            p.speak();
        }

    }
}
