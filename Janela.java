import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.JMenuBar;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.FlowLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.FileOutputStream;

/**
 * Escreva uma descrição da classe Janela aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Janela extends JFrame implements ChangeListener
{
    ControleApp controle;
    JPanel painelEditor, painelFundo;
    JLabel painelImagemOriginal;
    JLabel painelImagemEditada;
    JButton botaoEscolherImagem;
    JButton botaoHistogramaImagem;
    String caminhoImagem;
    Image mostrarImagem = null;
    Image mostrarImagemEditada = null;
    BufferedImage imagemOriginal = null;
    BufferedImage imagemEditada = null;
    boolean temImagem = false;
    Graphics gFundo;
    JComboBox comboFiltros;
    JSlider sliderIntensidade;
    JMenuBar menu;
    JMenu opcoesMenu;
    JMenuItem salvarItem;
    Filtros filtroImagem = Filtros.NONE;
    int intensidadeAnterior = 0;
    int valor2 = 0;
    int valorBrilho = 0;
    int valorContraste = 0;
    int valorCinza = 0;
    int salvarIndex = 0;
    Imagem img;
    
    Janela(){
        
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setTitle("Editor de Imagem");
        this.setLayout(new BorderLayout());
        
        menu = new JMenuBar();
        this.setJMenuBar(menu);
        
        opcoesMenu = new JMenu("Opções");
        menu.add(opcoesMenu);
        
        salvarItem = new JMenuItem("Salvar Imagem");
        opcoesMenu.add(salvarItem);
        
        iniciarPainelFundo();
        iniciarPainelImagemOriginal();
        iniciarPainelImagemEditada();
        iniciarPainelEditor();
        iniciarEventos();
    }
    
    private void iniciarPainelFundo(){
        painelFundo = new JPanel();
        painelFundo.setBackground(Color.LIGHT_GRAY);
        painelFundo.setLayout(new GridLayout(1, 2));
        this.add(painelFundo, BorderLayout.CENTER);
    }
    
    private void iniciarPainelImagemOriginal(){
        painelImagemOriginal = new JLabel();
        painelImagemOriginal.setHorizontalAlignment(JLabel.CENTER);
        painelImagemOriginal.setVerticalAlignment(JLabel.CENTER);
        painelImagemOriginal.setText("Original");
        //gFundo = painelImagemOriginal.getGraphics();
        painelFundo.add(painelImagemOriginal);
    }
    
    private void iniciarPainelImagemEditada(){
        painelImagemEditada = new JLabel();
        painelImagemEditada.setHorizontalAlignment(JLabel.CENTER);
        painelImagemEditada.setVerticalAlignment(JLabel.CENTER);
        painelImagemEditada.setText("Editada");
        //gFundo = painelImagemOriginal.getGraphics();
        painelFundo.add(painelImagemEditada);
    }
    private void iniciarPainelEditor(){
        painelEditor = new JPanel();
        painelEditor.setLayout(new GridLayout(18, 1));
        //painelEditor.setBackground(Color.BLUE);
        //painelEditor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botaoEscolherImagem = new JButton("Escolher Imagem");
        botaoHistogramaImagem = new JButton("Gerar Histograma");
        
        JLabel lblEspaco = new JLabel(" "); 
        JLabel lblFiltro = new JLabel("  Filtros: ");     
        comboFiltros = new JComboBox();
        comboFiltros.addItem("-nenhum-");
        comboFiltros.addItem("Brilho");
        comboFiltros.addItem("Contraste");
        comboFiltros.addItem("Tons de Cinza");
        
        JLabel lblIntensidade = new JLabel("  Intensidade: "); 
        sliderIntensidade = new JSlider(-10, 10, 0);
        //sliderIntensidade.setValue(0);
        //sliderIntensidade.setMaximum(100);
        //sliderIntensidade.setMinimum(0);
        sliderIntensidade.setPaintTrack(true);
        sliderIntensidade.addChangeListener(this);
        //sliderIntensidade.setPaintLabels(true);
        //sliderIntensidade.setPaintTicks(true);
        
        
        painelEditor.add(botaoEscolherImagem);
        painelEditor.add(lblEspaco);
        painelEditor.add(lblEspaco);
        //painelEditor.add(botaoHistogramaImagem);
        painelEditor.add(lblEspaco);
        painelEditor.add(lblFiltro);
        painelEditor.add(comboFiltros);
        painelEditor.add(lblIntensidade);
        painelEditor.add(sliderIntensidade);
        this.add(painelEditor, BorderLayout.EAST);
        this.setVisible(true);
    }
    
    private void iniciarEventos(){
        botaoEscolherImagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                //FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG Image", "jpg");
                int retorno = chooser.showOpenDialog(retornaJanela());
                
                if(retorno == JFileChooser.APPROVE_OPTION){
                    caminhoImagem = chooser.getSelectedFile().getPath();
                    //JOptionPane.showMessageDialog(null, caminhoImagem);
                    controle.carregarImagem(caminhoImagem);
                }
            }
        });
    
        comboFiltros.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(comboFiltros.getSelectedItem().equals("Brilho")){
                    filtroImagem = Filtros.BRILHO;
                    sliderIntensidade.setValue(valorBrilho);
                }
                else if(comboFiltros.getSelectedItem().equals("Contraste")){
                    filtroImagem = Filtros.CONTRASTE;
                    sliderIntensidade.setValue(valorContraste);
                }
                
                else if(comboFiltros.getSelectedItem().equals("Tons de Cinza")){
                    filtroImagem = Filtros.TONS_CINZA;
                    sliderIntensidade.setValue(valorCinza);
                }
                else{
                    filtroImagem = Filtros.NONE;
                }
            }
        });
        
        salvarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int op = Integer.parseInt(JOptionPane.showInputDialog("Deseja salvar a imagem editada?\n1- Sim\n2- Não"));
                if(op == 1){
                    controle.salvarImagem(imagemEditada); 
                }
            }
        });
    }
    
    public void stateChanged(ChangeEvent e)
    {
        if(!(imagemOriginal == null)){
            //Imagem img = new Imagem(imagemOriginal, filtroImagem);
            img.setFiltro(filtroImagem);
            boolean aumentar = true;
            //int valor = 0;
            int v = 0;
            //valor = sliderIntensidade.getValue()-intensidadeAnterior;
            if(sliderIntensidade.getValue() > intensidadeAnterior){
                v = sliderIntensidade.getValue()-intensidadeAnterior;
            }
            else{
                v = (intensidadeAnterior-sliderIntensidade.getValue())*-1;
                aumentar = false;
            }
            
            img.aplicarFiltro(v, aumentar);
            imagemEditada = img.getImagem();
        
            intensidadeAnterior = sliderIntensidade.getValue();
            
            mostrarImagemEditada = (Image)imagemEditada;
            ImageIcon icon = new ImageIcon(mostrarImagemEditada);
            controle.carregarImagemPainelEditada(icon);
            
            if(comboFiltros.getSelectedItem().equals("Brilho")){
                valorBrilho = sliderIntensidade.getValue();
                
            }
            else if(comboFiltros.getSelectedItem().equals("Contraste")){
                valorContraste = sliderIntensidade.getValue();
            }
            
            else if(comboFiltros.getSelectedItem().equals("Tons de Cinza")){
                valorCinza = sliderIntensidade.getValue();
            }
        }
    }
    
    private Janela retornaJanela(){
        return this;
    }

}
