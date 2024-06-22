import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuadroKanban {
    private List<Coluna> colunas;

    public QuadroKanban() {
        this.colunas = new ArrayList<>();
    }

    public void adicionarColuna(Coluna coluna) {
        colunas.add(coluna);
    }

    public void removerColuna(Coluna coluna) {
        colunas.remove(coluna);
    }

    public List<Coluna> getColunas() {
        return colunas;
    }

    public Coluna getColunaPorNome(String nome) {
        for (Coluna coluna : colunas) {
            if (coluna.getNome().equalsIgnoreCase(nome)) {
                return coluna;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Coluna coluna : colunas) {
            sb.append(coluna).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuadroKanban quadro = new QuadroKanban();

        Coluna colunaToDo = new Coluna("A fazer");
        Coluna colunaInProgress = new Coluna("Fazendo");
        Coluna colunaDone = new Coluna("Feito");

        quadro.adicionarColuna(colunaToDo);
        quadro.adicionarColuna(colunaInProgress);
        quadro.adicionarColuna(colunaDone);

        boolean running = true;

        while (running) {
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Mover Tarefa");
            System.out.println("3. Exibir Quadro");
            System.out.println("4. Visualizar Tarefa");
            System.out.println("5. Sair");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    System.out.print("Título da tarefa: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Descrição da tarefa: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Coluna (A fazer, Fazendo, Feito): ");
                    String nomeColuna = scanner.nextLine();

                    Coluna coluna = quadro.getColunaPorNome(nomeColuna);
                    if (coluna != null) {
                        Tarefa tarefa = new Tarefa(titulo, descricao);
                        coluna.adicionarTarefa(tarefa);
                        System.out.println("Tarefa adicionada!");
                    } else {
                        System.out.println("Coluna não encontrada.");
                    }
                    break;

                case 2:
                    System.out.print("Título da tarefa a mover: ");
                    String tituloMover = scanner.nextLine();
                    System.out.print("Coluna atual: ");
                    String colunaAtual = scanner.nextLine();
                    System.out.print("Coluna de destino: ");
                    String colunaDestino = scanner.nextLine();

                    Coluna colunaOrigem = quadro.getColunaPorNome(colunaAtual);
                    Coluna colunaDestinoObj = quadro.getColunaPorNome(colunaDestino);

                    if (colunaOrigem != null && colunaDestinoObj != null) {
                        Tarefa tarefaMover = colunaOrigem.getTarefaPorTitulo(tituloMover);

                        if (tarefaMover != null) {
                            colunaOrigem.removerTarefa(tarefaMover);
                            colunaDestinoObj.adicionarTarefa(tarefaMover);
                            System.out.println("Tarefa movida!");
                        } else {
                            System.out.println("Tarefa não encontrada na coluna " + colunaAtual);
                        }
                    } else {
                        System.out.println("Coluna(s) não encontrada(s).");
                    }
                    break;

                case 3:
                    System.out.println(quadro);
                    break;

                case 4:
                    System.out.print("Título da tarefa a visualizar: ");
                    String tituloVisualizar = scanner.nextLine();
                    boolean tarefaEncontrada = false;
                    for (Coluna c : quadro.getColunas()) {
                        Tarefa tarefa = c.getTarefaPorTitulo(tituloVisualizar);
                        if (tarefa != null) {
                            System.out.println("Detalhes da Tarefa:");
                            System.out.println("Título: " + tarefa.getTitulo());
                            System.out.println("Descrição: " + tarefa.getDescricao());
                            tarefaEncontrada = true;
                            break;
                        }
                    }
                    if (!tarefaEncontrada) {
                        System.out.println("Tarefa não encontrada.");
                    }
                    break;

                case 5:
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }
}
