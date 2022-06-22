package com.g56080.stibRide.model;

public record Message(String message, MessageType type, boolean error){

    public Message(String message){
        this(message, MessageType.NO_TYPE, false);
    }

    public Message(String message, MessageType type){
        this(message, type, false);
    }

    public Message(String message, boolean error){
        this(message, MessageType.NO_TYPE, error);
    }

    @Override
    public int hashCode(){
        return message.hashCode() + Boolean.valueOf(error).hashCode() + type.hashCode();
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Message msg){
            return msg.message.equals(message) && msg.error == error && msg.type == type;
        }

        return false;
    }
}

