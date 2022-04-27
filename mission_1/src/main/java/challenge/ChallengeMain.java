package challenge;

import basic.Lecturer;
import basic.Person;
import basic.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChallengeMain {
    public static void main(String[] args) {
        Person person= new Student("kim",26);
        Person person2= new Student("choi",27);

        Person person3= new Lecturer("Lee",30);

        List<Person> everyone= new ArrayList<>();
        everyone.add(person);
        everyone.add(person2);
        everyone.add(person3);

        printItems(everyone);
    }

    public static <T> void printItems(Iterable<T> iterable){
        Iterator<T> iterator= iterable.iterator();
        if(!iterator.hasNext()){
            System.out.println("No elements");
            return;
        }

        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("idx\t\titem\n");
        for(int i=0; iterator.hasNext(); i++){
            T item= iterator.next();
            stringBuilder.append(
                    String.format("%d\t\t%s\n", i, item.toString()));
        }
        System.out.println(stringBuilder);
    }
}
