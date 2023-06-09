public class ContaTerminal {

    private static double SALDO = 0.0;
    private static String extrato = "";
    private static final String MENSAGEM_OPCAO_VALIDA = "Informe uma opção válida!";

    public static void main(String[] args) {

        outer:
        while (true) {
            System.out.println("\nOpções:");
            System.out.println("1 - Saldo, 2 - Depósito, 3 - Saque, 4 - Extrato, 5 - Sair.");

            int opcao;

            try {
                opcao = Integer.parseInt(lerEntrada("Informe a opção desejada: "));
            } catch (Exception e) {
                System.out.println();
                System.out.println(MENSAGEM_OPCAO_VALIDA);
                continue;
            }
            System.out.println();

            switch (opcao) {
                case 1:
                    System.out.println(consultarSaldo());
                    break;
                case 2:
                    try {
                        depositar();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                case 3:
                    try {
                        sacar();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                case 4:
                    consultarExtrato();
                    break;
                case 5:
                    System.out.println("Processo finalizado.");
                    break outer;
                default:
                    System.out.println(MENSAGEM_OPCAO_VALIDA);
            }
        }
    }

    private static String lerEntrada(String solicitacao) {
        System.out.print(solicitacao);
        return new java.util.Scanner(System.in).nextLine();
    }

    private static String consultarSaldo(){
        return String.format("Seu saldo é R$ %.2f.", SALDO);
    }

    private static void depositar() throws Exception {
        double deposito;

        try {
            deposito = Double.parseDouble(lerEntrada("Informe o valor do depósito: "));
        } catch (Exception e) {
            throw new Exception("Informe uma quantia de depósito válida!");
        }

        if (deposito <= 0) {
            throw new Exception("Informe uma quantia de depósito positiva!");
        }

        SALDO += deposito;
        String operacao = String.format("Depositou R$ %.2f", deposito);
        System.out.println(operacao);
        extrato = extrato.isBlank() ? operacao : String.join("\n", extrato, operacao);
    }

    private static void sacar() throws Exception {
        double saque;

        if (SALDO <= 0) {
            System.out.println(consultarSaldo());
            throw new Exception("Não é possível sacar.");
        }

        try {
            saque = Double.parseDouble(lerEntrada("Informe o valor do saque: "));
        } catch (Exception e) {
            throw new Exception("Informe uma quantia de saque válida!");
        }

        if (saque <= 0) {
            throw new Exception("Informe uma quantia de saque positiva!");
        }
        if (SALDO - saque < 0) {
            throw new Exception("Saque não pode ser maior que o saldo!");
        }

        SALDO -= saque;
        String operacao = String.format("Sacou R$ %.2f", saque);
        System.out.println(operacao);
        extrato = extrato.isBlank() ? operacao : String.join("\n", extrato, operacao);
    }

    private static void consultarExtrato(){
        System.out.println("Extrato:");
        System.out.print(extrato.isBlank() ? extrato : extrato + "\n");
        System.out.println(consultarSaldo());
    }
}