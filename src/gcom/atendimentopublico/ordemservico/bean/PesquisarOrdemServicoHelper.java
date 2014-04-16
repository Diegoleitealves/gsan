package gcom.atendimentopublico.ordemservico.bean;

import gcom.util.ConstantesSistema;

import java.util.Collection;
import java.util.Date;
import java.util.Set;


/**
 * Classe que ir� auxiliar no formato de entrada da pesquisa de ordem de servico
 *
 * @author Rafael Pinto
 * @date 17/08/2006
 */
public class PesquisarOrdemServicoHelper {
	
	private static final long serialVersionUID = 1L;
	
	private Integer numeroOS = null;
	private Integer numeroRA = null;
	private Integer documentoCobranca = null;
	private short situacaoOrdemServico = ConstantesSistema.NUMERO_NAO_INFORMADO;
	private short situacaoProgramacao = ConstantesSistema.NUMERO_NAO_INFORMADO;
	private Integer[] tipoServicos = null;
	private Integer matriculaImovel = null;
	private Integer codigoCliente = null;
	
	private Integer unidadeGeracao = null;
	private Integer unidadeAtual = null;
	private Integer unidadeSuperior = null;
	
	private Date dataAtendimentoInicial = null;
	private Date dataAtendimentoFinal = null;
	private Date dataGeracaoInicial = null;
	private Date dataGeracaoFinal = null;
	private Date dataProgramacaoInicial = null;
	private Date dataProgramacaoFinal = null;
	private Date dataEncerramentoInicial = null;
	private Date dataEncerramentoFinal = null;
	
	private Integer municipio = null;
	private Integer bairro = null;
	private Integer areaBairro = null;
	private Integer logradouro = null;
	
	private Integer criterioSelecao = null;
	private int origemServico = ConstantesSistema.NUMERO_NAO_INFORMADO;
	
	private short servicoDiagnosticado = ConstantesSistema.NUMERO_NAO_INFORMADO;
	private short servicoAcompanhamento = ConstantesSistema.NUMERO_NAO_INFORMADO;

	private Date dataAtrasoInicial = null;
	private Date dataAtrasoFinal = null;
	private Date dataPrevisaoClienteInicial = null;
	private Date dataPrevisaoClienteFinal = null;
	private Date dataPrevisaoAgenciaInicial = null;
	private Date dataPrevisaoAgenciaFinal = null;
	
	private Integer unidadeLotacao = null;
	
	private Set colecaoIdsOS = null;
	
	private int numeroPaginasPesquisa = ConstantesSistema.NUMERO_NAO_INFORMADO;
	
	private Collection<Integer> idsRa = null;
	private Collection<Integer> idsUnidadesAtuais = null;
	private Collection<Integer> colecaoAtendimentoMotivoEncerramento = null;
	private Collection<Integer> colecaoPerfilImovel = null;
	
	private Short indicadorTerceirizado = null;
	private Short indicadorPavimento = null;
	private Short indicadorVistoria = null;
	private Short indicadorFiscalizacao = null;
	private String origemOrdemServico;
	
	private Integer idProjeto;
	
	public String getOrigemOrdemServico() {
		return origemOrdemServico;
	}

	public void setOrigemOrdemServico(String origemOrdemServico) {
		this.origemOrdemServico = origemOrdemServico;
	}

	public PesquisarOrdemServicoHelper() {
	}

	public Integer getAreaBairro() {
		return areaBairro;
	}

	public void setAreaBairro(Integer areaBairro) {
		this.areaBairro = areaBairro;
	}

	public Integer getBairro() {
		return bairro;
	}

	public void setBairro(Integer bairro) {
		this.bairro = bairro;
	}

	public Integer getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Integer codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Date getDataAtendimentoFinal() {
		return dataAtendimentoFinal;
	}

	public void setDataAtendimentoFinal(Date dataAtendimentoFinal) {
		this.dataAtendimentoFinal = dataAtendimentoFinal;
	}

	public Date getDataAtendimentoInicial() {
		return dataAtendimentoInicial;
	}

	public void setDataAtendimentoInicial(Date dataAtendimentoInicial) {
		this.dataAtendimentoInicial = dataAtendimentoInicial;
	}

	public Date getDataEncerramentoFinal() {
		return dataEncerramentoFinal;
	}

	public void setDataEncerramentoFinal(Date dataEncerramentoFinal) {
		this.dataEncerramentoFinal = dataEncerramentoFinal;
	}

	public Date getDataEncerramentoInicial() {
		return dataEncerramentoInicial;
	}

	public void setDataEncerramentoInicial(Date dataEncerramentoInicial) {
		this.dataEncerramentoInicial = dataEncerramentoInicial;
	}

	public Date getDataGeracaoFinal() {
		return dataGeracaoFinal;
	}

	public void setDataGeracaoFinal(Date dataGeracaoFinal) {
		this.dataGeracaoFinal = dataGeracaoFinal;
	}

	public Date getDataGeracaoInicial() {
		return dataGeracaoInicial;
	}

	public void setDataGeracaoInicial(Date dataGeracaoInicial) {
		this.dataGeracaoInicial = dataGeracaoInicial;
	}

	public Date getDataProgramacaoFinal() {
		return dataProgramacaoFinal;
	}

	public void setDataProgramacaoFinal(Date dataProgramacaoFinal) {
		this.dataProgramacaoFinal = dataProgramacaoFinal;
	}

	public Date getDataProgramacaoInicial() {
		return dataProgramacaoInicial;
	}

	public void setDataProgramacaoInicial(Date dataProgramacaoInicial) {
		this.dataProgramacaoInicial = dataProgramacaoInicial;
	}

	public Integer getDocumentoCobranca() {
		return documentoCobranca;
	}

	public void setDocumentoCobranca(Integer documentoCobranca) {
		this.documentoCobranca = documentoCobranca;
	}

	public Integer[] getTipoServicos() {
		return tipoServicos;
	}

	public void setTipoServicos(Integer[] tipoServicos) {
		this.tipoServicos = tipoServicos;
	}

	public Integer getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Integer logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getMatriculaImovel() {
		return matriculaImovel;
	}

	public void setMatriculaImovel(Integer matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
	}

	public Integer getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Integer municipio) {
		this.municipio = municipio;
	}

	public Integer getNumeroRA() {
		return numeroRA;
	}

	public void setNumeroRA(Integer numeroRA) {
		this.numeroRA = numeroRA;
	}

	public short getSituacaoOrdemServico() {
		return situacaoOrdemServico;
	}

	public void setSituacaoOrdemServico(short situacaoOrdemServico) {
		this.situacaoOrdemServico = situacaoOrdemServico;
	}

	public short getSituacaoProgramacao() {
		return situacaoProgramacao;
	}

	public void setSituacaoProgramacao(short situacaoProgramacao) {
		this.situacaoProgramacao = situacaoProgramacao;
	}

	public Integer getUnidadeAtual() {
		return unidadeAtual;
	}

	public void setUnidadeAtual(Integer unidadeAtual) {
		this.unidadeAtual = unidadeAtual;
	}

	public Integer getUnidadeGeracao() {
		return unidadeGeracao;
	}

	public void setUnidadeGeracao(Integer unidadeGeracao) {
		this.unidadeGeracao = unidadeGeracao;
	}

	public Integer getUnidadeSuperior() {
		return unidadeSuperior;
	}

	public void setUnidadeSuperior(Integer unidadeSuperior) {
		this.unidadeSuperior = unidadeSuperior;
	}

	public Set getColecaoIdsOS() {
		return colecaoIdsOS;
	}

	public void setColecaoIdsOS(Set colecaoIdsOS) {
		this.colecaoIdsOS = colecaoIdsOS;
	}

	public int getNumeroPaginasPesquisa() {
		return numeroPaginasPesquisa;
	}

	public void setNumeroPaginasPesquisa(int numeroPaginasPesquisa) {
		this.numeroPaginasPesquisa = numeroPaginasPesquisa;
	}

	public Integer getCriterioSelecao() {
		return criterioSelecao;
	}

	public void setCriterioSelecao(Integer criterioSelecao) {
		this.criterioSelecao = criterioSelecao;
	}


	public Date getDataPrevisaoAgenciaFinal() {
		return dataPrevisaoAgenciaFinal;
	}

	public void setDataPrevisaoAgenciaFinal(Date dataPrevisaoAgenciaFinal) {
		this.dataPrevisaoAgenciaFinal = dataPrevisaoAgenciaFinal;
	}

	public Date getDataPrevisaoAgenciaInicial() {
		return dataPrevisaoAgenciaInicial;
	}

	public void setDataPrevisaoAgenciaInicial(Date dataPrevisaoAgenciaInicial) {
		this.dataPrevisaoAgenciaInicial = dataPrevisaoAgenciaInicial;
	}

	public Date getDataPrevisaoClienteFinal() {
		return dataPrevisaoClienteFinal;
	}

	public void setDataPrevisaoClienteFinal(Date dataPrevisaoClienteFinal) {
		this.dataPrevisaoClienteFinal = dataPrevisaoClienteFinal;
	}

	public Date getDataPrevisaoClienteInicial() {
		return dataPrevisaoClienteInicial;
	}

	public void setDataPrevisaoClienteInicial(Date dataPrevisaoClienteInicial) {
		this.dataPrevisaoClienteInicial = dataPrevisaoClienteInicial;
	}

	public short getServicoAcompanhamento() {
		return servicoAcompanhamento;
	}

	public void setServicoAcompanhamento(short servicoAcompanhamento) {
		this.servicoAcompanhamento = servicoAcompanhamento;
	}

	public short getServicoDiagnosticado() {
		return servicoDiagnosticado;
	}

	public void setServicoDiagnosticado(short servicoDiagnosticado) {
		this.servicoDiagnosticado = servicoDiagnosticado;
	}

	public Integer getUnidadeLotacao() {
		return unidadeLotacao;
	}

	public void setUnidadeLotacao(Integer unidadeLotacao) {
		this.unidadeLotacao = unidadeLotacao;
	}

	public Date getDataAtrasoFinal() {
		return dataAtrasoFinal;
	}

	public void setDataAtrasoFinal(Date dataAtrasoFinal) {
		this.dataAtrasoFinal = dataAtrasoFinal;
	}

	public Date getDataAtrasoInicial() {
		return dataAtrasoInicial;
	}

	public void setDataAtrasoInicial(Date dataAtrasoInicial) {
		this.dataAtrasoInicial = dataAtrasoInicial;
	}

	public int getOrigemServico() {
		return origemServico;
	}

	public void setOrigemServico(int origemServico) {
		this.origemServico = origemServico;
	}

	public Integer getNumeroOS() {
		return numeroOS;
	}

	public void setNumeroOS(Integer numeroOS) {
		this.numeroOS = numeroOS;
	}

	public Collection<Integer> getIdsRa() {
		return idsRa;
	}

	public void setIdsRa(Collection<Integer> idsRa) {
		this.idsRa = idsRa;
	}

	public Short getIndicadorFiscalizacao() {
		return indicadorFiscalizacao;
	}

	public void setIndicadorFiscalizacao(Short indicadorFiscalizacao) {
		this.indicadorFiscalizacao = indicadorFiscalizacao;
	}

	public Short getIndicadorPavimento() {
		return indicadorPavimento;
	}

	public void setIndicadorPavimento(Short indicadorPavimento) {
		this.indicadorPavimento = indicadorPavimento;
	}

	public Short getIndicadorTerceirizado() {
		return indicadorTerceirizado;
	}

	public void setIndicadorTerceirizado(Short indicadorTerceirizado) {
		this.indicadorTerceirizado = indicadorTerceirizado;
	}

	public Short getIndicadorVistoria() {
		return indicadorVistoria;
	}

	public void setIndicadorVistoria(Short indicadorVistoria) {
		this.indicadorVistoria = indicadorVistoria;
	}

	/**
	 * @return Retorna o campo idsUnidadesAtuais.
	 */
	public Collection<Integer> getIdsUnidadesAtuais() {
		return idsUnidadesAtuais;
	}

	/**
	 * @param idsUnidadesAtuais O idsUnidadesAtuais a ser setado.
	 */
	public void setIdsUnidadesAtuais(Collection<Integer> idsUnidadesAtuais) {
		this.idsUnidadesAtuais = idsUnidadesAtuais;
	}

	/**
	 * @return Retorna o campo colecaoAtendimentoMotivoEncerramento.
	 */
	public Collection<Integer> getColecaoAtendimentoMotivoEncerramento() {
		return colecaoAtendimentoMotivoEncerramento;
	}

	/**
	 * @param colecaoAtendimentoMotivoEncerramento O colecaoAtendimentoMotivoEncerramento a ser setado.
	 */
	public void setColecaoAtendimentoMotivoEncerramento(
			Collection<Integer> colecaoAtendimentoMotivoEncerramento) {
		this.colecaoAtendimentoMotivoEncerramento = colecaoAtendimentoMotivoEncerramento;
	}

	public Integer getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}

	public Collection<Integer> getColecaoPerfilImovel() {
		return colecaoPerfilImovel;
	}

	public void setColecaoPerfilImovel(Collection<Integer> colecaoPerfilImovel) {
		this.colecaoPerfilImovel = colecaoPerfilImovel;
	}
	
}
