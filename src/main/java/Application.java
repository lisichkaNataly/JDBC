import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import model.Employee;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws SQLException {


        // Создаем переменные с данными для подключения к базе
        final String user = "postgres";
        final String password = "Mfc2022!";
        final String url = "jdbc:postgresql://localhost:5432/skypro";

        // Создаем соединение с базой с помощью Connection
        // Формируем запрос к базе с помощью PreparedStatement

        try (final Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id = (?)")) {

            // Подставляем значение вместо wildcard

            statement.setInt(1, 6);

            // Делаем запрос к базе и результат кладем в ResultSet

            final ResultSet resultSet = statement.executeQuery();

            // Методом next проверяем есть ли следующий элемент в resultSet
            // и одновременно переходим к нему, если таковой есть

            while (resultSet.next()) {

                // С помощью методов getInt и getString получаем данные из resultSet

                String first_name = "First_name: " + resultSet.getString("first_name");
                String last_name = "Fast_name: " + resultSet.getString("last_name");
                String gender = "Gender: " + resultSet.getString("gender");
                int age = resultSet.getInt(5);

                // Выводим данные в консоль
                System.out.println(first_name);
                System.out.println(last_name);
                System.out.println(gender);
                System.out.println(age);
            }

            //Создаем объект класса EmployeeDAOImpl
            EmployeeDAO employeeDao = new EmployeeDAOImpl(connection);
            Employee empl1 = new Employee(7, "Darya", "Androva", "woman", 25);

            // Создаём новый объект
            employeeDao.create(empl1);

            // Создаем список наполняя его объектами, которые получаем
            // путем вызова метода для получения всех элементов таблицы
            List <Employee> employeeList = new ArrayList<>(employeeDao.getAllEmployees());

            //Выведем список в консоль
            for (Employee employee : employeeList) {
                System.out.println(employee);
            }

            //Вызываем метод обновления данных в базе
            employeeDao.updateById(7,26);

            //Вызываем метод удаления данных из базы
            employeeDao.deleteById(7);
        }

    }

}
