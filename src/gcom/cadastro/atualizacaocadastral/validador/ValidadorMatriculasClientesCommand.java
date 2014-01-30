package gcom.cadastro.atualizacaocadastral.validador;

import gcom.cadastro.atualizacaocadastral.command.AtualizacaoCadastralImovel;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.IRepositorioClienteImovel;

import java.util.Collection;
import java.util.Map;

public class ValidadorMatriculasClientesCommand extends ValidadorCommand {

	private IRepositorioClienteImovel repositorioClienteImovel;

	public ValidadorMatriculasClientesCommand(AtualizacaoCadastralImovel cadastroImovel
			, Map<String, String> linha
			, IRepositorioClienteImovel repositorioClienteImovel) {
		super(cadastroImovel, linha);
		this.repositorioClienteImovel = repositorioClienteImovel;
	}
	
	public void execute() throws Exception{
		Collection<ClienteImovel> clientes = repositorioClienteImovel.pesquisarClienteImovel(cadastroImovel.getMatricula());
		
		validaProprietario(linha, clientes);

		validaResponsavel(linha, clientes);
	}

	private void validaResponsavel(Map<String, String> linha, Collection<ClienteImovel> clientes) {
		int matricula = Integer.valueOf(linha.get("matriculaResponsavel"));
		
		int base = -1;
		for (ClienteImovel cliente : clientes) {
			if (cliente.getClienteRelacaoTipo().getId().intValue() == (int) ClienteRelacaoTipo.RESPONSAVEL){
				base = cliente.getCliente().getId();
			}
		}

		if (base == -1 && matricula != 0){
			cadastroImovel.addMensagemErro("Matr�cula do respons�vel inexistente");
		}
		
		if (base != -1 && base != matricula){
			cadastroImovel.addMensagemErro("Inconsistencia na matr�cula do respons�vel");
		}
	}

	private void validaProprietario(Map<String, String> linha, Collection<ClienteImovel> clientes) {
		int matricula = Integer.valueOf(linha.get("matriculaProprietario"));
		
		int base = -1;
		for (ClienteImovel cliente : clientes) {
			if (cliente.getClienteRelacaoTipo().getId().intValue() == (int) ClienteRelacaoTipo.PROPRIETARIO){
				base = cliente.getCliente().getId();
			}
		}

		if (base == -1 && matricula != 0){
			cadastroImovel.addMensagemErro("Matr�cula do propriet�rio inexistente");
		}
		
		if (base != -1 && base != matricula){
			cadastroImovel.addMensagemErro("Inconsistencia na matr�cula do propriet�rio");
		}
	}
}
