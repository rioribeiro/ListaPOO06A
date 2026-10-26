package listaPOO06A;

import java.util.List;
import java.util.Scanner;

public class ClienteUI {

    private static final ClienteDAO dao = new ClienteDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static int Menu() {
        System.out.println("\n=== SISTEMA DE COMÉRCIO ELETRÔNICO - CLIENTES ===");
        System.out.println("1 - Listar Clientes");
        System.out.println("2 - Inserir Cliente");
        System.out.println("3 - Atualizar Cliente");
        System.out.println("4 - Excluir Cliente");
        System.out.println("0 - Finalizar Aplicação");
        System.out.print("Escolha uma opção: ");
        return sc.nextInt();
    }

    public static void Cliente_Listar() {
        List<Cliente> clientes = dao.Listar();
        if (clientes.isEmpty()) {
            System.out.println("\nNenhum cliente cadastrado.");
        } else {
            System.out.println("\n--- RELAÇÃO DE CLIENTES CADASTRADOS ---");
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    public static void Cliente_Inserir() {
        try {
            System.out.print("\nID do Cliente: ");
            int id = sc.nextInt();
            sc.nextLine(); // Limpa buffer do Scanner

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("E-mail: ");
            String email = sc.nextLine();

            System.out.print("Telefone: ");
            String fone = sc.nextLine();

            Cliente c = new Cliente(id, nome, email, fone);
            dao.Inserir(c);
            dao.Salvar(); // Salva automaticamente no arquivo JSON
            System.out.println("[SUCESSO] Cliente cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERRO DE DIGITAÇÃO]: Entrada inválida.");
            sc.nextLine();
        }
    }

    public static void Cliente_Atualizar() {
        try {
            System.out.print("\nInforme o ID do cliente que deseja atualizar: ");
            int id = sc.nextInt();
            sc.nextLine();

            Cliente c = dao.Listar_Id(id);
            if (c == null) {
                System.out.println("[ERRO]: Cliente não encontrado com o ID " + id);
                return;
            }

            System.out.println("Dados atuais: " + c);
            System.out.print("Novo Nome: ");
            String nome = sc.nextLine();

            System.out.print("Novo E-mail: ");
            String email = sc.nextLine();

            System.out.print("Novo Telefone: ");
            String fone = sc.nextLine();

            Cliente novo = new Cliente(id, nome, email, fone);
            dao.Atualizar(novo);
            dao.Salvar();
            System.out.println("[SUCESSO] Dados do cliente atualizados!");

        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO DE VALIDAÇÃO]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERRO DE DIGITAÇÃO]: Entrada inválida.");
            sc.nextLine();
        }
    }

    public static void Cliente_Excluir() {
        try {
            System.out.print("\nInforme o ID do cliente que deseja excluir: ");
            int id = sc.nextInt();
            sc.nextLine();

            Cliente c = dao.Listar_Id(id);
            if (c == null) {
                System.out.println("[ERRO]: Cliente não encontrado com o ID " + id);
                return;
            }

            dao.Excluir(c);
            dao.Salvar();
            System.out.println("[SUCESSO] Cliente '" + c.getNome() + "' removido da lista!");

        } catch (Exception e) {
            System.out.println("[ERRO]: Falha ao excluir (" + e.getMessage() + ")");
            sc.nextLine();
        }
    }

    public static void Main() {
        dao.Abrir(); 
        int opcao = -1;

        while (opcao != 0) {
            try {
                opcao = Menu();
                switch (opcao) {
                    case 1 -> Cliente_Listar();
                    case 2 -> Cliente_Inserir();
                    case 3 -> Cliente_Atualizar();
                    case 4 -> Cliente_Excluir();
                    case 0 -> System.out.println("\nEncerrando a aplicação...");
                    default -> System.out.println("\nOpção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("[ERRO]: Opção inválida informada.");
                sc.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Main();
    }
}
