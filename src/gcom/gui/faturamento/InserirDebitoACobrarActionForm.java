package gcom.gui.faturamento;

import org.apache.struts.action.*;
import javax.servlet.http.*;

/**
 * Inserir D�btio a Cobrar do Imovel
 * @author Rafael Santos
 * @since 21/15/2005
 * [UC0183] Inserir D�bito A Cobrar
 */
public class InserirDebitoACobrarActionForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private String idImovel;
	private String inscricaoImovelInformada;
	private String inscricaoImovel;
	private String nomeCliente;
	private String situacaoAgua;
	private String situacaoEsgoto;
	private String codigoImovel;
	private String idTipoDebito;
	private String descricaoTipoDebito;
	private String registroAtendimento;
	private String nomeRegistroAtendimento;
	private String ordemServico;
	private String nomeOrdemServico;
	private String numeroPrestacoes;
	private String valorPrestacao;
	private String valorEntrada;
	private String valorTotalServico;
	private String valorTotalServicoAParcelar;
	private String taxaJurosFinanciamento;
	private String valorJuros;
	private String percentualAbatimento;
	
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    /**@todo: finish this method, this is just the skeleton.*/
    return null;
  }
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
  }
/**
 * @return Returns the codigoImovel.
 */
public String getCodigoImovel() {
	return codigoImovel;
}
/**
 * @param codigoImovel The codigoImovel to set.
 */
public void setCodigoImovel(String codigoImovel) {
	this.codigoImovel = codigoImovel;
}
/**
 * @return Returns the numeroPrestacoes.
 */
public String getNumeroPrestacoes() {
	return numeroPrestacoes;
}
/**
 * @param numeroPrestacoes The numeroPrestacoes to set.
 */
public void setNumeroPrestacoes(String numeroPrestacoes) {
	this.numeroPrestacoes = numeroPrestacoes;
}
/**
 * @return Returns the ordemServico.
 */
public String getOrdemServico() {
	return ordemServico;
}
/**
 * @param ordemServico The ordemServico to set.
 */
public void setOrdemServico(String ordemServico) {
	this.ordemServico = ordemServico;
}
/**
 * @return Returns the percentualAbatimento.
 */
public String getPercentualAbatimento() {
	return percentualAbatimento;
}
/**
 * @param percentualAbatimento The percentualAbatimento to set.
 */
public void setPercentualAbatimento(String percentualAbatimento) {
	this.percentualAbatimento = percentualAbatimento;
}
/**
 * @return Returns the registroAtendimento.
 */
public String getRegistroAtendimento() {
	return registroAtendimento;
}
/**
 * @param registroAtendimento The registroAtendimento to set.
 */
public void setRegistroAtendimento(String registroAtendimento) {
	this.registroAtendimento = registroAtendimento;
}
/**
 * @return Returns the taxaJurosFinanciamento.
 */
public String getTaxaJurosFinanciamento() {
	return taxaJurosFinanciamento;
}
/**
 * @param taxaJurosFinanciamento The taxaJurosFinanciamento to set.
 */
public void setTaxaJurosFinanciamento(String taxaJurosFinanciamento) {
	this.taxaJurosFinanciamento = taxaJurosFinanciamento;
}
/**
 * @return Returns the valorEntrada.
 */
public String getValorEntrada() {
	return valorEntrada;
}
/**
 * @param valorEntrada The valorEntrada to set.
 */
public void setValorEntrada(String valorEntrada) {
	this.valorEntrada = valorEntrada;
}
/**
 * @return Returns the valorJuros.
 */
public String getValorJuros() {
	return valorJuros;
}
/**
 * @param valorJuros The valorJuros to set.
 */
public void setValorJuros(String valorJuros) {
	this.valorJuros = valorJuros;
}
/**
 * @return Returns the valorPrestacao.
 */
public String getValorPrestacao() {
	return valorPrestacao;
}
/**
 * @param valorPrestacao The valorPrestacao to set.
 */
public void setValorPrestacao(String valorPrestacao) {
	this.valorPrestacao = valorPrestacao;
}
/**
 * @return Returns the valorTotalSercioAParcelar.
 */
public String getValorTotalServicoAParcelar() {
	return valorTotalServicoAParcelar;
}
/**
 * @param valorTotalSercioAParcelar The valorTotalSercioAParcelar to set.
 */
public void setvalorTotalServicoAParcelar(String valorTotalServicoAParcelar) {
	this.valorTotalServicoAParcelar = valorTotalServicoAParcelar;
}
/**
 * @return Returns the valorTotalServico.
 */
public String getValorTotalServico() {
	return valorTotalServico;
}
/**
 * @param valorTotalServico The valorTotalServico to set.
 */
public void setValorTotalServico(String valorTotalServico) {
	this.valorTotalServico = valorTotalServico;
}
/**
 * @return Returns the inscricaoImovel.
 */
public String getInscricaoImovel() {
	return inscricaoImovel;
}
/**
 * @param inscricaoImovel The inscricaoImovel to set.
 */
public void setInscricaoImovel(String inscricaoImovel) {
	this.inscricaoImovel = inscricaoImovel;
}
/**
 * @return Returns the nomeCliente.
 */
public String getNomeCliente() {
	return nomeCliente;
}
/**
 * @param nomeCliente The nomeCliente to set.
 */
public void setNomeCliente(String nomeCliente) {
	this.nomeCliente = nomeCliente;
}
/**
 * @return Returns the situacaoAgua.
 */
public String getSituacaoAgua() {
	return situacaoAgua;
}
/**
 * @param situacaoAgua The situacaoAgua to set.
 */
public void setSituacaoAgua(String situacaoAgua) {
	this.situacaoAgua = situacaoAgua;
}
/**
 * @return Returns the situacaoEsgoto.
 */
public String getSituacaoEsgoto() {
	return situacaoEsgoto;
}
/**
 * @param situacaoEsgoto The situacaoEsgoto to set.
 */
public void setSituacaoEsgoto(String situacaoEsgoto) {
	this.situacaoEsgoto = situacaoEsgoto;
}
/**
 * @param valorTotalServicoAParcelar The valorTotalServicoAParcelar to set.
 */
public void setValorTotalServicoAParcelar(String valorTotalServicoAParcelar) {
	this.valorTotalServicoAParcelar = valorTotalServicoAParcelar;
}
/**
 * @return Retorna o campo descricaoTipoDebito.
 */
public String getDescricaoTipoDebito() {
	return descricaoTipoDebito;
}
/**
 * @param descricaoTipoDebito O descricaoTipoDebito a ser setado.
 */
public void setDescricaoTipoDebito(String descricaoTipoDebito) {
	this.descricaoTipoDebito = descricaoTipoDebito;
}
/**
 * @return Retorna o campo idTipoDebito.
 */
public String getIdTipoDebito() {
	return idTipoDebito;
}
/**
 * @param idTipoDebito O idTipoDebito a ser setado.
 */
public void setIdTipoDebito(String idTipoDebito) {
	this.idTipoDebito = idTipoDebito;
}
/**
 * @return Retorna o campo nomeOrdemServico.
 */
public String getNomeOrdemServico() {
	return nomeOrdemServico;
}
/**
 * @param nomeOrdemServico O nomeOrdemServico a ser setado.
 */
public void setNomeOrdemServico(String nomeOrdemServico) {
	this.nomeOrdemServico = nomeOrdemServico;
}
/**
 * @return Retorna o campo nomeRegistroAtendimento.
 */
public String getNomeRegistroAtendimento() {
	return nomeRegistroAtendimento;
}
/**
 * @param nomeRegistroAtendimento O nomeRegistroAtendimento a ser setado.
 */
public void setNomeRegistroAtendimento(String nomeRegistroAtendimento) {
	this.nomeRegistroAtendimento = nomeRegistroAtendimento;
}
/**
 * @return Retorna o campo idImovel.
 */
public String getIdImovel() {
	return idImovel;
}
/**
 * @param idImovel O idImovel a ser setado.
 */
public void setIdImovel(String idImovel) {
	this.idImovel = idImovel;
}
/**
 * @return Retorna o campo inscricaoImovelInformada.
 */
public String getInscricaoImovelInformada() {
	return inscricaoImovelInformada;
}
/**
 * @param inscricaoImovelInformada O inscricaoImovelInformada a ser setado.
 */
public void setInscricaoImovelInformada(String inscricaoImovelInformada) {
	this.inscricaoImovelInformada = inscricaoImovelInformada;
}
  
}
