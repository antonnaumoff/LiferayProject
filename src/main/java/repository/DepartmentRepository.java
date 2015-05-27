package repository;

import models.Department;
import utils.exceptions.DataBaseException;

import java.util.List;


public interface DepartmentRepository {

    List<Department> getAll() throws DataBaseException;

    void createDepartment(String title) throws DataBaseException;

    void deleteDepartment(int id) throws DataBaseException;

    Department getDepartmentById(int id) throws DataBaseException;

    void editDepartment(String test, int id) throws DataBaseException;

}
