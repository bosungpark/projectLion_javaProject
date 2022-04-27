package basic;

public class Lecturer extends AbstractPerson{

    public Lecturer(String name, int age) {
        super(name, age);
    }

    @Override
    public void speak() {
        System.out.println(String.format("hello my name is %s and i am a lecturer.", getName()));
    }
}
