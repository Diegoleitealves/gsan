package gcom.cadastro.atualizacaocadastral.validador;

import gcom.cadastro.atualizacaocadastral.command.AtualizacaoCadastralImovel;
import gcom.util.ParserUtil;

public class ValidadorTamanhoLinhaServicosCommand extends ValidadorCommand {
	private ParserUtil parser;

	public ValidadorTamanhoLinhaServicosCommand(ParserUtil parser, AtualizacaoCadastralImovel cadastroImovel) {
		super(cadastroImovel, null);
		this.parser = parser;
	}

	@Override
	public void execute() {
		if (parser.getFonte().length() != 81){
			cadastroImovel.addMensagemErroLayout("Linha Tipo 04 (Servi�os) n�o compat�vel com o Layout");
		}
	}
}
