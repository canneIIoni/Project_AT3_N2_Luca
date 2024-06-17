package client;

import model.Livro;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientBiblioteca {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado ao servidor.");

            while (true) {
                exibirMenu();
                String opcao = scanner.nextLine().trim().toLowerCase();

                switch (opcao) {
                    case "1":
                        System.out.println("Enviando requisição para listar livros...");
                        oos.writeObject("listar");
                        oos.flush(); // Certifique-se de que os dados são enviados imediatamente
                        System.out.println("Aguardando resposta do servidor...");
                        List<Livro> listaLivros = (List<Livro>) ois.readObject();
                        System.out.println("Lista de Livros:");
                        for (Livro livro : listaLivros) {
                            System.out.println(livro);
                        }
                        break;
                    case "2":
                        System.out.print("Digite o título do livro que deseja alugar: ");
                        String tituloAlugar = scanner.nextLine().trim();
                        System.out.println("Enviando requisição para alugar livro...");
                        oos.writeObject("alugar");
                        oos.writeObject(tituloAlugar);
                        oos.flush();
                        System.out.println("Aguardando resposta do servidor...");
                        boolean alugado = (boolean) ois.readObject();
                        if (alugado) {
                            System.out.println("Livro alugado com sucesso.");
                        } else {
                            System.out.println("Não foi possível alugar o livro.");
                        }
                        break;
                    case "3":
                        System.out.print("Digite o título do livro que deseja devolver: ");
                        String tituloDevolver = scanner.nextLine().trim();
                        System.out.println("Enviando requisição para devolver livro...");
                        oos.writeObject("devolver");
                        oos.writeObject(tituloDevolver);
                        oos.flush();
                        System.out.println("Aguardando resposta do servidor...");
                        boolean devolvido = (boolean) ois.readObject();
                        if (devolvido) {
                            System.out.println("Livro devolvido com sucesso.");
                        } else {
                            System.out.println("Não foi possível devolver o livro.");
                        }
                        break;
                    case "4":
                        Livro novoLivro = cadastrarLivro(scanner);
                        if (novoLivro != null) {
                            System.out.println("Enviando requisição para cadastrar novo livro...");
                            oos.writeObject("cadastrar");
                            oos.writeObject(novoLivro);
                            oos.flush();
                            System.out.println("Aguardando resposta do servidor...");
                            boolean cadastrado = (boolean) ois.readObject();
                            if (cadastrado) {
                                System.out.println("Livro cadastrado com sucesso.");
                            } else {
                                System.out.println("Não foi possível cadastrar o livro.");
                            }
                        }
                        break;
                    case "5":
                        System.out.println("Saindo do sistema...");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void exibirMenu() {
        System.out.println("\n### Menu ###");
        System.out.println("1 - Listar livros");
        System.out.println("2 - Alugar livro");
        System.out.println("3 - Devolver livro");
        System.out.println("4 - Cadastrar novo livro");
        System.out.println("5 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static Livro cadastrarLivro(Scanner scanner) {
        System.out.print("Digite o título do novo livro: ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Digite o autor do novo livro: ");
        String autor = scanner.nextLine().trim();

        System.out.print("Digite o gênero do novo livro: ");
        String genero = scanner.nextLine().trim();

        int exemplares = solicitarNumero("Digite a quantidade de exemplares do novo livro: ", scanner);

        return new Livro(titulo, autor, genero, exemplares);
    }

    private static int solicitarNumero(String mensagem, Scanner scanner) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }
}