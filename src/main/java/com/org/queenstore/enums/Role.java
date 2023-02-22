package com.org.queenstore.enums;

public enum Role {

    CLIENT(0, "Cliente"),
    SELLER(1, "Vendedor");

    private int code;
    private String description;

    private Role(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }

    public static Role toEnum(Integer code){
        if (code == null)return null;
        for (Role typeRole : Role.values()){
            if (code.equals(typeRole.getCode()))return typeRole;
        }
        throw new IllegalArgumentException("Função inválida" + code);
    }
}
