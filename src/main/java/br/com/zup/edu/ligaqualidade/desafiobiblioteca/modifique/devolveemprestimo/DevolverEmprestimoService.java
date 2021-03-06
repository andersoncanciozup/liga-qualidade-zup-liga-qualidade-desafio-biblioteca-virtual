package br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.devolveemprestimo;

import br.com.zup.edu.ligaqualidade.desafiobiblioteca.DadosDevolucao;
import br.com.zup.edu.ligaqualidade.desafiobiblioteca.modifique.registraemprestimo.EmprestimoConcedidoRepository;

import java.util.Set;

public class DevolverEmprestimoService {

    EmprestimoConcedidoRepository emprestimoConcedidoRepository;

    public DevolverEmprestimoService(EmprestimoConcedidoRepository emprestimoConcedidoRepository) {
        this.emprestimoConcedidoRepository = emprestimoConcedidoRepository;
    }

    public void devolver(Set<DadosDevolucao> devolucoes) {

        for (DadosDevolucao devolucao : devolucoes) {
            emprestimoConcedidoRepository.devolver(devolucao.idEmprestimo);
        }
    }
}
