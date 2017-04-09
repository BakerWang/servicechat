package com.xzg.domain;

 
 import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**通过annotation来映射hibernate实体的,基于annotation的hibernate主键标识为@Id, 
其生成规则由@GeneratedValue设定的.这里的@id和@GeneratedValue都是JPA的标准用法, */
 @Entity
 @Table(name="t_role")
 public class Role {
     @Id
     @GeneratedValue
     private Long id;
 
     private String roleName;
 
     @ManyToMany(cascade = CascadeType.ALL)//形成和user主外建关联
     private List<User> users = new ArrayList<User>();
 
     public Long getId() {
        return id;
     }
     public void setId(Long id) {
         this.id = id;
     }
 
     public String getRoleName() {
         return roleName;
    }
 
     public void setRoleName(String roleName) {
         this.roleName = roleName;
     }
 
    public List<User> getUsers() {
         return users;
    }
 
     public void setUsers(List<User> users) {
        this.users = users;
     }
 }