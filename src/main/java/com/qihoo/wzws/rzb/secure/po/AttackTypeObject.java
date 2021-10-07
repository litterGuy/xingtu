
package com.qihoo.wzws.rzb.secure.po;


import java.util.List;


public class AttackTypeObject {
    private String typeId;
    private String typeName;
    private List<AttackEntity> typeList;


    public String getTypeName() {

        return this.typeName;

    }


    public String getTypeId() {

        return this.typeId;

    }


    public List<AttackEntity> getTypeList() {

        return this.typeList;

    }


    public void setTypeName(String typeName) {

        this.typeName = typeName;

    }


    public void setTypeId(String typeId) {

        this.typeId = typeId;

    }


    public void setTypeList(List<AttackEntity> typeList) {

        this.typeList = typeList;

    }

}