package Database.DAOs;
import java.util.List;

public interface BasicDAO<T> {
    void create(T t);
    T read(int id);
    List<T> readAll();
    void update(T t);
    void delete(int id);
}

