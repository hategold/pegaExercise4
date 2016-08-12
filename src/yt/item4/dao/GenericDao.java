package yt.item4.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

	public T getById(PK Id);

	public boolean deleteById(PK Id);

	public boolean delete(T t);

	public boolean update(T t);

	public boolean insert(T t);

	public List<T> findAll();

	public List<T> findByFk(String fkFieldName, Object id);

}
