package repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    void save(T t);
    T getById(ID id);
    List<T> getAll();
    T update(T t);
    T deleteById(ID id);
}
