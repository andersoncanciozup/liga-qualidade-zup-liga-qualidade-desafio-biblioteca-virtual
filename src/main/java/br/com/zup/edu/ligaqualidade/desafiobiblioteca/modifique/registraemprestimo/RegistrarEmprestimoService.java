package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.registraemprestimo;

import java.time.LocalDate;
import java.util.Set;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosEmprestimo;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.EmprestimoConcedido;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.ExemplarRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.UsuarioRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.DadosUsuario;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoUsuario;

public class RegistrarEmprestimoService {

    UsuarioRepository usuarioRepository;
    EmprestimoConcedidoRepository emprestimoConcedidoRepository;
    VerificaDisponibilidade exemplarDisponivelService;

    public RegistrarEmprestimoService(UsuarioRepository usuarioRepository,
                                      ExemplarRepository exemplarRepository,
                                      EmprestimoConcedidoRepository emprestimoConcedidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.emprestimoConcedidoRepository = emprestimoConcedidoRepository;
        this.exemplarDisponivelService = new VerificaDisponibilidade(exemplarRepository, emprestimoConcedidoRepository);
    }

    public void registrar(Set<DadosEmprestimo> emprestimos) {
    	
        for (DadosEmprestimo emprestimo : emprestimos) {
        	
            if (emprestimo.tempo > 60) {
                continue;
            }

            DadosUsuario dadosUsuario = usuarioRepository.get(emprestimo.idUsuario);
            if (TipoUsuario.PADRAO.equals(dadosUsuario.padrao) && TipoExemplar.RESTRITO.equals(emprestimo.tipoExemplar)) {
                continue;
            }

            Integer idExemplar = exemplarDisponivelService.getId(emprestimo.idLivro, emprestimo.tipoExemplar);

            EmprestimoConcedido emprestimoConcedido = new EmprestimoConcedido(emprestimo.idPedido, emprestimo.idUsuario,
                    idExemplar,
                    LocalDate.now().plusDays(emprestimo.tempo));

            emprestimoConcedidoRepository.regitrar(emprestimoConcedido);
        }
    }
}
