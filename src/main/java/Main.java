public class Main {
    public static void main(String[] args) {
        PersonBuilder personBuilder = new PersonBuilder();
        Person mom = personBuilder.name("Andrew")
                .surname("Lesh")
                .age(16)
                .address("Student street")
                .build();
        Person son = mom.newChildBuilder()
                .name("Антошка")
                .build();
        System.out.println("У " + mom + " есть сын, " + son);
        try {
            // Не хватает обязательных полей
            new PersonBuilder().build();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        try {
            // Возраст недопустимый
            new PersonBuilder().age(-100).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
