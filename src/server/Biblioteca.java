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

    private void salvarLivros() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("{\"livros\": [");
            for (int i = 0; i < livros.size(); i++) {
                bw.write(livros.get(i).toJson());
                if (i < livros.size() - 1) {
                    bw.write(",");
                }
            }
            bw.write("]}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Livro> listarLivros() {
        return livros;
    }

    public synchronized boolean alugarLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getExemplares() > 0) {
                livro.setExemplares(livro.getExemplares() - 1);
                salvarLivros();
                return true;
            }
        }
        return false;
    }

    public synchronized boolean devolverLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livro.setExemplares(livro.getExemplares() + 1);
                salvarLivros();
                return true;
            }
        }
        return false;
    }
}