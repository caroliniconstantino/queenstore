package com.org.queenstore.enums;

public enum MethodPayment {
    TICKET(0, "Ticket"),
    CREDIT_CARD(1, "Credit Card"),
    DEBIT_CARD(2, "Debit Cartd"),
    PIX(3, "Pix");

    private int code;
    private String description;

    private MethodPayment(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MethodPayment toEnum(Integer code){
        if(code == null ){return null;}
        for(MethodPayment typePayment : MethodPayment.values()){
            if(code.equals(typePayment.getCode())){return typePayment;};
        }
    throw new IllegalArgumentException("Tipo de pagamento inválido: " + code);
    }
}
