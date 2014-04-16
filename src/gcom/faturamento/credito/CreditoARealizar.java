package gcom.faturamento.credito;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class CreditoARealizar extends ObjetoTransacao {
	private static final long serialVersionUID = 1L;
		
	public static final int ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR = 62; //Operacao.OPERACAO_CREDITO_A_REALIZAR_INSERIR
	public static final int ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR = 66; //Operacao.OPERACAO_CREDITO_A_REALIZAR_CANCELAR
	
	/** identifier field */
	@ControleAlteracao(funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR})
	private Integer id;

	/** persistent field */
	private Date geracaoCredito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR,ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private Integer anoMesReferenciaCredito;

	/** nullable persistent field */
	private Integer anoMesReferenciaContabil;

	/** nullable persistent field */
	private Integer anoMesCobrancaCredito;

	/** nullable persistent field */
	private BigDecimal valorResidualMesAnterior;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR,ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private BigDecimal valorCredito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR,ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private Short numeroPrestacaoCredito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR})
	private Short numeroPrestacaoRealizada;

	/** nullable persistent field */
	private Integer codigoSetorComercial;

	/** nullable persistent field */
	private Integer numeroQuadra;

	/** nullable persistent field */
	private Short numeroLote;

	/** nullable persistent field */
	private Short numeroSubLote;

	/** nullable persistent field */
	private Date ultimaAlteracao;
    
    private Short numeroParcelaBonus;
    
    /** nullable persistent field */
	private BigDecimal valorResidualConcedidoMes;

	/** persistent field */
	@ControleAlteracao(value=FiltroCreditoARealizar.REGISTRO_ATENDIMENTO,
			funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR,ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private RegistroAtendimento registroAtendimento;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	@ControleAlteracao(value=FiltroCreditoARealizar.ORDEM_SERVICO,
			funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR,ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private OrdemServico ordemServico;

	/** persistent field */
	private Quadra quadra;

	/** persistent field */
	private Localidade localidade;

	/** nullable persistent field */
	@ControleAlteracao(value=FiltroCreditoARealizar.CREDITO_TIPO,
			funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR,ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private gcom.faturamento.credito.CreditoTipo creditoTipo;

	/** persistent field */
	private LancamentoItemContabil lancamentoItemContabil;

	/** persistent field */
	@ControleAlteracao(value=FiltroCreditoARealizar.DEBITO_CREDITO_SITUACAO_ATUAL_,
			funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private DebitoCreditoSituacao debitoCreditoSituacaoAtual;

	/** persistent field */
	private DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	/** persistent field */
	@ControleAlteracao(value=FiltroCreditoARealizar.CREDITO_ORIGEM,
			funcionalidade={ATRIBUTOS_CREDITO_A_REALIZAR_INSERIR,ATRIBUTOS_CREDITO_A_REALIZAR_CANCELAR})
	private gcom.faturamento.credito.CreditoOrigem creditoOrigem;

	/** persistent field */
	private Parcelamento parcelamento;

	/** persistent field */
	private DocumentoTipo documentoTipo;
	
	/** persistent field */
	private Usuario usuario;

	/** persistent field */
	private Set creditoARealizarCategoria;
	
	/** persistent field */
	private CreditoARealizarGeral origem;
	
	
	private CreditoARealizarGeral creditoARealizarGeral;
	
	private Integer anoMesReferenciaPrestacao;
	
	private Integer numeroParcelasAntecipadas;
	

	/** full constructor */
	public CreditoARealizar(Date geracaoCredito,
			Integer anoMesReferenciaCredito, Integer anoMesReferenciaContabil,
			Integer anoMesCobrancaCredito, BigDecimal valorResidualMesAnterior,
			BigDecimal valorCredito, Short numeroPrestacaoCredito,
			Short numeroPrestacaoRealizada, Integer codigoSetorComercial,
			Integer numeroQuadra, Short numeroLote, Short numeroSubLote,
			Date ultimaAlteracao, RegistroAtendimento registroAtendimento,
			Imovel imovel, OrdemServico ordemServico, Quadra quadra,
			Localidade localidade,
			gcom.faturamento.credito.CreditoTipo creditoTipo,
			LancamentoItemContabil lancamentoItemContabil,
			DebitoCreditoSituacao debitoCreditoSituacaoAtual,
			DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
			gcom.faturamento.credito.CreditoOrigem creditoOrigem,
			Parcelamento parcelamento, DocumentoTipo documentoTipo,
			Usuario usuario, Set creditoARealizarCategoria) {
		this.geracaoCredito = geracaoCredito;
		this.anoMesReferenciaCredito = anoMesReferenciaCredito;
		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
		this.anoMesCobrancaCredito = anoMesCobrancaCredito;
		this.valorResidualMesAnterior = valorResidualMesAnterior;
		this.valorCredito = valorCredito;
		this.numeroPrestacaoCredito = numeroPrestacaoCredito;
		this.numeroPrestacaoRealizada = numeroPrestacaoRealizada;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.ultimaAlteracao = ultimaAlteracao;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.ordemServico = ordemServico;
		this.quadra = quadra;
		this.localidade = localidade;
		this.creditoTipo = creditoTipo;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.creditoOrigem = creditoOrigem;
		this.parcelamento = parcelamento;
		this.documentoTipo = documentoTipo;
		this.usuario = usuario;
		this.creditoARealizarCategoria = creditoARealizarCategoria;
	}

	/** default constructor */
	public CreditoARealizar() {
	}

	/** minimal constructor */
	public CreditoARealizar(Date geracaoCredito,
			RegistroAtendimento registroAtendimento, Imovel imovel,
			OrdemServico ordemServico, Quadra quadra, Localidade localidade,
			LancamentoItemContabil lancamentoItemContabil,
			DebitoCreditoSituacao debitoCreditoSituacaoAtual,
			DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
			gcom.faturamento.credito.CreditoOrigem creditoOrigem,
			Parcelamento parcelamento, DocumentoTipo documentoTipo,
			Set creditoARealizarCategoria) {
		this.geracaoCredito = geracaoCredito;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.ordemServico = ordemServico;
		this.quadra = quadra;
		this.localidade = localidade;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.creditoOrigem = creditoOrigem;
		this.parcelamento = parcelamento;
		this.documentoTipo = documentoTipo;
		this.creditoARealizarCategoria = creditoARealizarCategoria;
	}
	


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getGeracaoCredito() {
		return this.geracaoCredito;
	}

	public void setGeracaoCredito(Date geracaoCredito) {
		this.geracaoCredito = geracaoCredito;
	}

	public Integer getAnoMesReferenciaCredito() {
		return this.anoMesReferenciaCredito;
	}

	public void setAnoMesReferenciaCredito(Integer anoMesReferenciaCredito) {
		this.anoMesReferenciaCredito = anoMesReferenciaCredito;
	}

	public Integer getAnoMesReferenciaContabil() {
		return this.anoMesReferenciaContabil;
	}

	public void setAnoMesReferenciaContabil(Integer anoMesReferenciaContabil) {
		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
	}

	public Integer getAnoMesCobrancaCredito() {
		return this.anoMesCobrancaCredito;
	}

	public void setAnoMesCobrancaCredito(Integer anoMesCobrancaCredito) {
		this.anoMesCobrancaCredito = anoMesCobrancaCredito;
	}

	public BigDecimal getValorResidualMesAnterior() {
		return this.valorResidualMesAnterior;
	}

	public void setValorResidualMesAnterior(BigDecimal valorResidualMesAnterior) {
		this.valorResidualMesAnterior = valorResidualMesAnterior;
	}

	public BigDecimal getValorCredito() {
		return this.valorCredito;
	}

	public void setValorCredito(BigDecimal valorCredito) {
		this.valorCredito = valorCredito;
	}

	public Short getNumeroPrestacaoCredito() {
		return this.numeroPrestacaoCredito;
	}

	public void setNumeroPrestacaoCredito(Short numeroPrestacaoCredito) {
		this.numeroPrestacaoCredito = numeroPrestacaoCredito;
	}

	public Short getNumeroPrestacaoRealizada() {
		return this.numeroPrestacaoRealizada;
	}

	public void setNumeroPrestacaoRealizada(Short numeroPrestacaoRealizada) {
		this.numeroPrestacaoRealizada = numeroPrestacaoRealizada;
	}

	public Integer getCodigoSetorComercial() {
		return this.codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial) {
		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra() {
		return this.numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra) {
		this.numeroQuadra = numeroQuadra;
	}

	public Short getNumeroLote() {
		return this.numeroLote;
	}

	public void setNumeroLote(Short numeroLote) {
		this.numeroLote = numeroLote;
	}

	public Short getNumeroSubLote() {
		return this.numeroSubLote;
	}

	public void setNumeroSubLote(Short numeroSubLote) {
		this.numeroSubLote = numeroSubLote;
	}

	public Date getUltimaAlteracao() {
		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public RegistroAtendimento getRegistroAtendimento() {
		return this.registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento) {
		this.registroAtendimento = registroAtendimento;
	}

	public Imovel getImovel() {
		return this.imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public OrdemServico getOrdemServico() {
		return this.ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Quadra getQuadra() {
		return this.quadra;
	}

	public void setQuadra(Quadra quadra) {
		this.quadra = quadra;
	}

	public Localidade getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public gcom.faturamento.credito.CreditoTipo getCreditoTipo() {
		return this.creditoTipo;
	}

	public void setCreditoTipo(gcom.faturamento.credito.CreditoTipo creditoTipo) {
		this.creditoTipo = creditoTipo;
	}

	public LancamentoItemContabil getLancamentoItemContabil() {
		return this.lancamentoItemContabil;
	}

	public void setLancamentoItemContabil(
			LancamentoItemContabil lancamentoItemContabil) {
		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public DebitoCreditoSituacao getDebitoCreditoSituacaoAtual() {
		return this.debitoCreditoSituacaoAtual;
	}

	public void setDebitoCreditoSituacaoAtual(
			DebitoCreditoSituacao debitoCreditoSituacaoAtual) {
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
	}

	public DebitoCreditoSituacao getDebitoCreditoSituacaoAnterior() {
		return this.debitoCreditoSituacaoAnterior;
	}

	public void setDebitoCreditoSituacaoAnterior(
			DebitoCreditoSituacao debitoCreditoSituacaoAnterior) {
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
	}

	public gcom.faturamento.credito.CreditoOrigem getCreditoOrigem() {
		return this.creditoOrigem;
	}

	public void setCreditoOrigem(
			gcom.faturamento.credito.CreditoOrigem creditoOrigem) {
		this.creditoOrigem = creditoOrigem;
	}

	public Parcelamento getParcelamento() {
		return this.parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento) {
		this.parcelamento = parcelamento;
	}

	public DocumentoTipo getDocumentoTipo() {
		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo) {
		this.documentoTipo = documentoTipo;
	}

	public Set getCreditoARealizarCategoria() {
		return this.creditoARealizarCategoria;
	}

	public void setCreditoARealizarCategoria(Set creditoARealizarCategoria) {
		this.creditoARealizarCategoria = creditoARealizarCategoria;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
	
	public Integer getAnoMesReferenciaPrestacao() {
		return anoMesReferenciaPrestacao;
	}

	public void setAnoMesReferenciaPrestacao(Integer anoMesReferenciaPrestacao) {
		this.anoMesReferenciaPrestacao = anoMesReferenciaPrestacao;
	}

	public String getFormatarAnoMesCobrancaCredito() {

		String anoMesRecebido = "" + this.getAnoMesCobrancaCredito();
		String mes = anoMesRecebido.substring(4, 6);
		String ano = anoMesRecebido.substring(0, 4);
		String anoMesFormatado = mes + "/" + ano;

		return anoMesFormatado.toString();
	}
	public String getFormatarAnoMesReferenciaCredito() {

		String anoMesRecebido = "" + this.getAnoMesReferenciaCredito();
		String mes = anoMesRecebido.substring(4, 6);
		String ano = anoMesRecebido.substring(0, 4);
		String anoMesFormatado = mes + "/" + ano;

		return anoMesFormatado.toString();
	}
	public String getFormatarAnoMesReferenciaContabil() {

		String anoMesRecebido = "" + this.getAnoMesReferenciaContabil();
		String mes = anoMesRecebido.substring(4, 6);
		String ano = anoMesRecebido.substring(0, 4);
		String anoMesFormatado = mes + "/" + ano;

		return anoMesFormatado.toString();
	}
	
	public BigDecimal getValorTotal() {

		BigDecimal retornoDivisao = new BigDecimal("0.00");
		BigDecimal retornoMultiplicacao = new BigDecimal("0.00");
		BigDecimal retornoSubtracao = new BigDecimal("0.00");
		BigDecimal retorno = new BigDecimal("0.00");

		retornoDivisao = Util.dividirArredondando(this.valorCredito,new BigDecimal(numeroPrestacaoCredito));

		retornoMultiplicacao = retornoDivisao.multiply(new BigDecimal(
				numeroPrestacaoRealizada));

		retornoSubtracao = this.valorCredito.subtract(retornoMultiplicacao);
		if (valorResidualMesAnterior != null) {
			retorno = retornoSubtracao.add(valorResidualMesAnterior);
		}else{
            retorno = retornoSubtracao;
        }
		retorno = retorno.setScale(2, BigDecimal.ROUND_HALF_UP);

		return retorno;
	}
	
	/**
 	 * Realiza o calculo de quantas parcelas falta para creditar  
 	 * 
 	 * @author Fernanda Paiva
 	 * @created 7 de Abril de 2006
 	*/
 	public short getParcelasRestante(){
 		
 	   short retorno = Short.parseShort(""+
               (getNumeroPrestacaoCredito() -  getNumeroPrestacaoRealizada()));
       
 		return retorno;
 	}
 	
 	public String[] retornaCamposChavePrimaria(){
		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}
	
	public Filtro retornaFiltro(){
		FiltroCreditoARealizar filtroCreditoARealizar = new FiltroCreditoARealizar();
		
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.REGISTRO_ATENDIMENTO);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.IMOVEL);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.ORDEM_SERVICO);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.QUADRA);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.LOCALIDADE);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.CREDITO_A_REALIZAR_CATEGORIA);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.CREDITO_TIPO);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.LANCAMENTO_ITEM_CONTABIL);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.DEBITO_CREDITO_SITUACAO_ATUAL_);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.DEBITO_CREDITO_SITUACAO_ANTERIOR_);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.CREDITO_ORIGEM);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.PARCELAMENTO);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.DOCUMENTO_TIPO);
		filtroCreditoARealizar.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoARealizar.USUARIO);
		
		filtroCreditoARealizar.adicionarParametro(
				new ParametroSimples(FiltroCreditoARealizar.ID, this.getId()));
		return filtroCreditoARealizar; 
	}

	/**
	 * @return Retorna o campo creditoARealizarGeral.
	 */
	public CreditoARealizarGeral getCreditoARealizarGeral() {
		return creditoARealizarGeral;
	}

	/**
	 * @param creditoARealizarGeral O creditoARealizarGeral a ser setado.
	 */
	public void setCreditoARealizarGeral(CreditoARealizarGeral creditoARealizarGeral) {
		this.creditoARealizarGeral = creditoARealizarGeral;
	}

	public CreditoARealizarGeral getOrigem() {
		return origem;
	}

	public void setOrigem(CreditoARealizarGeral origem) {
		this.origem = origem;
	}

	@Override
	public void initializeLazy() {
		retornaCamposChavePrimaria();
		if (getCreditoTipo() != null){
			getCreditoTipo().initializeLazy();
		}
		if (getCreditoOrigem() != null){
			getCreditoOrigem().initializeLazy();
		}
		if (registroAtendimento != null){
			getRegistroAtendimento().initializeLazy();
		}
		if (ordemServico != null){
			getOrdemServico().initializeLazy();
		}
	}
	
	public String[] retornarAtributosInformacoesOperacaoEfetuada(){
		String []atributos = {
				"creditoTipo.descricao"
				, "valorCredito"
				};
			return atributos;		
	}
	
	public String[] retornarLabelsInformacoesOperacaoEfetuada(){
		String []labels = {"Tipo Credito"
				, "Valor"
				};
			return labels;		
	}

	public Short getNumeroParcelaBonus() {
        return numeroParcelaBonus;
    }

    public void setNumeroParcelaBonus(Short numeroParcelaBonus) {
        this.numeroParcelaBonus = numeroParcelaBonus;
    }
   
    /**
     * Realiza o calculo de quantas parcelas falta para creditar  
     * numero total de presta��es menos
     * numero de parcelas realizadas menos
     * numero de parcelas bonus
     * 
     * @author Vivianne Sousa
     * @created 21/02/2008
    */
    public short getParcelasRestanteComBonus(){
        
       short retorno = Short.parseShort(""+
               (getNumeroPrestacaoCredito() -  getNumeroPrestacaoRealizada()));
       
      if (getNumeroParcelaBonus() != null){
          retorno = Short.parseShort(""+ (retorno - getNumeroParcelaBonus().shortValue()));
      }
            
        return retorno;
    }
    
    /**
     * @author Vivianne Sousa
     * @created 21/02/2008
    */
    public BigDecimal getValorTotalComBonus() {

        BigDecimal retornoDivisao = new BigDecimal("0.00");
        BigDecimal retornoMultiplicacao = new BigDecimal("0.00");
        BigDecimal retornoSubtracao = new BigDecimal("0.00");
        BigDecimal retorno = new BigDecimal("0.00");

        retornoDivisao = Util.dividirArredondando(this.valorCredito,new BigDecimal(numeroPrestacaoCredito));

        if (numeroParcelaBonus != null){
            retornoMultiplicacao = retornoDivisao.multiply(new BigDecimal(numeroPrestacaoRealizada + numeroParcelaBonus));
        }else{
            retornoMultiplicacao = retornoDivisao.multiply(new BigDecimal(numeroPrestacaoRealizada));
        }

        retornoSubtracao = this.valorCredito.subtract(retornoMultiplicacao);
        if (valorResidualMesAnterior != null) {
            retorno = retornoSubtracao.add(valorResidualMesAnterior);
        }else{
            retorno = retornoSubtracao;
        }
        retorno = retorno.setScale(2, BigDecimal.ROUND_HALF_UP);

        return retorno;
    }
    
    /**
     * @author Vivianne Sousa
     * @created 21/02/2008
    */
    public short getNumeroPrestacaoCreditoMenosBonus() {
        short retorno = getNumeroPrestacaoCredito();
        
        if (getNumeroParcelaBonus() != null){
            retorno = Short.parseShort(""+ (retorno - getNumeroParcelaBonus().shortValue()));
        }
             
        return retorno;
    }

	/**
	 * @return Retorna o campo usuario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario O usuario a ser setado.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public BigDecimal getValorPrestacao(){
        
        //truncando o resultado com 2 casas decimais
        BigDecimal retornoDivisao = 
            this.getValorCredito().divide(new BigDecimal(this.getNumeroPrestacaoCredito()),2,BigDecimal.ROUND_DOWN);
       
        return retornoDivisao;
    }
	
	public BigDecimal getValorAntecipacaoParcela(int quantidadePrestacoes){
    	
    	return this.getValorPrestacao().multiply(BigDecimal.valueOf(quantidadePrestacoes));
    }

	public Integer getNumeroParcelasAntecipadas() {
		return numeroParcelasAntecipadas;
	}

	public void setNumeroParcelasAntecipadas(Integer numeroParcelasAntecipadas) {
		this.numeroParcelasAntecipadas = numeroParcelasAntecipadas;
	}

	public BigDecimal getValorResidualConcedidoMes() {
		return valorResidualConcedidoMes;
	}

	public void setValorResidualConcedidoMes(BigDecimal valorResidualConcedidoMes) {
		this.valorResidualConcedidoMes = valorResidualConcedidoMes;
	}
}
