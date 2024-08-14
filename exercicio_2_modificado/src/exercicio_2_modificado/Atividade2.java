package exercicio_2_modificado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Atividade2 {
    
    private final String url = "jdbc:postgresql://localhost/exercicio2";
    private final String user = "postgres";
    private final String password = "32109860";
    
    private Connection connect() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS mesa (" +
                     "codigo INT PRIMARY KEY," +
                     "nome VARCHAR(100) NOT NULL," +
                     "idade INT NOT NULL" +
                     ")";
        
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addRecord(int codigo, String nome, int idade) {
        String sql = "INSERT INTO mesa (codigo, nome, idade) VALUES (?, ?, ?)";
        
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            pstmt.setString(2, nome);
            pstmt.setInt(3, idade);
            pstmt.executeUpdate();
            System.out.println("Registro adicionado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void removeRecord(int codigo) {
        String sql = "DELETE FROM mesa WHERE codigo = ?";
        
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            pstmt.executeUpdate();
            System.out.println("Registro removido com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void updateRecord(int codigo, String nome, int idade) {
        String sql = "UPDATE mesa SET nome = ?, idade = ? WHERE codigo = ?";
        
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.setInt(3, codigo);
            pstmt.executeUpdate();
            System.out.println("Registro atualizado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Atividade2 sqlConnect = new Atividade2();
        sqlConnect.createTable();
        
        Scanner sc = new Scanner(System.in);
        int x = 0;
        boolean fim = true;
        
        while (fim) {
            System.out.println("1-adicionar");
            System.out.println("2-remover");
            System.out.println("3-editar");
            System.out.println("4-sair");
            x = sc.nextInt();
            
            switch (x) {
                case 1:
                    System.out.println("Digite o código:");
                    int codigo = sc.nextInt();
                    System.out.println("Digite o nome:");
                    sc.nextLine(); // Consumir o newline restante
                    String nome = sc.nextLine();
                    System.out.println("Digite a idade:");
                    int idade = sc.nextInt();
                    sqlConnect.addRecord(codigo, nome, idade);
                    break;
                case 2:
                    System.out.println("Digite o código do registro a ser removido:");
                    codigo = sc.nextInt();
                    sqlConnect.removeRecord(codigo);
                    break;
                case 3:
                    System.out.println("Digite o código do registro a ser atualizado:");
                    codigo = sc.nextInt();
                    System.out.println("Digite o novo nome:");
                    sc.nextLine(); // Consumir o newline restante
                    nome = sc.nextLine();
                    System.out.println("Digite a nova idade:");
                    idade = sc.nextInt();
                    sqlConnect.updateRecord(codigo, nome, idade);
                    break;
                case 4:
                    fim = false;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
        
        sc.close();
    }
}

