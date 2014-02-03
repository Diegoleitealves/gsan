package gcom.cadastro.atualizacaocadastral.validador;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import gcom.cadastro.atualizacaocadastral.command.AtualizacaoCadastralImovel;

public class ValidadorCepClienteProprietarioResponsavel extends ValidadorCommand {

	public ValidadorCepClienteProprietarioResponsavel(AtualizacaoCadastralImovel cadastroImovel, Map<String, String> linha) {
		super(cadastroImovel, linha);
	}
	
	@Override
	public void execute() throws Exception {
		validarCepClienteProprietario(cadastroImovel, linha);
		validarCepClienteResposanvel(cadastroImovel, linha);
	}

	private void validarCepClienteProprietario(AtualizacaoCadastralImovel cadastroImovel, Map<String, String> linha) {
		String matriculaProprietario = linha.get("matriculaProprietario"); 
		String cepProprietario = linha.get("cepProprietario");
		String nomeProprietario = linha.get("nomeProprietario");
		if((!StringUtils.isEmpty(matriculaProprietario) && !StringUtils.containsOnly(matriculaProprietario,  new char[]{'0'}))
			|| (StringUtils.containsOnly(nomeProprietario,  new char[]{'0'}) && !StringUtils.isEmpty(nomeProprietario))) {
			if(StringUtils.isEmpty(cepProprietario)) {
				cadastroImovel.addMensagemErro("E obrigat�rio o preenchimento do CEP para o cliente Propriet�rio.");
				return;
			}
			
			if(!StringUtils.isNumeric(cepProprietario) || StringUtils.containsOnly(cepProprietario,  new char[]{'0'})){
				cadastroImovel.addMensagemErro("N�mero do CEP do cliente Propriet�rio � inv�lido");
			}
		}
	}
	
	private void validarCepClienteResposanvel(AtualizacaoCadastralImovel cadastroImovel, Map<String, String> linha) {
		String matriculaResponsavel = linha.get("matriculaResponsavel"); 
		String cepResponsavel = linha.get("cepResponsavel");
		String nomeResponsavel = linha.get("nomeResponsavel");
		if((!StringUtils.isEmpty(matriculaResponsavel) && !StringUtils.containsOnly(matriculaResponsavel,  new char[]{'0'}))
			|| (StringUtils.containsOnly(matriculaResponsavel,  new char[]{'0'}) && !StringUtils.isEmpty(nomeResponsavel))) {
			if(StringUtils.isEmpty(cepResponsavel)) {
				cadastroImovel.addMensagemErro("� obrigat�rio o preenchimento do CEP para o cliente Respons�vel.");
				return;
			}
				
			if(!StringUtils.isNumeric(cepResponsavel) || StringUtils.containsOnly(cepResponsavel,  new char[]{'0'})){
				cadastroImovel.addMensagemErro("N�mero do CEP do cliente Respons�vel � inv�lido");
			}
		}
	}
}
