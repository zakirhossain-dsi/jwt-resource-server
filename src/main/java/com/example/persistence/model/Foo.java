package com.example.persistence.model;

public class Foo {

    private Long id;
    private String name;

    protected Foo() {    }

    public Foo(String name) {
        this.name = name;
    }

    public Foo(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Foo [id=" + id + ", name=" + name + "]";
    }
}
