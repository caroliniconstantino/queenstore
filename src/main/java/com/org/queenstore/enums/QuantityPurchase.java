package com.org.queenstore.enums;

public enum QuantityPurchase {
    TICKET(0, "Ticket"),
    CREDIT_CARD(1, "Credit Card"),
    DEBIT_CARD(2, "Debit Cartd"),
    PIX(3, "Pix");

    private int code;
    private String description;

    private QuantityPurchase(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static QuantityPurchase toEnum(Integer code){
        if(code == null ){return null;}
        for(QuantityPurchase typePayment : QuantityPurchase.values()){
            if(code.equals(typePayment.getCode())){return typePayment;};
        }
    throw new IllegalArgumentException("Tipo de pagamento inválido: " + code);
    }
}
