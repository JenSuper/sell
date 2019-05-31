package com.jensuper.demo;

import lombok.Data;

@Data
public class vo {

    private Integer id;
    private String name;
    private Integer pid;
    private Integer level;


    public vo(Integer id, String name, Integer pid, Integer level) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
