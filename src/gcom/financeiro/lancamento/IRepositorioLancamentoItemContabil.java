package gcom.financeiro.lancamento;

import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * 
 * Title: GCOM
 * 
 * Description: Interface do Reposit�rio de Item Lan�amento Cont�bil
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 23 de Janeiro de 2006
 * @version 1.0
 */
public interface IRepositorioLancamentoItemContabil {

	/**
     * Pesquisa uma cole��o de itens de lan�amento cont�bil
     * @return Cole��o de Item de Lan�amento Cont�bil
     * @exception ErroRepositorioException Erro no hibernate
     */
    public Collection<LancamentoItemContabil> pesquisarLancamentoItemContabil() throws ErroRepositorioException;

}
