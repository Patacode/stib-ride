package com.g56080.stibRide.util;

public class Pair<L, R>{
    
    private final L left_value;
    private final R right_value;

    public Pair(L left, R right){
        left_value = left;
        right_value = right;
    }

    public L left(){
        return left_value;
    }

    public R right(){
        return right_value;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Pair<?, ?> kp){
            return kp.left_value.equals(left_value) 
                && kp.right_value.equals(right_value);
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Math.abs(left_value.hashCode() + right_value.hashCode());
    }
}

