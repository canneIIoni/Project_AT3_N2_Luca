package model;

import java.io.Serializable;

public class Livro implements Serializable {
    private String titulo;
    private String autor;
    private String genero;
    private int exemplares;

    public Livro(String titulo, String autor, String genero, int exemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.exemplares = exemplares;
    }

    // Getters e Setters (mantidos como antes)

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getExemplares() {
        return exemplares;
    }

    public void setExemplares(int exemplares) {
        this.exemplares = exemplares;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, exemplares: %d",
                titulo, autor, genero, exemplares);
    }

    public static Livro fromJson(String json) {
        // Remover espaços em branco extras
        json = json.trim();

        // Encontrar os índices dos valores
        int startIndex = json.indexOf("\"titulo\": \"") + "\"titulo\": \"".length();
        int endIndex = json.indexOf("\",", startIndex);
        String titulo = json.substring(startIndex, endIndex);

        startIndex = json.indexOf("\"autor\": \"") + "\"autor\": \"".length();
        endIndex = json.indexOf("\",", startIndex);
        String autor = json.substring(startIndex, endIndex);

        startIndex = json.indexOf("\"genero\": \"") + "\"genero\": \"".length();
        endIndex = json.indexOf("\",", startIndex);
        String genero = json.substring(startIndex, endIndex);

        startIndex = json.indexOf("\"exemplares\": ") + "\"exemplares\": ".length();
        endIndex = json.indexOf("}", startIndex);
        int exemplares = Integer.parseInt(json.substring(startIndex, endIndex).trim());

        return new Livro(titulo, autor, genero, exemplares);
    }

    public String toJson() {
        return String.format("{\"titulo\": \"%s\", \"autor\": \"%s\", \"genero\": \"%s\", \"exemplares\": %d}",
                titulo, autor, genero, exemplares);
    }
}