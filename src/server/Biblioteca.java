package server;

import model.Livro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> livros;
    private final String filePath = "livros.json";

    public Biblioteca() {
        this.livros = carregarLivros();
    }

    private List<Livro> carregarLivros() {
        List<Livro> livros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder json = new StringBuilder();
            String linha;
            while ((linha = br.readLine()) != null) {
                json.append(linha);
            }
            String jsonString = json.toString();
            String[] livrosJson = jsonString.substring(jsonString.indexOf("[") + 1, jsonString.lastIndexOf("]")).split("},");
            for (String livroJson : livrosJson) {
                if (!livroJson.endsWith("}")) {
                    livroJson += "}";
                }
                livros.add(Livro.fromJson(livroJson));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public synchronized List<Livro> listarLivros() {
        return livros;
    }
}
