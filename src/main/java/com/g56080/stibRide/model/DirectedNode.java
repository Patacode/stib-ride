package com.g56080.stibRide.model;

public class DirectedNode<T>{

    private T value;
    private DirectedNode<T> next;

    public DirectedNode(T value){
        this(value, null);
    }

    public DirectedNode(T value, DirectedNode<T> next){
        this.value = value;
        this.next = next;
    }

    public T value(){
        return value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public DirectedNode<T> next(){
        return next;
    }

    public void setNext(DirectedNode<T> next){
        this.next = next;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof DirectedNode<?> dn){
            return dn.value.equals(value) && dn.next == next;
        }

        return false;
    }

    @Override
    public String toString(){
        return String.format("Node[%s, %s]", value.toString(), next == null ? "null" : next.value().toString());
    }
}

