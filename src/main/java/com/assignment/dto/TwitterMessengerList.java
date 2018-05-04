package com.assignment.dto;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @param <T>
 */
public class TwitterMessengerList<T>  extends LinkedList<T>{

    public TwitterMessengerList() {
    }

    public boolean add(T object){
        if (object instanceof  UserDTO) {
            if(super.contains(object)){
                ((UserDTO)(super.get(indexOf(object)))).copyUserFollowers((UserDTO)object);
                return true;
            }
            return super.add(object);
        }else return super.add(object);
    }

    public boolean addAll( Collection<? extends T> c) {
       for(T o : c) this.add(o);
        return true;
    }
}
