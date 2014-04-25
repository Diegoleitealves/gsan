package gcom.micromedicao.bean;


public class RelatorioAnaliseConsumoHelper {

	private String idLocalidade;
	private String codigoSetorComercial;
	private String numeroQuadra;
	private String lote;
	private String subLote;
	private String matriculaImovel;
	private String rota;
	private String seqRota;
	private String endereco;
	private String qtdEconomias;
	private String categoria;
	private String usuario;
	private String leituraAnterior;
	private String leituraAtual;
	private String hidrometro;
	private String anoMesFaturamento;
	
	//******************************************************************************
	// autor: Ivan S�rgio
	// data: 24/07/2008
	// solicitante: Leonardo Vieira
	// Caso a Empresa selecionada NAO seja COMPESA nao informar a Leitura Atual;
	// Exibir a descricao da Leitura Anormalidade Informada;
	//******************************************************************************
	private String descricaoLeituraAnormalidadeInformada;
	private String indicadorExibirLeituraAtual;
	
	public RelatorioAnaliseConsumoHelper(
			String idLocalidade,
			String codigoSetorComercial,
			String numeroQuadra,
			String lote,
			String subLote,
			String matriculaImovel,
			String rota,
			String seqRota,
			String endereco,
			String qtdEconomias,
			String categoria,
			String usuario){
		
			this.idLocalidade = idLocalidade;
			this.codigoSetorComercial = codigoSetorComercial;
			this.numeroQuadra = numeroQuadra;
			this.lote = lote;
			this.subLote = subLote;
			this.matriculaImovel = matriculaImovel;
			this.rota = rota;
			this.seqRota = seqRota;
			this.endereco = endereco;
			this.qtdEconomias = qtdEconomias;
			this.categoria = categoria;
			this.usuario = usuario;
	}
	
	public RelatorioAnaliseConsumoHelper(
			String idLocalidade,
			String codigoSetorComercial,
			String numeroQuadra,
			String lote,
			String subLote,
			String matriculaImovel,
			String rota,
			String seqRota,
			String endereco,
			String qtdEconomias,
			String categoria,
			String usuario,
			String leituraAnterior,
			String leituraAtual,
			String hidrometro){
		
			this.idLocalidade = idLocalidade;
			this.codigoSetorComercial = codigoSetorComercial;
			this.numeroQuadra = numeroQuadra;
			this.lote = lote;
			this.subLote = subLote;
			this.matriculaImovel = matriculaImovel;
			this.rota = rota;
			this.seqRota = seqRota;
			this.endereco = endereco;
			this.qtdEconomias = qtdEconomias;
			this.categoria = categoria;
			this.usuario = usuario;
			this.leituraAnterior = leituraAnterior;
			this.leituraAtual = leituraAtual;
			this.hidrometro = hidrometro;
	}
	
	public RelatorioAnaliseConsumoHelper(){}

	
	
	public String getAnoMesFaturamento() {
		return anoMesFaturamento;
	}

	public void setAnoMesFaturamento(String anoMesFaturamento) {
		this.anoMesFaturamento = anoMesFaturamento;
	}

	public String getHidrometro() {
		return hidrometro;
	}

	public void setHidrometro(String hidrometro) {
		this.hidrometro = hidrometro;
	}

	public String getLeituraAnterior() {
		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior) {
		this.leituraAnterior = leituraAnterior;
	}

	public String getLeituraAtual() {
		return leituraAtual;
	}

	public void setLeituraAtual(String leituraAtual) {
		this.leituraAtual = leituraAtual;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCodigoSetorComercial() {
		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial) {
		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getIdLocalidade() {
		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade) {
		this.idLocalidade = idLocalidade;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getMatriculaImovel() {
		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
	}

	
	public String getNumeroQuadra() {
		return numeroQuadra;
	}

	public void setNumeroQuadra(String numeroQuadra) {
		this.numeroQuadra = numeroQuadra;
	}

	public String getRota() {
		return rota;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}

	public String getSeqRota() {
		return seqRota;
	}

	public void setSeqRota(String seqRota) {
		this.seqRota = seqRota;
	}

	public String getSubLote() {
		return subLote;
	}

	public void setSubLote(String subLote) {
		this.subLote = subLote;
	}
	
	
	public String getInscricaoImovelFormatada() {
		String inscricao = null;

		String zeroUm = "0";
		String zeroDois = "00";
		String zeroTres = "000";

		String localidade, setorComercial, quadra, lote, subLote;

		localidade = this.getIdLocalidade();
		setorComercial = this.getCodigoSetorComercial();
		quadra = this.getNumeroQuadra();
		lote = this.getLote();
		subLote = this.getSubLote();

		if (this.getIdLocalidade().length() < 3 && this.getIdLocalidade().length() > 1) {
			localidade = zeroUm + this.getIdLocalidade();
		} else if (this.getIdLocalidade().length() < 3) {
			localidade = zeroDois + this.getIdLocalidade();
		}

		if (this.getCodigoSetorComercial().length() < 3 && this.getCodigoSetorComercial().length() > 1) {
			setorComercial = zeroUm + this.getCodigoSetorComercial();
		} else if (this.getCodigoSetorComercial().length() < 3) {
			setorComercial = zeroDois + this.getCodigoSetorComercial();
		}

		if (this.getNumeroQuadra().length() < 3 && this.getNumeroQuadra().length() > 1) {
			quadra = zeroUm + this.getNumeroQuadra();
		} else if (this.getNumeroQuadra().length() < 3) {
			quadra = zeroDois + this.getNumeroQuadra();
		}

		if (this.getLote().length() < 4 && this.getLote().length() > 2) {
			lote = zeroUm + this.getLote();
		} else if (this.getLote().length() < 3 && this.getLote().length() > 1) {
			lote = zeroDois + this.getLote();
		} else if (this.getLote().length() < 2) {
			lote = zeroTres + this.getLote();
		}

		if (this.getSubLote().length() < 3 && this.getSubLote().length() > 1) {
			subLote = zeroUm + this.getSubLote();
		} else if (this.getSubLote().length() < 3) {
			subLote = zeroDois + this.getSubLote();
		}

		inscricao = localidade + "." + setorComercial + "." + quadra + "."
				+ lote + "." + subLote;

		return inscricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getQtdEconomias() {
		return qtdEconomias;
	}

	public void setQtdEconomias(String qtdEconomias) {
		this.qtdEconomias = qtdEconomias;
	}

	public String getDescricaoLeituraAnormalidadeInformada() {
		return descricaoLeituraAnormalidadeInformada;
	}

	public void setDescricaoLeituraAnormalidadeInformada(
			String descricaoLeituraAnormalidadeInformada) {
		this.descricaoLeituraAnormalidadeInformada = descricaoLeituraAnormalidadeInformada;
	}

	public String getIndicadorExibirLeituraAtual() {
		return indicadorExibirLeituraAtual;
	}

	public void setIndicadorExibirLeituraAtual(String indicadorExibirLeituraAtual) {
		this.indicadorExibirLeituraAtual = indicadorExibirLeituraAtual;
	}


}
