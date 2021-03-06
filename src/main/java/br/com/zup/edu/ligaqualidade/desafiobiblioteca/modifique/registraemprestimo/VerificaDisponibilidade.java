package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.registraemprestimo;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.repository.ExemplarRepository;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.pronto.TipoExemplar;

public class VerificaDisponibilidade {

	ExemplarRepository exemplarRepository;
    EmprestimoConcedidoRepository emprestimoConcedidoRepository;

    public VerificaDisponibilidade(ExemplarRepository exemplarRepository,
            EmprestimoConcedidoRepository emprestimoConcedidoRepository){
        this.exemplarRepository = exemplarRepository;
        this.emprestimoConcedidoRepository = emprestimoConcedidoRepository;
    }

    public Integer getId(Integer idLivro, TipoExemplar tipoExemplar){

        Set<Integer> todosIdsExemplares = exemplarRepository.getIds(idLivro, tipoExemplar);
        Set<Integer> todosExemplaresComEmprestimoAtivo = emprestimoConcedidoRepository.getExemplaresComEmprestivoAtivos(todosIdsExemplares);
        Set<Integer> todosExemplaresDisponiveis = todosIdsExemplares.stream().filter(it -> !todosExemplaresComEmprestimoAtivo.contains(it)).collect(Collectors.toSet());

        return todosExemplaresDisponiveis.stream().findFirst().get();
    }

}
