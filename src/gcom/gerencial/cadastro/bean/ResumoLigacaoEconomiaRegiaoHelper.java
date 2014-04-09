package gcom.gerencial.cadastro.bean;

/**
 * Classe responsavel pelo caso de uso UC0275, na vis�o por Regi�o * 
 *
 * @author Ivan S�rgio
 * @date 16/04/2007
 */

public class ResumoLigacaoEconomiaRegiaoHelper {
	
	private Integer idRegiao;
	private Integer idMicrorregiao;
	private Integer idMunicipio;
	private Integer idBairro;
	private Integer idPerfilImovel;
	private Integer idSituacaoLigacaoAgua;
	private Integer idSituacaoLigacaoEsgoto;
	private Integer idPrincipalCategoriaImovel;
	private Integer idPrincipalSubCategoriaImovel;
	private Integer idEsferaCliente;
	private Integer idTipoClienteResponsavel;
	private Integer idPerfilLigacaoAgua;
	private Integer idPerfilLigacaoEsgoto;
	private Integer idIndicadorHidrometro;
	private Integer idIndicadorHidrometroPoco;
	private Integer idIndicadorVolumeMinimoAguaFixado;
	private Integer idIndicadorVolumeMinimoEsgotoFixado;
	private Integer idIndicadorPoco;	
	private Integer quantidadeLigacoes = new Integer(0);
	private Integer quantidadeEconomia = new Integer(0);
	
	/**
	* OBS - Duas quebras adicionais nao foram passadas neste contrutor,
	* a saber, idCategoria e idSubCatergoria, pois no momento da criacao deste objeto
	* essas informacoes nao estao disponiveis. 
	*/
	
	public ResumoLigacaoEconomiaRegiaoHelper(Integer idRegiao, Integer idMicrorregiao, Integer idMunicipio,
			Integer idBairro, Integer idPerfilImovel, Integer idSituacaoLigacaoAgua,
			Integer idSituacaoLigacaoEsgoto,
			Integer idEsferaCliente, Integer idTipoClienteResponsavel, Integer idPerfilLigacaoAgua,
			Integer idPerfilLigacaoEsgoto, Integer idIndicadorHidrometro, Integer idIndicadorHidrometroPoco,
			Integer idIndicadorVolumeMinimoAguaFixado, Integer idIndicadorVolumeMinimoEsgotoFixado,
			Integer idIndicadorPoco){
		this.idRegiao = idRegiao;
		this.idMicrorregiao = idMicrorregiao;
		this.idMunicipio = idMunicipio;
		this.idBairro = idBairro;
		this.idPerfilImovel = idPerfilImovel;
		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
		this.idEsferaCliente = idEsferaCliente;
		this.idTipoClienteResponsavel = idTipoClienteResponsavel;
		this.idPerfilLigacaoAgua = idPerfilLigacaoAgua;
		this.idPerfilLigacaoEsgoto = idPerfilLigacaoEsgoto;
		this.idIndicadorHidrometro = idIndicadorHidrometro;
		this.idIndicadorHidrometroPoco = idIndicadorHidrometroPoco;
		this.idIndicadorVolumeMinimoAguaFixado = idIndicadorVolumeMinimoAguaFixado;
		this.idIndicadorVolumeMinimoEsgotoFixado = idIndicadorVolumeMinimoEsgotoFixado;
		this.idIndicadorPoco = idIndicadorPoco;
	}
	
	public Integer getIdBairro() {
		return idBairro;
	}


	public void setIdBairro(Integer idBairro) {
		this.idBairro = idBairro;
	}


	public Integer getIdEsferaCliente() {
		return idEsferaCliente;
	}


	public void setIdEsferaCliente(Integer idEsferaCliente) {
		this.idEsferaCliente = idEsferaCliente;
	}


	public Integer getIdIndicadorHidrometro() {
		return idIndicadorHidrometro;
	}


	public void setIdIndicadorHidrometro(Integer idIndicadorHidrometro) {
		this.idIndicadorHidrometro = idIndicadorHidrometro;
	}


	public Integer getIdIndicadorHidrometroPoco() {
		return idIndicadorHidrometroPoco;
	}


	public void setIdIndicadorHidrometroPoco(Integer idIndicadorHidrometroPoco) {
		this.idIndicadorHidrometroPoco = idIndicadorHidrometroPoco;
	}


	public Integer getIdIndicadorPoco() {
		return idIndicadorPoco;
	}


	public void setIdIndicadorPoco(Integer idIndicadorPoco) {
		this.idIndicadorPoco = idIndicadorPoco;
	}


	public Integer getIdIndicadorVolumeMinimoAguaFixado() {
		return idIndicadorVolumeMinimoAguaFixado;
	}


	public void setIdIndicadorVolumeMinimoAguaFixado(
			Integer idIndicadorVolumeMinimoAguaFixado) {
		this.idIndicadorVolumeMinimoAguaFixado = idIndicadorVolumeMinimoAguaFixado;
	}


	public Integer getIdIndicadorVolumeMinimoEsgotoFixado() {
		return idIndicadorVolumeMinimoEsgotoFixado;
	}


	public void setIdIndicadorVolumeMinimoEsgotoFixado(
			Integer idIndicadorVolumeMinimoEsgotoFixado) {
		this.idIndicadorVolumeMinimoEsgotoFixado = idIndicadorVolumeMinimoEsgotoFixado;
	}


	public Integer getIdMicrorregiao() {
		return idMicrorregiao;
	}


	public void setIdMicrorregiao(Integer idMicrorregiao) {
		this.idMicrorregiao = idMicrorregiao;
	}


	public Integer getIdMunicipio() {
		return idMunicipio;
	}


	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}


	public Integer getIdPerfilImovel() {
		return idPerfilImovel;
	}


	public void setIdPerfilImovel(Integer idPerfilImovel) {
		this.idPerfilImovel = idPerfilImovel;
	}


	public Integer getIdPerfilLigacaoAgua() {
		return idPerfilLigacaoAgua;
	}


	public void setIdPerfilLigacaoAgua(Integer idPerfilLigacaoAgua) {
		this.idPerfilLigacaoAgua = idPerfilLigacaoAgua;
	}


	public Integer getIdPerfilLigacaoEsgoto() {
		return idPerfilLigacaoEsgoto;
	}


	public void setIdPerfilLigacaoEsgoto(Integer idPerfilLigacaoEsgoto) {
		this.idPerfilLigacaoEsgoto = idPerfilLigacaoEsgoto;
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


	public void setIdPrincipalSubCategoriaImovel(
			Integer idPrincipalSubCategoriaImovel) {
		this.idPrincipalSubCategoriaImovel = idPrincipalSubCategoriaImovel;
	}


	public Integer getIdRegiao() {
		return idRegiao;
	}


	public void setIdRegiao(Integer idRegiao) {
		this.idRegiao = idRegiao;
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


	public Integer getIdTipoClienteResponsavel() {
		return idTipoClienteResponsavel;
	}


	public void setIdTipoClienteResponsavel(Integer idTipoClienteResponsavel) {
		this.idTipoClienteResponsavel = idTipoClienteResponsavel;
	}


	public Integer getQuantidadeEconomia() {
		return quantidadeEconomia;
	}


	public void setQuantidadeEconomia(Integer quantidadeEconomia) {
		this.quantidadeEconomia = quantidadeEconomia;
	}


	public Integer getQuantidadeLigacoes() {
		return quantidadeLigacoes;
	}


	public void setQuantidadeLigacoes(Integer quantidadeLigacoes) {
		this.quantidadeLigacoes = quantidadeLigacoes;
	}
	
	
	/**
	 * 
	 * Verifica se duas propriedades dessa classes s�o iguais
	 * @author Bruno Barros
	 * @date 13/04/2007
	 *
	 * @param pro1 Primeira Propriedade
	 * @param pro2 Segunda Propriedade
	 * @return False se diferente, true se iguais
	 */
	private boolean propriedadesIguais( Integer pro1, Integer pro2 ){
		if ( pro1 != null ){
			if ( !pro1.equals( pro2 ) ){
				return false;
			}			
		} else if ( pro2 != null ){
			return false;
		}
		
		// Se chegou at� aqui, � por que a duas propriedades s�o iguais
		return true;
	}
	
	
	/**
	 * 
	 * Verifica se os objetos comparados s�o iguais
	 * @author Ivan S�rgio
	 * @date 16/04/2007
	 *
	 * @param object Objeto a ser comparado com essa instancia
	 * @return
	 */
	
	public boolean equals( Object object ){
		if ( object instanceof ResumoLigacaoEconomiaRegiaoHelper ){
			ResumoLigacaoEconomiaRegiaoHelper rsmTemp = (ResumoLigacaoEconomiaRegiaoHelper) object;
			
			return
			  propriedadesIguais( rsmTemp.getIdRegiao(), this.getIdRegiao() ) &&
			  propriedadesIguais( rsmTemp.getIdMicrorregiao(), this.getIdMicrorregiao() ) &&
			  propriedadesIguais( rsmTemp.getIdMunicipio(), this.getIdMunicipio() ) &&
			  propriedadesIguais( rsmTemp.getIdBairro(), this.getIdBairro() ) &&
			  propriedadesIguais( rsmTemp.getIdPerfilImovel(), this.getIdPerfilImovel() ) &&
			  propriedadesIguais( rsmTemp.getIdSituacaoLigacaoAgua(), this.getIdSituacaoLigacaoAgua() ) &&
			  propriedadesIguais( rsmTemp.getIdSituacaoLigacaoEsgoto(), this.getIdSituacaoLigacaoEsgoto() ) &&
			  propriedadesIguais( rsmTemp.getIdPrincipalCategoriaImovel(), this.getIdPrincipalCategoriaImovel() ) &&
			  propriedadesIguais( rsmTemp.getIdPrincipalSubCategoriaImovel(), this.getIdPrincipalSubCategoriaImovel() ) &&
			  propriedadesIguais( rsmTemp.getIdEsferaCliente(), this.getIdEsferaCliente() ) &&
			  propriedadesIguais( rsmTemp.getIdTipoClienteResponsavel(), this.getIdTipoClienteResponsavel() ) &&
			  propriedadesIguais( rsmTemp.getIdPerfilLigacaoAgua(), this.getIdPerfilLigacaoAgua() ) &&
			  propriedadesIguais( rsmTemp.getIdPerfilLigacaoEsgoto(), this.getIdPerfilLigacaoEsgoto() ) &&
			  propriedadesIguais( rsmTemp.getIdIndicadorHidrometro(), this.getIdIndicadorHidrometro() ) &&
			  propriedadesIguais( rsmTemp.getIdIndicadorHidrometroPoco(), this.getIdIndicadorHidrometroPoco() ) &&
			  propriedadesIguais( rsmTemp.getIdIndicadorVolumeMinimoAguaFixado(), this.getIdIndicadorVolumeMinimoAguaFixado() ) &&
			  propriedadesIguais( rsmTemp.getIdIndicadorVolumeMinimoEsgotoFixado(), this.getIdIndicadorVolumeMinimoEsgotoFixado() ) &&
			  propriedadesIguais( rsmTemp.getIdIndicadorPoco(), this.getIdIndicadorPoco() );
		} else {
			return false;
		}
	}
}
