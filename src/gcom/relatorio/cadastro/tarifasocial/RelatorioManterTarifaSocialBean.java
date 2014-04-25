package gcom.relatorio.cadastro.tarifasocial;

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

public class RelatorioManterTarifaSocialBean implements RelatorioBean {
    private String codigo;

    private String descricao;

    private String indicadorValidade;

    private String maximoMeses;

    private String indicadorUso;

    /**
     * Construtor da classe RelatorioManterTarifaSocialBean
     * 
     * @param codigo
     *            Descri��o do par�metro
     * @param descricao
     *            Descri��o do par�metro
     * @param indicadorValidade
     *            Descri��o do par�metro
     * @param maximoMeses
     *            Descri��o do par�metro
     * @param indicadorUso
     *            Descri��o do par�metro
     */
    public RelatorioManterTarifaSocialBean(String codigo, String descricao,
            String indicadorValidade, String maximoMeses, String indicadorUso) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.indicadorValidade = indicadorValidade;
        this.maximoMeses = maximoMeses;
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
     * Retorna o valor de descricao
     * 
     * @return O valor de descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o valor de indicadorValidade
     * 
     * @return O valor de indicadorValidade
     */
    public String getIndicadorValidade() {
        return indicadorValidade;
    }

    /**
     * Retorna o valor de maximoMeses
     * 
     * @return O valor de maximoMeses
     */
    public String getMaximoMeses() {
        return maximoMeses;
    }

    /**
     * Seta o valor de maximoMeses
     * 
     * @param maximoMeses
     *            O novo valor de maximoMeses
     */
    public void setMaximoMeses(String maximoMeses) {
        this.maximoMeses = maximoMeses;
    }

    /**
     * Seta o valor de indicadorValidade
     * 
     * @param indicadorValidade
     *            O novo valor de indicadorValidade
     */
    public void setIndicadorValidade(String indicadorValidade) {
        this.indicadorValidade = indicadorValidade;
    }

    /**
     * Seta o valor de descricao
     * 
     * @param descricao
     *            O novo valor de descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
     * Retorna o valor de indicadorUso
     * 
     * @return O valor de indicadorUso
     */
    public String getIndicadorUso() {
        return indicadorUso;
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

}
