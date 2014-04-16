package gcom.gerencial.faturamento.bean;

import java.math.BigDecimal;

/**
 * Classe respons�vel por ajudar o caso de uso [UC0571] Gerar Resumo do Faturamento 
 *
 * @author Fernando Fontelles
 * @date 25/05/2010
 */
public class ResumoFaturamentoPorAnoHelper {
    
    public final static Integer RESUMO_AGUA_ESGOTO = 0; 
    public final static Integer RESUMO_DEBITOS_COBRADOS = 1;
    public final static Integer RESUMO_IMPOSTOS = 2;
    public final static Integer RESUMO_CREDITOS = 3;
    public final static Integer RESUMO_GUIA = 4;
    public final static Integer RESUMO_FINANCIAMENTO = 5;
    
    private Integer anoMesFaturamento;
    private Integer idGerenciaRegional;
    private Integer idUnidadeNegocio;
    private Integer cdElo;
    private Integer idLocalidade;
    private Integer idSetorComercial;
    private Integer cdSetorComercial;
//    private Integer idRota;
//    private Short   cdRota;
//    private Integer idQuadra;
//    private Integer nmQuadra;
    private Integer idPerfilImovel;
    private Integer situacaoLigacaoAgua;
    private Integer situacaoLigacaoEsgoto;
    private Integer idCategoria;
    private Integer idSubcategoria;
    private Integer idEsferaPoder;
    private Integer idTipoCliente;
    private Integer idPerfilLigacaoAgua;
    private Integer idPerfilLigacaoEsgoto;
    private Integer idTarifaConsumo;
    private Integer idGrupoFaturamento;
//    private Integer idEmpresa;
    private Integer indHidrometro;
    private Integer idOrigemCredito;
    private Integer idItemLancamentoContabil;
    private Integer idTipoDocumento;
    private Integer idTipoFinanciamento;
    private Integer idTipoDebito;
    private Integer idTipoCredito;
    private Integer idTipoImposto;
    private BigDecimal valorAgua = new BigDecimal( 0 );
    private BigDecimal valorEsgoto = new BigDecimal( 0 );
    private BigDecimal valorDocumentosFaturadosOutros = new BigDecimal( 0 );
    private BigDecimal valorImposto = new BigDecimal( 0 );
    private BigDecimal valorCreditoRealizado = new BigDecimal( 0 );
    private BigDecimal valorFinanciadoIncluido = new BigDecimal( 0 );
    private BigDecimal valorFinanciadoCancelado = new BigDecimal( 0 );
    private Integer quantidadeDocumentosFaturadosOutros = 0;
    private Integer quantidadeDocumentosFaturadosCredito = 0;
    private Integer volumeAgua = 0;
    private Integer volumeEsgoto = 0;
    private Integer quantidadeEconomiasFaturadas = 0;
    private Integer quantidadeContasEmitidas = 0;
    private Integer tipoResumo;
    
    
    public Integer getTipoResumo() {
        return tipoResumo;
    }


    
    public void setTipoResumo(Integer tipoResumo) {
        this.tipoResumo = tipoResumo;
    }


    public Integer getQuantidadeContasEmitidas() {
        return quantidadeContasEmitidas;
    }

    
    public void setQuantidadeContasEmitidas(Integer quantidadeContasEmitidas) {
        this.quantidadeContasEmitidas = quantidadeContasEmitidas;
    }

    
    public Integer getQuantidadeEconomiasFaturadas() {
        return quantidadeEconomiasFaturadas;
    }

    
    public void setQuantidadeEconomiasFaturadas(Integer quantidadeEconomiasFaturadas) {
        this.quantidadeEconomiasFaturadas = quantidadeEconomiasFaturadas;
    }

    
    public BigDecimal getValorAgua() {
        return valorAgua;
    }

    
    public void setValorAgua(BigDecimal valorAgua) {
        this.valorAgua = valorAgua;
    }

    
    public BigDecimal getValorEsgoto() {
        return valorEsgoto;
    }

    
    public void setValorEsgoto(BigDecimal valorEsgoto) {
        this.valorEsgoto = valorEsgoto;
    }

    
    public Integer getVolumeAgua() {
        return volumeAgua;
    }

    
    public void setVolumeAgua(Integer volumeAgua) {
        this.volumeAgua = volumeAgua;
    }

    
    public Integer getVolumeEsgoto() {
        return volumeEsgoto;
    }

    
    public void setVolumeEsgoto(Integer volumeEsgoto) {
        this.volumeEsgoto = volumeEsgoto;
    }
    
    /**
     * Compara duas properiedades inteiras, comparando
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
     * Compara duas properiedades inteiras, comparando
     * seus valores para descobrirmos se sao iguais
     * @param pro1 Primeira propriedade
     * @param pro2 Segunda propriedade
     * @return Se iguais, retorna true
     */
//    private boolean propriedadesIguais( Short pro1, Short pro2 ){
//      if ( pro2 != null ){
//          if ( !pro2.equals( pro1 ) ){
//              return false;
//          }
//      } else if ( pro1 != null ){
//          return false;
//      }
//      
//      // Se chegou ate aqui quer dizer que as propriedades sao iguais
//      return true;
//    }        

    public boolean equals( Object obj ){
        
        ResumoFaturamentoPorAnoHelper objeto = ( ResumoFaturamentoPorAnoHelper ) obj;
        
        // Verificamos qual o tipo de quebra que precisa ser respeitado para cada tipo de resumo
        if ( this.getTipoResumo().equals( ResumoFaturamentoPorAnoHelper.RESUMO_AGUA_ESGOTO ) ){
            return
                propriedadesIguais( this.getAnoMesFaturamento(), objeto.getAnoMesFaturamento() ) &&
                propriedadesIguais( this.getIdGerenciaRegional(), objeto.getIdGerenciaRegional() ) &&
                propriedadesIguais( this.getIdUnidadeNegocio(), objeto.getIdUnidadeNegocio() ) &&
                propriedadesIguais( this.getCdElo(), objeto.getCdElo() ) &&
                propriedadesIguais( this.getIdLocalidade(), objeto.getIdLocalidade() ) &&
                propriedadesIguais( this.getIdSetorComercial(), objeto.getIdSetorComercial() ) &&
                propriedadesIguais( this.getCdSetorComercial(), objeto.getCdSetorComercial() ) &&
//                propriedadesIguais( this.getIdRota(), objeto.getIdRota() ) &&
//                propriedadesIguais( this.getCdRota(), objeto.getCdRota() ) &&
//                propriedadesIguais( this.getIdQuadra(), objeto.getIdQuadra() ) &&
//                propriedadesIguais( this.getNmQuadra(), objeto.getNmQuadra() ) &&
                propriedadesIguais( this.getIdPerfilImovel(), objeto.getIdPerfilImovel() ) &&
                propriedadesIguais( this.getSituacaoLigacaoAgua(), objeto.getSituacaoLigacaoAgua() ) &&
                propriedadesIguais( this.getSituacaoLigacaoEsgoto(), objeto.getSituacaoLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdCategoria(), objeto.getIdCategoria() ) &&
                propriedadesIguais( this.getIdSubcategoria(), objeto.getIdSubcategoria() ) &&
                propriedadesIguais( this.getIdEsferaPoder(), objeto.getIdEsferaPoder() ) &&
                propriedadesIguais( this.getIdTipoCliente(), objeto.getIdTipoCliente() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoAgua(), objeto.getIdPerfilLigacaoAgua() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoEsgoto(), objeto.getIdPerfilLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdTarifaConsumo(), objeto.getIdTarifaConsumo() ) &&
                propriedadesIguais( this.getIdGrupoFaturamento(), objeto.getIdGrupoFaturamento() ) &&
//                propriedadesIguais( this.getIdEmpresa(), objeto.getIdEmpresa() ) &&
                propriedadesIguais( this.getIndHidrometro(), objeto.getIndHidrometro() ); 
        } else if ( propriedadesIguais( this.getTipoResumo(), ResumoFaturamentoPorAnoHelper.RESUMO_DEBITOS_COBRADOS ) ){
            return
                propriedadesIguais( this.getAnoMesFaturamento(), objeto.getAnoMesFaturamento() ) &&
                propriedadesIguais( this.getIdGerenciaRegional(), objeto.getIdGerenciaRegional() ) &&
                propriedadesIguais( this.getIdUnidadeNegocio(), objeto.getIdUnidadeNegocio() ) &&
                propriedadesIguais( this.getCdElo(), objeto.getCdElo() ) &&
                propriedadesIguais( this.getIdLocalidade(), objeto.getIdLocalidade() ) &&
                propriedadesIguais( this.getIdSetorComercial(), objeto.getIdSetorComercial() ) &&
                propriedadesIguais( this.getCdSetorComercial(), objeto.getCdSetorComercial() ) &&
//                propriedadesIguais( this.getIdRota(), objeto.getIdRota() ) &&
//                propriedadesIguais( this.getCdRota(), objeto.getCdRota() ) &&
//                propriedadesIguais( this.getIdQuadra(), objeto.getIdQuadra() ) &&
//                propriedadesIguais( this.getNmQuadra(), objeto.getNmQuadra() ) &&
                propriedadesIguais( this.getIdPerfilImovel(), objeto.getIdPerfilImovel() ) &&
                propriedadesIguais( this.getSituacaoLigacaoAgua(), objeto.getSituacaoLigacaoAgua() ) &&
                propriedadesIguais( this.getSituacaoLigacaoEsgoto(), objeto.getSituacaoLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdCategoria(), objeto.getIdCategoria() ) &&
                propriedadesIguais( this.getIdSubcategoria(), objeto.getIdSubcategoria() ) &&
                propriedadesIguais( this.getIdEsferaPoder(), objeto.getIdEsferaPoder() ) &&
                propriedadesIguais( this.getIdTipoCliente(), objeto.getIdTipoCliente() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoAgua(), objeto.getIdPerfilLigacaoAgua() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoEsgoto(), objeto.getIdPerfilLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdTarifaConsumo(), objeto.getIdTarifaConsumo() ) &&
                propriedadesIguais( this.getIdGrupoFaturamento(), objeto.getIdGrupoFaturamento() ) &&
//                propriedadesIguais( this.getIdEmpresa(), objeto.getIdEmpresa() ) &&
                propriedadesIguais( this.getIndHidrometro(), objeto.getIndHidrometro() ) &&
                propriedadesIguais( this.getIdItemLancamentoContabil(), objeto.getIdItemLancamentoContabil() ) &&
                propriedadesIguais( this.getIdTipoFinanciamento(), objeto.getIdTipoFinanciamento() ) &&
                propriedadesIguais( this.getIdTipoDebito(), objeto.getIdTipoDebito() );
        } else if ( propriedadesIguais( this.getTipoResumo(), ResumoFaturamentoPorAnoHelper.RESUMO_IMPOSTOS ) ){
            return
                propriedadesIguais( this.getAnoMesFaturamento(), objeto.getAnoMesFaturamento() ) &&
                propriedadesIguais( this.getIdGerenciaRegional(), objeto.getIdGerenciaRegional() ) &&
                propriedadesIguais( this.getIdUnidadeNegocio(), objeto.getIdUnidadeNegocio() ) &&
                propriedadesIguais( this.getCdElo(), objeto.getCdElo() ) &&
                propriedadesIguais( this.getIdLocalidade(), objeto.getIdLocalidade() ) &&
                propriedadesIguais( this.getIdSetorComercial(), objeto.getIdSetorComercial() ) &&
                propriedadesIguais( this.getCdSetorComercial(), objeto.getCdSetorComercial() ) &&
//                propriedadesIguais( this.getIdRota(), objeto.getIdRota() ) &&
//                propriedadesIguais( this.getCdRota(), objeto.getCdRota() ) &&
//                propriedadesIguais( this.getIdQuadra(), objeto.getIdQuadra() ) &&
//                propriedadesIguais( this.getNmQuadra(), objeto.getNmQuadra() ) &&
                propriedadesIguais( this.getIdPerfilImovel(), objeto.getIdPerfilImovel() ) &&
                propriedadesIguais( this.getSituacaoLigacaoAgua(), objeto.getSituacaoLigacaoAgua() ) &&
                propriedadesIguais( this.getSituacaoLigacaoEsgoto(), objeto.getSituacaoLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdCategoria(), objeto.getIdCategoria() ) &&
                propriedadesIguais( this.getIdSubcategoria(), objeto.getIdSubcategoria() ) &&
                propriedadesIguais( this.getIdEsferaPoder(), objeto.getIdEsferaPoder() ) &&
                propriedadesIguais( this.getIdTipoCliente(), objeto.getIdTipoCliente() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoAgua(), objeto.getIdPerfilLigacaoAgua() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoEsgoto(), objeto.getIdPerfilLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdTarifaConsumo(), objeto.getIdTarifaConsumo() ) &&
                propriedadesIguais( this.getIdGrupoFaturamento(), objeto.getIdGrupoFaturamento() ) &&
//                propriedadesIguais( this.getIdEmpresa(), objeto.getIdEmpresa() ) &&
                propriedadesIguais( this.getIndHidrometro(), objeto.getIndHidrometro() ) &&
                propriedadesIguais( this.getIdTipoImposto(), objeto.getIdTipoImposto() );
        } else if ( propriedadesIguais( this.getTipoResumo(), ResumoFaturamentoPorAnoHelper.RESUMO_GUIA ) ) {
            return
                propriedadesIguais( this.getAnoMesFaturamento(), objeto.getAnoMesFaturamento() ) &&
                propriedadesIguais( this.getIdGerenciaRegional(), objeto.getIdGerenciaRegional() ) &&
                propriedadesIguais( this.getIdUnidadeNegocio(), objeto.getIdUnidadeNegocio() ) &&
                propriedadesIguais( this.getCdElo(), objeto.getCdElo() ) &&
                propriedadesIguais( this.getIdLocalidade(), objeto.getIdLocalidade() ) &&
                propriedadesIguais( this.getIdSetorComercial(), objeto.getIdSetorComercial() ) &&
                propriedadesIguais( this.getCdSetorComercial(), objeto.getCdSetorComercial() ) &&
//                propriedadesIguais( this.getIdRota(), objeto.getIdRota() ) &&
//                propriedadesIguais( this.getCdRota(), objeto.getCdRota() ) &&
//                propriedadesIguais( this.getIdQuadra(), objeto.getIdQuadra() ) &&
//                propriedadesIguais( this.getNmQuadra(), objeto.getNmQuadra() ) &&
                propriedadesIguais( this.getIdPerfilImovel(), objeto.getIdPerfilImovel() ) &&
                propriedadesIguais( this.getSituacaoLigacaoAgua(), objeto.getSituacaoLigacaoAgua() ) &&
                propriedadesIguais( this.getSituacaoLigacaoEsgoto(), objeto.getSituacaoLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdCategoria(), objeto.getIdCategoria() ) &&
                propriedadesIguais( this.getIdSubcategoria(), objeto.getIdSubcategoria() ) &&
                propriedadesIguais( this.getIdEsferaPoder(), objeto.getIdEsferaPoder() ) &&
                propriedadesIguais( this.getIdTipoCliente(), objeto.getIdTipoCliente() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoAgua(), objeto.getIdPerfilLigacaoAgua() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoEsgoto(), objeto.getIdPerfilLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdTarifaConsumo(), objeto.getIdTarifaConsumo() ) &&
                propriedadesIguais( this.getIdGrupoFaturamento(), objeto.getIdGrupoFaturamento() ) &&
//                propriedadesIguais( this.getIdEmpresa(), objeto.getIdEmpresa() ) &&
                propriedadesIguais( this.getIndHidrometro(), objeto.getIndHidrometro() ) && 
                propriedadesIguais( this.getIdItemLancamentoContabil(), objeto.getIdItemLancamentoContabil() ) &&
                propriedadesIguais( this.getIdTipoFinanciamento(), objeto.getIdTipoFinanciamento() ) &&
                propriedadesIguais( this.getIdTipoDebito(), objeto.getIdTipoDebito() );            
        } else if ( propriedadesIguais( this.getTipoResumo(), ResumoFaturamentoPorAnoHelper.RESUMO_FINANCIAMENTO ) ){
            return 
                propriedadesIguais( this.getAnoMesFaturamento(), objeto.getAnoMesFaturamento() ) &&
                propriedadesIguais( this.getIdGerenciaRegional(), objeto.getIdGerenciaRegional() ) &&
                propriedadesIguais( this.getIdUnidadeNegocio(), objeto.getIdUnidadeNegocio() ) &&
                propriedadesIguais( this.getCdElo(), objeto.getCdElo() ) &&
                propriedadesIguais( this.getIdLocalidade(), objeto.getIdLocalidade() ) &&
                propriedadesIguais( this.getIdSetorComercial(), objeto.getIdSetorComercial() ) &&
                propriedadesIguais( this.getCdSetorComercial(), objeto.getCdSetorComercial() ) &&
//                propriedadesIguais( this.getIdRota(), objeto.getIdRota() ) &&
//                propriedadesIguais( this.getCdRota(), objeto.getCdRota() ) &&
//                propriedadesIguais( this.getIdQuadra(), objeto.getIdQuadra() ) &&
//                propriedadesIguais( this.getNmQuadra(), objeto.getNmQuadra() ) &&
                propriedadesIguais( this.getIdPerfilImovel(), objeto.getIdPerfilImovel() ) &&
                propriedadesIguais( this.getSituacaoLigacaoAgua(), objeto.getSituacaoLigacaoAgua() ) &&
                propriedadesIguais( this.getSituacaoLigacaoEsgoto(), objeto.getSituacaoLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdCategoria(), objeto.getIdCategoria() ) &&
                propriedadesIguais( this.getIdSubcategoria(), objeto.getIdSubcategoria() ) &&
                propriedadesIguais( this.getIdEsferaPoder(), objeto.getIdEsferaPoder() ) &&
                propriedadesIguais( this.getIdTipoCliente(), objeto.getIdTipoCliente() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoAgua(), objeto.getIdPerfilLigacaoAgua() ) &&
                propriedadesIguais( this.getIdPerfilLigacaoEsgoto(), objeto.getIdPerfilLigacaoEsgoto() ) &&
                propriedadesIguais( this.getIdTarifaConsumo(), objeto.getIdTarifaConsumo() ) &&
                propriedadesIguais( this.getIdGrupoFaturamento(), objeto.getIdGrupoFaturamento() ) &&
//                propriedadesIguais( this.getIdEmpresa(), objeto.getIdEmpresa() ) &&
                propriedadesIguais( this.getIndHidrometro(), objeto.getIndHidrometro() ) &&
                propriedadesIguais( this.getIdItemLancamentoContabil(), objeto.getIdItemLancamentoContabil() ) &&
                propriedadesIguais( this.getIdTipoDebito(), objeto.getIdTipoDebito() );           
        } else {
            return false;
        }
    }

    public Integer getCdElo() {
        return cdElo;
    }
    
    public void setCdElo(Integer cdElo) {
        this.cdElo = cdElo;
    }
    
    public Integer getCdSetorComercial() {
        return cdSetorComercial;
    }
    
    public void setCdSetorComercial(Integer cdSetorComercial) {
        this.cdSetorComercial = cdSetorComercial;
    }
    
    public Integer getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
    
//    public Integer getIdEmpresa() {
//        return idEmpresa;
//    }
//    
//    public void setIdEmpresa(Integer idEmpresa) {
//        this.idEmpresa = idEmpresa;
//    }
    
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
    
    public Integer getIdGrupoFaturamento() {
        return idGrupoFaturamento;
    }
    
    public void setIdGrupoFaturamento(Integer idGrupoFaturamento) {
        this.idGrupoFaturamento = idGrupoFaturamento;
    }
    
    public Integer getIdItemLancamentoContabil() {
        return idItemLancamentoContabil;
    }
    
    public void setIdItemLancamentoContabil(Integer idItemLancamentoContabil) {
        this.idItemLancamentoContabil = idItemLancamentoContabil;
    }
    
    public Integer getIdLocalidade() {
        return idLocalidade;
    }
    
    public void setIdLocalidade(Integer idLocalidade) {
        this.idLocalidade = idLocalidade;
    }
    
    public Integer getIdOrigemCredito() {
        return idOrigemCredito;
    }
    
    public void setIdOrigemCredito(Integer idOrigemCredito) {
        this.idOrigemCredito = idOrigemCredito;
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
    
    public void setIdPerfilLigacaoEsgoto(
            Integer idPerfilLigacaoEsgoto) {
        this.idPerfilLigacaoEsgoto = idPerfilLigacaoEsgoto;
    }
    
//    public Integer getIdQuadra() {
//        return idQuadra;
//    }
//    
//    public void setIdQuadra(Integer idQuadra) {
//        this.idQuadra = idQuadra;
//    }
//    
//    public Integer getIdRota() {
//        return idRota;
//    }
//    
//    public void setIdRota(Integer idRota) {
//        this.idRota = idRota;
//    }
    
    public Integer getIdSetorComercial() {
        return idSetorComercial;
    }
    
    public void setIdSetorComercial(Integer idSetorComercial) {
        this.idSetorComercial = idSetorComercial;
    }
    
    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }
    
    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }
    
    public Integer getIdTarifaConsumo() {
        return idTarifaConsumo;
    }
    
    public void setIdTarifaConsumo(Integer idTarifaConsumo) {
        this.idTarifaConsumo = idTarifaConsumo;
    }
    
    public Integer getIdTipoCliente() {
        return idTipoCliente;
    }
    
    public void setIdTipoCliente(Integer idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }
    
    public Integer getIdTipoCredito() {
        return idTipoCredito;
    }
    
    public void setIdTipoCredito(Integer idTipoCredito) {
        this.idTipoCredito = idTipoCredito;
    }
    
    public Integer getIdTipoDebito() {
        return idTipoDebito;
    }
    
    public void setIdTipoDebito(Integer idTipoDebito) {
        this.idTipoDebito = idTipoDebito;
    }
    
    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }
    
    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }
    
    public Integer getIdTipoFinanciamento() {
        return idTipoFinanciamento;
    }
    
    public void setIdTipoFinanciamento(Integer idTipoFinanciamento) {
        this.idTipoFinanciamento = idTipoFinanciamento;
    }
    
    public Integer getIdTipoImposto() {
        return idTipoImposto;
    }
    
    public void setIdTipoImposto(Integer idTipoImposto) {
        this.idTipoImposto = idTipoImposto;
    }
    
    public Integer getIdUnidadeNegocio() {
        return idUnidadeNegocio;
    }
    
    public void setIdUnidadeNegocio(Integer idUnidadeNegocio) {
        this.idUnidadeNegocio = idUnidadeNegocio;
    }
    
    public Integer getIndHidrometro() {
        return indHidrometro;
    }
    
    public void setIndHidrometro(Integer indHidrometro) {
        this.indHidrometro = indHidrometro;
    }
    
//    public Integer getNmQuadra() {
//        return nmQuadra;
//    }
//    
//    public void setNmQuadra(Integer nmQuadra) {
//        this.nmQuadra = nmQuadra;
//    }
    
    public Integer getSituacaoLigacaoAgua() {
        return situacaoLigacaoAgua;
    }
    
    public void setSituacaoLigacaoAgua(Integer situacaoLigacaoAgua) {
        this.situacaoLigacaoAgua = situacaoLigacaoAgua;
    }
    
    public Integer getSituacaoLigacaoEsgoto() {
        return situacaoLigacaoEsgoto;
    }
    
    public void setSituacaoLigacaoEsgoto(Integer situacaoLigacaoEsgoto) {
        this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
    }


    
    public Integer getAnoMesFaturamento() {
        return anoMesFaturamento;
    }


    
    public void setAnoMesFaturamento(Integer anoMesFaturamento) {
        this.anoMesFaturamento = anoMesFaturamento;
    }


    
//    public Short getCdRota() {
//        return cdRota;
//    }
//
//
//    
//    public void setCdRota(Short cdRota) {
//        this.cdRota = cdRota;
//    }


    
    public Integer getQuantidadeDocumentosFaturadosOutros() {
        return quantidadeDocumentosFaturadosOutros;
    }


    
    public void setQuantidadeDocumentosFaturadosOutros(
            Integer quantidadeDocumentosFaturadosOutros) {
        this.quantidadeDocumentosFaturadosOutros = quantidadeDocumentosFaturadosOutros;
    }


    
    public BigDecimal getValorDocumentosFaturadosOutros() {
        return valorDocumentosFaturadosOutros;
    }


    
    public void setValorDocumentosFaturadosOutros(
            BigDecimal valorDocumentosFaturadosOutros) {
        this.valorDocumentosFaturadosOutros = valorDocumentosFaturadosOutros;
    }



    
    public BigDecimal getValorImposto() {
        return valorImposto;
    }



    
    public void setValorImposto(BigDecimal valorImposto) {
        this.valorImposto = valorImposto;
    }



    
    public Integer getQuantidadeDocumentosFaturadosCredito() {
        return quantidadeDocumentosFaturadosCredito;
    }



    
    public void setQuantidadeDocumentosFaturadosCredito(
            Integer quantidadeDocumentosFaturadosCredito) {
        this.quantidadeDocumentosFaturadosCredito = quantidadeDocumentosFaturadosCredito;
    }



    
    public BigDecimal getValorCreditoRealizado() {
        return valorCreditoRealizado;
    }



    
    public void setValorCreditoRealizado(BigDecimal valorCreditoRealizado) {
        this.valorCreditoRealizado = valorCreditoRealizado;
    }



    
    public BigDecimal getValorFinanciadoCancelado() {
        return valorFinanciadoCancelado;
    }



    
    public void setValorFinanciadoCancelado(BigDecimal valorFinanciadoCancelado) {
        this.valorFinanciadoCancelado = valorFinanciadoCancelado;
    }



    
    public BigDecimal getValorFinanciadoIncluido() {
        return valorFinanciadoIncluido;
    }



    
    public void setValorFinanciadoIncluido(BigDecimal valorFinanciadoIncluido) {
        this.valorFinanciadoIncluido = valorFinanciadoIncluido;
    }
}

