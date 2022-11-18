
/**
 * Escreva uma descrição da classe App aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class App
{
    Janela j;
    ControleApp ap;
    
    public static void main(String[] args){
        Janela j = new Janela();
        ControleApp ca = new ControleApp();
        j.controle = ca;
        ca.janela = j;
    }
}
