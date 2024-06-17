package model;

public class Livro {
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

    // Getters e Setters

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
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                ", exemplares=" + exemplares +
                '}';
    }

    public String toJson() {
        return String.format("{\"titulo\": \"%s\", \"autor\": \"%s\", \"genero\": \"%s\", \"exemplares\": %d}",
                titulo, autor, genero, exemplares);
    }

    public static Livro fromJson(String json) {
        String[] parts = json.split("\"");
        String titulo = parts[3];
        String autor = parts[7];
        String genero = parts[11];
        int exemplares = Integer.parseInt(parts[14].split(": ")[1]);
        return new Livro(titulo, autor, genero, exemplares);
    }
}
