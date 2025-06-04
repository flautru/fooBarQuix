package com.fabien.kata.fooBarQuix.model;

public record FooBarQuixResult (int number, String result) {

    @Override
    public String toString(){
        return number + " \"" + result + "\"";
    }


}
