package com.ueda.pedido.error;

public class Error {
    private String campo;
    private String erro;

    public Error(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
