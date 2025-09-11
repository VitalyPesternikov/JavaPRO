package homework2;

public class Employee {
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    private String name;

    private Integer age;

    private String position;

    public Employee(String name, Integer age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }
}
