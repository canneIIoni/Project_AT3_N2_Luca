package server;

import model.Livro;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor pronto para receber conexões...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());

                new Thread(() -> {
                    try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

                        String comando = (String) ois.readObject();
                        switch (comando) {
                            case "listar":
                                List<Livro> listaLivros = biblioteca.listarLivros();
                                oos.writeObject(listaLivros);
                                break;
                            case "alugar":
                                String tituloAlugar = (String) ois.readObject();
                                boolean alugado = biblioteca.alugarLivro(tituloAlugar);
                                oos.writeObject(alugado);
                                break;
                            case "devolver":
                                String tituloDevolver = (String) ois.readObject();
                                boolean devolvido = biblioteca.devolverLivro(tituloDevolver);
                                oos.writeObject(devolvido);
                                break;
                            case "cadastrar":
                                Livro novoLivro = (Livro) ois.readObject();
                                boolean cadastrado = biblioteca.cadastrarLivro(novoLivro);
                                oos.writeObject(cadastrado);
                                break;
                            default:
                                System.out.println("Comando inválido: " + comando);
                                break;
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
