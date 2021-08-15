package jorn.hiel.mapper.service.interfaces;

import java.util.List;

public interface RepositoryInterface<T>{

    void clearRepo();
    boolean add(T t);
    List<T> getItems();
    void printList();

}
