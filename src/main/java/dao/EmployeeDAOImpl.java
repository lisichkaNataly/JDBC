package dao;

import model.Employee;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO{

    private final Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Employee employee) {
        // Формируем запрос к базе с помощью PreparedStatement

        try (PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO employee (first_name,last_name, gender , age,city_id) VALUES ((?), (?), (?), (?),(?))")) {

            // Подставляем значение вместо wildcard
            // первым параметром указываем порядковый номер wildcard
            // вторым параметром передаем значение

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getId());

            // С помощью метода executeUpdate отправляем запрос к базе

            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getById(int id) {
        Employee employee = new Employee();

        // Формируем запрос к базе с помощью PreparedStatement
        try (PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM employee INNER JOIN city ON employee.city_id = city.city_id AND id=(?)")){

            // Подставляем значение вместо wildcard
            statement.setInt(1,id);

            // Делаем запрос к базе и результат кладем в ResultSet
            ResultSet resultSet = statement.executeQuery();

            // Методом next проверяем есть ли следующий элемент в resultSet
            // и одновременно переходим к нему, если таковой есть
            while (resultSet.next()) {

                // С помощью методов getInt и getString получаем данные из resultSet
                // и присваиваем их полим объекта

                employee.setId(resultSet.getInt("employee_id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(resultSet.getInt("age"));
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {

        // Создаем список, в который будем складывать объекты
        List <Employee> employees = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM employee LEFT JOIN city ON employee.city_id=city.city_id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");

                // Создаем объекты на основе полученных данных
                // и укладываем их в итоговый список
                employees.add((new Employee(id, firstName, lastName, gender, age)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }



    @Override
    public void updateById(int id, Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE employee SET (first_name = (?), last_name = (?) , gender = (?) , age = (?)) WHERE id=(?)")) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM employee WHERE id=(?)")) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
