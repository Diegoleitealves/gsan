package gcom.relatorio.cadastro.localidade;

import gcom.relatorio.RelatorioBean;

/**
 * <p>
 * 
 * Title: GCOM
 * </p>
 * <p>
 * 
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * 
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rafael Corr�a
 * @version 1.0
 */

public class RelatorioManterSetorComercialBean implements RelatorioBean {
    private String localidade;

    private String codigo;

    private String nome;

    private String municipio;

    private String indicadorUso;

    /**
     * Construtor da classe RelatorioManterSetorComercialBean
     * 
     * @param localidade
     *            Descri��o do par�metro
     * @param codigo
     *            Descri��o do par�metro
     * @param nome
     *            Descri��o do par�metro
     * @param municipio
     *            Descri��o do par�metro
     * @param indicadorUso
     *            Descri��o do par�metro
     */
    public RelatorioManterSetorComercialBean(String localidade, String codigo,
            String nome, String municipio, String indicadorUso) {
        this.localidade = localidade;
        this.codigo = codigo;
        this.nome = nome;
        this.municipio = municipio;
        this.indicadorUso = indicadorUso;
    }

    /**
     * Retorna o valor de codigo
     * 
     * @return O valor de codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Seta o valor de codigo
     * 
     * @param codigo
     *            O novo valor de codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Seta o valor de indicadorUso
     * 
     * @param indicadorUso
     *            O novo valor de indicadorUso
     */
    public void setIndicadorUso(String indicadorUso) {
        this.indicadorUso = indicadorUso;
    }

    /**
     * Retorna o valor de indicadorUso
     * 
     * @return O valor de indicadorUso
     */
    public String getIndicadorUso() {
        return indicadorUso;
    }

    /**
     * Retorna o valor de localidade
     * 
     * @return O valor de localidade
     */
    public String getLocalidade() {
        return localidade;
    }

    /**
     * Retorna o valor de municipio
     * 
     * @return O valor de municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Retorna o valor de nome
     * 
     * @return O valor de nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Seta o valor de localidade
     * 
     * @param localidade
     *            O novo valor de localidade
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Seta o valor de municipio
     * 
     * @param municipio
     *            O novo valor de municipio
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Seta o valor de nome
     * 
     * @param nome
     *            O novo valor de nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

}
