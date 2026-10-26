package listaPOO06A;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private List<Cliente> objetos = new ArrayList<>();
    private static final String ARQUIVO_JSON = "clientes.json";

    public void Inserir(Cliente obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Objeto cliente não pode ser nulo.");
        }
        if (Listar_Id(obj.getId()) != null) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com o ID " + obj.getId());
        }
        objetos.add(obj);
    }

    public List<Cliente> Listar() {
        return objetos;
    }

    public Cliente Listar_Id(int id) {
        for (Cliente c : objetos) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public void Atualizar(Cliente obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Objeto cliente não pode ser nulo.");
        }
        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getId() == obj.getId()) {
                objetos.set(i, obj);
                return;
            }
        }
        throw new IllegalArgumentException("Cliente com ID " + obj.getId() + " não foi encontrado para atualização.");
    }

    public void Excluir(Cliente obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Objeto cliente não pode ser nulo.");
        }
        objetos.removeIf(c -> c.getId() == obj.getId());
    }

    public void Abrir() {
        File file = new File(ARQUIVO_JSON);
        if (!file.exists()) {
            objetos = new ArrayList<>();
            return;
        }

        objetos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{") && line.endsWith("}") || line.endsWith("},")) {
                    int id = Integer.parseInt(extrairValorJson(line, "id"));
                    String nome = extrairValorJson(line, "nome");
                    String email = extrairValorJson(line, "email");
                    String fone = extrairValorJson(line, "fone");

                    objetos.add(new Cliente(id, nome, email, fone));
                }
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("[AVISO DAO]: Não foi possível ler o arquivo JSON (" + e.getMessage() + ")");
        }
    }

    public void Salvar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_JSON))) {
            writer.write("[\n");
            for (int i = 0; i < objetos.size(); i++) {
                Cliente c = objetos.get(i);
                String json = String.format("  {\"id\": %d, \"nome\": \"%s\", \"email\": \"%s\", \"fone\": \"%s\"}",
                        c.getId(),
                        escaparJson(c.getNome()),
                        escaparJson(c.getEmail()),
                        escaparJson(c.getFone()));
                writer.write(json);
                if (i < objetos.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]\n");
        } catch (IOException e) {
            System.out.println("[ERRO DAO]: Falha ao salvar arquivo JSON: " + e.getMessage());
        }
    }

    private String extrairValorJson(String jsonLine, String chave) {
        String busca = "\"" + chave + "\":";
        int inicioChave = jsonLine.indexOf(busca);
        if (inicioChave == -1) return "";

        int inicioValor = inicioChave + busca.length();
        String sub = jsonLine.substring(inicioValor).trim();

        if (sub.startsWith("\"")) {
            int fimString = sub.indexOf("\"", 1);
            return sub.substring(1, fimString);
        } else {
            int fimNumero = sub.indexOf(",");
            if (fimNumero == -1) fimNumero = sub.indexOf("}");
            return sub.substring(0, fimNumero).trim();
        }
    }

    private String escaparJson(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "\\\"");
    }
}
