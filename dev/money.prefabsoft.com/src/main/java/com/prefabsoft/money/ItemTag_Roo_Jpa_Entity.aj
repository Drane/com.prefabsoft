// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.prefabsoft.money;

import com.prefabsoft.money.ItemTag;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect ItemTag_Roo_Jpa_Entity {
    
    declare @type: ItemTag: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ItemTag.id;
    
    @Version
    @Column(name = "version")
    private Integer ItemTag.version;
    
    public Long ItemTag.getId() {
        return this.id;
    }
    
    public void ItemTag.setId(Long id) {
        this.id = id;
    }
    
    public Integer ItemTag.getVersion() {
        return this.version;
    }
    
    public void ItemTag.setVersion(Integer version) {
        this.version = version;
    }
    
}
