package gcom.gerencial.cobranca.bean;

import java.math.BigDecimal;

/**
 * Classe respons�vel por ajudar Gerar Resumo da Pendencia Por Ano
 *
 * @author Fernando Fontelles Filho
 * @date 25/03/2010
 */
public class ResumoPendenciaContasGerencialPorAnoHelper {	
	
	private Integer idGerenciaRegional;
	private Integer idUnidadeNegocio;
	private Integer idElo;
	private Integer idLocalidade;
	private Integer idSetorComercial;
	private Integer codigoSetorComercial;
	private Integer idPerfilImovel;
	private Integer idSituacaoLigacaoAgua;
	private Integer idSituacaoLigacaoEsgoto;
	private Integer idPrincipalCategoriaImovel;
	private Integer idPrincipalSubCategoriaImovel;
	private Integer idEsferaPoder;
	private Integer idHidrometro;
	private Integer idTipoDocumento;
	private Integer anoMesReferenciaDocumento;
	private Integer idReferenciaVencimentoConta;	
	private Integer quantidadeLigacoes = 0;
	private Integer quantidadeDocumentos = 1;
	private BigDecimal valorPendenteAgua = new BigDecimal(0);
	private BigDecimal valorPendenteEsgoto = new BigDecimal(0);
	private BigDecimal valorPendenteDebito = new BigDecimal(0);
	private BigDecimal valorPendenteCredito = new BigDecimal(0);
	private BigDecimal valorPendenteImposto = new BigDecimal(0);
	
	public Integer getAnoMesReferenciaDocumento() {
		return anoMesReferenciaDocumento;
	}
	public void setAnoMesReferenciaDocumento(Integer anoMesReferenciaDocumento) {
		this.anoMesReferenciaDocumento = anoMesReferenciaDocumento;
	}
	public Integer getCodigoSetorComercial() {
		return codigoSetorComercial;
	}
	public void setCodigoSetorComericial(Integer codigoSetorComericial) {
		this.codigoSetorComercial = codigoSetorComericial;
	}
	public Integer getIdElo() {
		return idElo;
	}
	public void setIdElo(Integer idElo) {
		this.idElo = idElo;
	}
	public Integer getIdEsferaPoder() {
		return idEsferaPoder;
	}
	public void setIdEsferaPoder(Integer idEsferaPoder) {
		this.idEsferaPoder = idEsferaPoder;
	}
	public Integer getIdGerenciaRegional() {
		return idGerenciaRegional;
	}
	public void setIdGerenciaRegional(Integer idGerenciaRegional) {
		this.idGerenciaRegional = idGerenciaRegional;
	}
	public Integer getIdLocalidade() {
		return idLocalidade;
	}
	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}
	public Integer getIdPerfilImovel() {
		return idPerfilImovel;
	}
	public void setIdPerfilImovel(Integer idPerfilImovel) {
		this.idPerfilImovel = idPerfilImovel;
	}
	
	public Integer getIdPrincipalCategoriaImovel() {
		return idPrincipalCategoriaImovel;
	}
	public void setIdPrincipalCategoriaImovel(Integer idPrincipalCategoriaImovel) {
		this.idPrincipalCategoriaImovel = idPrincipalCategoriaImovel;
	}
	public Integer getIdPrincipalSubCategoriaImovel() {
		return idPrincipalSubCategoriaImovel;
	}
	public void setIdPrincipalSubCategoriaImovel(Integer idPrincipalSubCategoriaImovel) {
		this.idPrincipalSubCategoriaImovel = idPrincipalSubCategoriaImovel;
	}
	
	public Integer getIdReferenciaVencimentoConta() {
		return idReferenciaVencimentoConta;
	}
	public void setIdReferenciaVencimentoConta(Integer idReferenciaVencimentoConta) {
		this.idReferenciaVencimentoConta = idReferenciaVencimentoConta;
	}
	
	public Integer getIdSetorComercial() {
		return idSetorComercial;
	}
	public void setIdSetorComercial(Integer idSetorComercial) {
		this.idSetorComercial = idSetorComercial;
	}
	public Integer getIdSituacaoLigacaoAgua() {
		return idSituacaoLigacaoAgua;
	}
	public void setIdSituacaoLigacaoAgua(Integer idSituacaoLigacaoAgua) {
		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
	}
	public Integer getIdSituacaoLigacaoEsgoto() {
		return idSituacaoLigacaoEsgoto;
	}
	public void setIdSituacaoLigacaoEsgoto(Integer idSituacaoLigacaoEsgoto) {
		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
	}
	
	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public Integer getIdUnidadeNegocio() {
		return idUnidadeNegocio;
	}
	public void setIdUnidadeNegocio(Integer idUnidadeNegocio) {
		this.idUnidadeNegocio = idUnidadeNegocio;
	}
	
	public Integer getQuantidadeDocumentos() {
		return quantidadeDocumentos;
	}
	public void setQuantidadeDocumentos(Integer quantidadeDocumentos) {
		this.quantidadeDocumentos = quantidadeDocumentos;
	}
	public Integer getQuantidadeLigacoes() {
		return quantidadeLigacoes;
	}
	public void setQuantidadeLigacoes(Integer quantidadeLigacoes) {
		this.quantidadeLigacoes = quantidadeLigacoes;
	}
	public BigDecimal getValorPendenteAgua() {
		return valorPendenteAgua;
	}
	public void setValorPendenteAgua(BigDecimal valorPendenteAgua) {
		if (valorPendenteAgua != null ){
			this.valorPendenteAgua = valorPendenteAgua;	
		}		
	}
	public BigDecimal getValorPendenteCredito() {
		return valorPendenteCredito;
	}
	public void setValorPendenteCredito(BigDecimal valorPendenteCredito) {
		if (valorPendenteCredito != null ){
			this.valorPendenteCredito = valorPendenteCredito;
		}
	}
	public BigDecimal getValorPendenteDebito() {
		return valorPendenteDebito;
	}
	public void setValorPendenteDebito(BigDecimal valorPendenteDebito) {
		if ( valorPendenteDebito != null ){
			this.valorPendenteDebito = valorPendenteDebito;
		}
	}
	public BigDecimal getValorPendenteEsgoto() {
		return valorPendenteEsgoto;
	}
	public void setValorPendenteEsgoto(BigDecimal valorPendenteEsgoto) {
		if ( valorPendenteEsgoto != null){
			this.valorPendenteEsgoto = valorPendenteEsgoto;
		}
	}
	public BigDecimal getValorPendenteImposto() {
		return valorPendenteImposto;
	}
	public void setValorPendenteImposto(BigDecimal valorPendenteImposto) {
		if ( valorPendenteImposto != null ){
			this.valorPendenteImposto = valorPendenteImposto;
		}
	}

	/**
	 * Construtor com os campos iniciais do objeto
	 * 
	 * @param idGerenciaRegional
	 * @param idUnidadeNegocio
	 * @param idElo
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param codigoSetorComericial
	 * @param idPerfilImovel
	 * @param idSituacaoLigacaoAgua
	 * @param idSituacaoLigacaoEsgoto
	 * @param idTipoDocumento
	 * @param anoMesReferenciaDocumento
	 * @param idReferenciaVencimentoConta
	 */
	public ResumoPendenciaContasGerencialPorAnoHelper(
			Integer idGerenciaRegional, //0
			Integer idUnidadeNegocio,  //1
			Integer idElo, //2
			Integer idLocalidade, //3
			Integer idSetorComercial, //4
			Integer codigoSetorComercial, //5
			Integer idPerfilImovel, //6
			Integer idSituacaoLigacaoAgua, //7
			Integer idSituacaoLigacaoEsgoto, //8
			Integer idHidrometro, //9
			Integer idTipoDocumento, //10
			Integer anoMesReferenciaDocumento, //11
			Integer idReferenciaVencimentoConta, //12	
			BigDecimal valorPendenteAgua, //13
			BigDecimal valorPendenteEsgoto, //14
			BigDecimal valorPendenteDebito, //15
			BigDecimal valorPendenteCredito, //16
			BigDecimal valorPendenteImposto //17
			) {			
		this.idGerenciaRegional = idGerenciaRegional;
		this.idUnidadeNegocio = idUnidadeNegocio;
		this.idElo = idElo;
		this.idLocalidade = idLocalidade;
		this.idSetorComercial = idSetorComercial;
		this.codigoSetorComercial = codigoSetorComercial;
		this.idPerfilImovel = idPerfilImovel;
		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
		this.idHidrometro = idHidrometro;
		this.idTipoDocumento = idTipoDocumento;
		this.anoMesReferenciaDocumento = anoMesReferenciaDocumento;
		this.idReferenciaVencimentoConta = idReferenciaVencimentoConta;				
		this.valorPendenteAgua = ( valorPendenteAgua == null ? new BigDecimal(0) : valorPendenteAgua ); 
		this.valorPendenteEsgoto = ( valorPendenteEsgoto == null ? new BigDecimal(0) : valorPendenteEsgoto );
		this.valorPendenteDebito = ( valorPendenteDebito == null ? new BigDecimal(0) : valorPendenteDebito );
		this.valorPendenteCredito = ( valorPendenteCredito == null ? new BigDecimal(0) : valorPendenteCredito );
		this.valorPendenteImposto = ( valorPendenteImposto == null ? new BigDecimal(0) : valorPendenteImposto );
	}
	
	/**
	 * Compara duas properiedades Integereiras, comparando
	 * seus valores para descobrirmos se sao iguais
	 * @param pro1 Primeira propriedade
	 * @param pro2 Segunda propriedade
	 * @return Se iguais, retorna true
	 */
	private boolean propriedadesIguais( Integer pro1, Integer pro2 ){
	  if ( pro2 != null ){
		  if ( !pro2.equals( pro1 ) ){
			  return false;
		  }
	  } else if ( pro1 != null ){
		  return false;
	  }
	  
	  // Se chegou ate aqui quer dizer que as propriedades sao iguais
	  return true;
	}
	
    /**
	* Compara dois objetos levando em consideracao apenas as propriedades
    * que identificam o agrupamento
    * 
    * @param obj Objeto a ser comparado com a instancia deste objeto 
    */
	public boolean equals( Object obj ){
		
		if ( !( obj instanceof ResumoPendenciaContasGerencialPorAnoHelper ) ){
			return false;			
		} else {
			ResumoPendenciaContasGerencialPorAnoHelper resumoTemp = 
				( ResumoPendenciaContasGerencialPorAnoHelper ) obj;
		  
		    // Verificamos se todas as propriedades que identificam o objeto sao iguais
			boolean retorno =  
			  propriedadesIguais( this.idGerenciaRegional, resumoTemp.idGerenciaRegional ) &&
			  propriedadesIguais( this.idUnidadeNegocio, resumoTemp.idUnidadeNegocio ) &&
			  propriedadesIguais( this.idLocalidade, resumoTemp.idLocalidade ) &&
			  propriedadesIguais( this.idElo, resumoTemp.idElo ) &&
			  propriedadesIguais( this.idSetorComercial, resumoTemp.idSetorComercial ) &&
			  propriedadesIguais( this.codigoSetorComercial, resumoTemp.codigoSetorComercial ) &&
			  propriedadesIguais( this.idPerfilImovel, resumoTemp.idPerfilImovel ) &&
			  propriedadesIguais( this.idEsferaPoder, resumoTemp.idEsferaPoder ) &&
			  propriedadesIguais( this.idSituacaoLigacaoAgua, resumoTemp.idSituacaoLigacaoAgua ) &&
			  propriedadesIguais( this.idSituacaoLigacaoEsgoto, resumoTemp.idSituacaoLigacaoEsgoto ) &&
			  propriedadesIguais( this.idPrincipalCategoriaImovel, resumoTemp.idPrincipalCategoriaImovel ) &&
			  propriedadesIguais( this.idPrincipalSubCategoriaImovel, resumoTemp.idPrincipalSubCategoriaImovel ) &&
			  propriedadesIguais( this.idHidrometro, resumoTemp.idHidrometro ) &&
			  propriedadesIguais( this.idTipoDocumento, resumoTemp.idTipoDocumento ) &&		
			  propriedadesIguais( this.anoMesReferenciaDocumento, resumoTemp.anoMesReferenciaDocumento ) &&
			  propriedadesIguais( this.idReferenciaVencimentoConta, resumoTemp.idReferenciaVencimentoConta ) &&
			  propriedadesIguais( this.getIdFaixaValorTotalPendente(), resumoTemp.getIdFaixaValorTotalPendente() );
			
			if ( retorno == true ){
				System.out.println("stop");
			}
			
			return retorno;
			
		}		
	}
	public Integer getIdHidrometro() {
		return idHidrometro;
	}
	public void setIdHidrometro(Integer idHidrometro) {
		this.idHidrometro = idHidrometro;
	}
	public void setCodigoSetorComercial(Integer codigoSetorComercial) {
		this.codigoSetorComercial = codigoSetorComercial;
	}
	public Integer getIdFaixaValorTotalPendente() {
		
		Integer retorno = 0;
		
			BigDecimal valorPendente =	
				this.valorPendenteAgua
				  .add(	this.valorPendenteEsgoto )
				  .add( this.valorPendenteDebito )
				  .add(this.valorPendenteImposto)
				  .subtract( this.valorPendenteCredito );
			
			if ( valorPendente.doubleValue() <= 50 ){
				retorno = 1;
			} else if ( valorPendente.doubleValue() <= 500 ){
				retorno = 2;
			} else if ( valorPendente.doubleValue() <= 1000 ){
				retorno = 3;
			} else {
				retorno = 4;
			} 
		return retorno;
		
	}
	
}
