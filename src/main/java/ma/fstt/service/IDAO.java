package ma.fstt.service;

import java.util.List;

public interface IDAO<T> {
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(Long id);
    T getOne(Long id);
    List<T> getAll();
}
