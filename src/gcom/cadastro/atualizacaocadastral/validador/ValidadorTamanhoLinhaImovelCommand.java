package gcom.cadastro.atualizacaocadastral.validador;

import gcom.cadastro.atualizacaocadastral.command.AtualizacaoCadastralImovel;
import gcom.util.ParserUtil;

public class ValidadorTamanhoLinhaImovelCommand extends ValidadorCommand {

	private ParserUtil parser;
	
	public ValidadorTamanhoLinhaImovelCommand(ParserUtil parser, AtualizacaoCadastralImovel imovel) {
		super(imovel, null);
		this.parser = parser;
		
	}
	
	@Override
	public void execute() {
		if (parser.getFonte().length() != 370){
			cadastroImovel.addMensagemErroLayout("A linha Tipo 02 n�o est� compat�vel ao definido no leiaute");
		}
	}

}
