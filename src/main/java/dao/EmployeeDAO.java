package dao;

import model.Employee;

import java.util.List;

public interface EmployeeDAO {

    // Добавление объекта
    void create(Employee employee);

    // Получение объекта по id
    Employee getById(int id);

    // Получение всех объектов
    List<Employee> getAllEmployees();

    // Изменение объекта по id
    void updateById(int id, Employee employee);

    // Удаление объекта по id
    void deleteById(int id);
}
