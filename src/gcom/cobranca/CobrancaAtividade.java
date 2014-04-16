package gcom.cobranca;

import gcom.batch.Processo;
import gcom.faturamento.conta.FiltroContaMensagem;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CobrancaAtividade extends ObjetoTransacao {
	
	private static final long serialVersionUID = 1L;

	// Constantes de atividades de cobran�a >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public final static Integer SIMULAR = new Integer(1);
	public final static Integer EMITIR = new Integer(2);
	public final static Integer ENCERRAR = new Integer(3);
	public final static Integer ENCERRAR_OS = new Integer(5);
	public final static Integer ATIVO_CRONOGRAMA = new Integer(1);
	public final static Short INDICADOR_OBRIGATORIEDADE_ATIVO = 1;
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricaoCobrancaAtividade;

	/** nullable persistent field */
	private Short indicadorComando;

	/** nullable persistent field */
	private Short indicadorExecucao;

	/** nullable persistent field */
	private Short indicadorCronograma;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Short ordemRealizacao;

	private Processo processo;
	
	private Short indicadorObrigatoriedade;

	/** persistent field */
	private gcom.cobranca.CobrancaAtividade cobrancaAtividadePredecessora;

	private Integer numeroDiasExecucao;
	
	private CobrancaAcao cobrancaAcao;
	
	//criado para exibi��o na tela de Inserir Cronograma de Cobran�a
	private Date dataPrevista;
	
	public String[] retornaCamposChavePrimaria() {
		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/** full constructor */
	public CobrancaAtividade(String descricaoCobrancaAtividade,
			Short indicadorComando, Short indicadorExecucao,
			Short indicadorUso, Date ultimaAlteracao,
			gcom.cobranca.CobrancaAtividade cobrancaAtividadePredecessora) {
		this.descricaoCobrancaAtividade = descricaoCobrancaAtividade;
		this.indicadorComando = indicadorComando;
		this.indicadorExecucao = indicadorExecucao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.cobrancaAtividadePredecessora = cobrancaAtividadePredecessora;
	}

	/** default constructor */
	public CobrancaAtividade() {
	}

	/** minimal constructor */
	public CobrancaAtividade(
			gcom.cobranca.CobrancaAtividade cobrancaAtividadePredecessora) {
		this.cobrancaAtividadePredecessora = cobrancaAtividadePredecessora;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricaoCobrancaAtividade() {
		return this.descricaoCobrancaAtividade;
	}

	public void setDescricaoCobrancaAtividade(String descricaoCobrancaAtividade) {
		this.descricaoCobrancaAtividade = descricaoCobrancaAtividade;
	}

	public Short getIndicadorComando() {
		return this.indicadorComando;
	}

	public void setIndicadorComando(Short indicadorComando) {
		this.indicadorComando = indicadorComando;
	}

	public Short getIndicadorExecucao() {
		return this.indicadorExecucao;
	}

	public void setIndicadorExecucao(Short indicadorExecucao) {
		this.indicadorExecucao = indicadorExecucao;
	}

	public Short getIndicadorUso() {
		return this.indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso) {
		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao() {
		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.cobranca.CobrancaAtividade getCobrancaAtividadePredecessora() {
		return this.cobrancaAtividadePredecessora;
	}

	public void setCobrancaAtividadePredecessora(
			gcom.cobranca.CobrancaAtividade cobrancaAtividadePredecessora) {
		this.cobrancaAtividadePredecessora = cobrancaAtividadePredecessora;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Short getOrdemRealizacao() {
		return ordemRealizacao;
	}

	public void setOrdemRealizacao(Short ordemRealizacao) {
		this.ordemRealizacao = ordemRealizacao;
	}

	public Short getIndicadorCronograma() {
		return indicadorCronograma;
	}

	public void setIndicadorCronograma(Short indicadorCronograma) {
		this.indicadorCronograma = indicadorCronograma;
	}

	/**
	 * @return Retorna o campo processo.
	 */
	public Processo getProcesso() {
		return processo;
	}

	/**
	 * @param processo
	 *            O processo a ser setado.
	 */
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Short getIndicadorObrigatoriedade() {
		return indicadorObrigatoriedade;
	}

	public void setIndicadorObrigatoriedade(Short indicadorObrigatoriedade) {
		this.indicadorObrigatoriedade = indicadorObrigatoriedade;
	}
	
	public Filtro retornaFiltro() {
		
		FiltroCobrancaAtividade filtroCobrancaAtividade = new FiltroCobrancaAtividade();
		filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroContaMensagem.ID,this.getId()));
		filtroCobrancaAtividade.adicionarCaminhoParaCarregamentoEntidade("processo");
		
		
		return filtroCobrancaAtividade;
	}

	public Date getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public CobrancaAcao getCobrancaAcao() {
		return cobrancaAcao;
	}

	public void setCobrancaAcao(CobrancaAcao cobrancaAcao) {
		this.cobrancaAcao = cobrancaAcao;
	}

	public Integer getNumeroDiasExecucao() {
		return numeroDiasExecucao;
	}

	public void setNumeroDiasExecucao(Integer numeroDiasExecucao) {
		this.numeroDiasExecucao = numeroDiasExecucao;
	}

	public String getDataPrevistaFormatada() {
		
		String retorno = "";
		
		if(dataPrevista != null){
			retorno = Util.formatarData(dataPrevista);
		}
		
		return retorno;
	}
}
