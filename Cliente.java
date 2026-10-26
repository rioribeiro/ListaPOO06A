package listaPOO06A;

public class Cliente {

    private int id;
    private String nome;
    private String email;
    private String fone;

    public Cliente(int id, String nome, String email, String fone) {
        setId(id);
        setNome(nome);
        setEmail(email);
        setFone(fone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("O ID do cliente deve ser maior que zero.");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("O e-mail do cliente não pode ser vazio.");
        }
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        if (fone != null && !fone.trim().isEmpty()) {
            this.fone = fone;
        } else {
            throw new IllegalArgumentException("O telefone do cliente não pode ser vazio.");
        }
    }

    @Override
    public String toString() {
        return String.format("Cliente #%d | Nome: %s | E-mail: %s | Fone: %s", id, nome, email, fone);
    }
}
