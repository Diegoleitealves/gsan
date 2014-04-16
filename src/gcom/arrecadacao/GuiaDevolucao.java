package gcom.arrecadacao;
 
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.DocumentoTipo;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.DebitoACobrarGeral;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.seguranca.acesso.usuario.Usuario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class GuiaDevolucao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Date dataEmissao;

	/** nullable persistent field */
	private Date dataValidade;

	/** nullable persistent field */
	private BigDecimal valorDevolucao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Integer anoMesReferenciaContabil;

	/** persistent field */
	private LancamentoItemContabil lancamentoItemContabil;

	/** persistent field */
	private Cliente cliente;

	/** persistent field */
	private DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	/** persistent field */
	private DebitoCreditoSituacao debitoCreditoSituacaoAtual;

	/** persistent field */
	private RegistroAtendimento registroAtendimento;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private DocumentoTipo documentoTipo;

	/** persistent field */
	private Conta conta;

	/** persistent field */
	private OrdemServico ordemServico;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private CreditoTipo creditoTipo;

	/** persistent field */
	private GuiaPagamento guiaPagamento;

	/** persistent field */
	private Integer anoMesReferenciaGuiaDevolucao;

	/** persistent field */
	private DebitoTipo debitoTipo;

	/** persistent field */
	private DebitoACobrarGeral debitoACobrarGeral;

	/** persistent field */
	private Funcionario funcionarioAnalista;
	
	/** persistent field */
	private Funcionario funcionarioAutorizador;
	
	/** persistent field */
	private Usuario usuario;	
	

	/** full constructor */
	public GuiaDevolucao(Date dataEmissao, Date dataValidade,
			BigDecimal valorDevolucao, Date ultimaAlteracao,
			Integer anoMesReferenciaContabil,
			LancamentoItemContabil lancamentoItemContabil, Cliente cliente,
			DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
			DebitoCreditoSituacao debitoCreditoSituacaoAtual,
			RegistroAtendimento registroAtendimento, Imovel imovel,
			DocumentoTipo documentoTipo, Conta conta,
			OrdemServico ordemServico, Localidade localidade,
			CreditoTipo creditoTipo, GuiaPagamento guiaPagamento) {
		this.dataEmissao = dataEmissao;
		this.dataValidade = dataValidade;
		this.valorDevolucao = valorDevolucao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.cliente = cliente;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.documentoTipo = documentoTipo;
		this.conta = conta;
		this.ordemServico = ordemServico;
		this.localidade = localidade;
		this.creditoTipo = creditoTipo;
		this.guiaPagamento = guiaPagamento;
	}

	/** full constructor */
	public GuiaDevolucao(Date dataEmissao, Date dataValidade,
			BigDecimal valorDevolucao, Date ultimaAlteracao,
			Integer anoMesReferenciaContabil,
			LancamentoItemContabil lancamentoItemContabil, Cliente cliente,
			DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
			DebitoCreditoSituacao debitoCreditoSituacaoAtual,
			RegistroAtendimento registroAtendimento, Imovel imovel,
			DocumentoTipo documentoTipo, Conta conta,
			OrdemServico ordemServico, Localidade localidade,
			CreditoTipo creditoTipo, GuiaPagamento guiaPagamento,
			Integer anoMesReferenciaGuiaDevolucao, DebitoTipo debitoTipo,
			DebitoACobrarGeral debitoACobrarGeral, Funcionario funcionarioAnalista, 
			Funcionario funcionarioAutorizador, Usuario usuario) {
		this.dataEmissao = dataEmissao;
		this.dataValidade = dataValidade;
		this.valorDevolucao = valorDevolucao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.cliente = cliente;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.documentoTipo = documentoTipo;
		this.conta = conta;
		this.ordemServico = ordemServico;
		this.localidade = localidade;
		this.creditoTipo = creditoTipo;
		this.guiaPagamento = guiaPagamento;
		this.guiaPagamento = guiaPagamento;
		this.guiaPagamento = guiaPagamento;
		this.anoMesReferenciaGuiaDevolucao = anoMesReferenciaGuiaDevolucao;
		this.debitoTipo = debitoTipo;
		this.debitoACobrarGeral = debitoACobrarGeral;
		this.funcionarioAnalista = funcionarioAnalista;
		this.funcionarioAutorizador = funcionarioAutorizador;
		this.usuario = usuario;
	}

	/** default constructor */
	public GuiaDevolucao() {
	}

	/** minimal constructor */
	public GuiaDevolucao(LancamentoItemContabil lancamentoItemContabil,
			Cliente cliente,
			DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
			DebitoCreditoSituacao debitoCreditoSituacaoAtual,
			RegistroAtendimento registroAtendimento, Imovel imovel,
			DocumentoTipo documentoTipo, Conta conta,
			OrdemServico ordemServico, Localidade localidade,
			CreditoTipo creditoTipo, GuiaPagamento guiaPagamento) {
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.cliente = cliente;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.documentoTipo = documentoTipo;
		this.conta = conta;
		this.ordemServico = ordemServico;
		this.localidade = localidade;
		this.creditoTipo = creditoTipo;
		this.guiaPagamento = guiaPagamento;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataEmissao() {
		return this.dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataValidade() {
		return this.dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public BigDecimal getValorDevolucao() {
		return this.valorDevolucao;
	}

	public void setValorDevolucao(BigDecimal valorDevolucao) {
		this.valorDevolucao = valorDevolucao;
	}

	public Date getUltimaAlteracao() {
		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getAnoMesReferenciaContabil() {
		return this.anoMesReferenciaContabil;
	}

	public void setAnoMesReferenciaContabil(Integer anoMesReferenciaContabil) {
		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
	}

	public LancamentoItemContabil getLancamentoItemContabil() {
		return this.lancamentoItemContabil;
	}

	public void setLancamentoItemContabil(
			LancamentoItemContabil lancamentoItemContabil) {
		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DebitoCreditoSituacao getDebitoCreditoSituacaoAnterior() {
		return this.debitoCreditoSituacaoAnterior;
	}

	public void setDebitoCreditoSituacaoAnterior(
			DebitoCreditoSituacao debitoCreditoSituacaoAnterior) {
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
	}

	public DebitoCreditoSituacao getDebitoCreditoSituacaoAtual() {
		return this.debitoCreditoSituacaoAtual;
	}

	public void setDebitoCreditoSituacaoAtual(
			DebitoCreditoSituacao debitoCreditoSituacaoAtual) {
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
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

	public DocumentoTipo getDocumentoTipo() {
		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo) {
		this.documentoTipo = documentoTipo;
	}

	public Conta getConta() {
		return this.conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public OrdemServico getOrdemServico() {
		return this.ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Localidade getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public CreditoTipo getCreditoTipo() {
		return this.creditoTipo;
	}

	public void setCreditoTipo(CreditoTipo creditoTipo) {
		this.creditoTipo = creditoTipo;
	}

	public GuiaPagamento getGuiaPagamento() {
		return this.guiaPagamento;
	}

	public void setGuiaPagamento(GuiaPagamento guiaPagamento) {
		this.guiaPagamento = guiaPagamento;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Integer getAnoMesReferenciaGuiaDevolucao() {
		return anoMesReferenciaGuiaDevolucao;
	}

	public void setAnoMesReferenciaGuiaDevolucao(
			Integer anoMesReferenciaGuiaDevolucao) {
		this.anoMesReferenciaGuiaDevolucao = anoMesReferenciaGuiaDevolucao;
	}

	public DebitoTipo getDebitoTipo() {
		return debitoTipo;
	}

	public void setDebitoTipo(DebitoTipo debitoTipo) {
		this.debitoTipo = debitoTipo;
	}

	
	public DebitoACobrarGeral getDebitoACobrarGeral() {
		return debitoACobrarGeral;
	}

	public void setDebitoACobrarGeral(DebitoACobrarGeral debitoACobrarGeral) {
		this.debitoACobrarGeral = debitoACobrarGeral;
	}

	public String[] retornaCamposChavePrimaria(){
		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/**
	 * @return Retorna o campo funcionarioAnalista.
	 */
	public Funcionario getFuncionarioAnalista() {
		return funcionarioAnalista;
	}

	/**
	 * @param funcionarioAnalista O funcionarioAnalista a ser setado.
	 */
	public void setFuncionarioAnalista(Funcionario funcionarioAnalista) {
		this.funcionarioAnalista = funcionarioAnalista;
	}

	/**
	 * @return Retorna o campo funcionarioAutorizador.
	 */
	public Funcionario getFuncionarioAutorizador() {
		return funcionarioAutorizador;
	}

	/**
	 * @param funcionarioAutorizador O funcionarioAutorizador a ser setado.
	 */
	public void setFuncionarioAutorizador(Funcionario funcionarioAutorizador) {
		this.funcionarioAutorizador = funcionarioAutorizador;
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

}
