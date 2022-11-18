import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.*;
import java.io.FileOutputStream;

/**
 * Escreva uma descrição da classe ControleApp aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class ControleApp
{
    Janela janela;
    int salvarIndex = 0;
    public void carregarImagem(String caminho){
    
        try
        {
            File file = new File(caminho);
            janela.imagemOriginal = new BufferedImage(janela.painelFundo.getWidth()/2, janela.painelFundo.getHeight(), BufferedImage.TYPE_INT_ARGB);
            janela.imagemOriginal = ImageIO.read(file);
            janela.imagemEditada = janela.imagemOriginal;
            janela.mostrarImagem = (Image)janela.imagemOriginal;
            
            ImageIcon icon = new ImageIcon(janela.mostrarImagem);
            carregarImagemPainelOriginal(icon);
            carregarImagemPainelEditada(icon);
            janela.sliderIntensidade.setValue(0);
            
            janela.img = new Imagem(janela.imagemOriginal);
            
            janela.valorBrilho = 0;
            janela.valorContraste = 0;
            janela.valorCinza = 0;
            
        }
        catch (Exception ioe)
        {
            JOptionPane.showMessageDialog(null, ioe.getMessage());
        }
    }
    
    public void salvarImagem(BufferedImage img){
        
        try{
            int op = Integer.parseInt(JOptionPane.showInputDialog("Deseja salvar a imagem editada?\n1- Sim\n2- Não"));
            if(op == 1){
                salvarIndex++;
                FileOutputStream fos = new FileOutputStream("nova"+salvarIndex+".jpg");
                ImageIO.write(img, "jpg", fos);
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
    public void carregarImagemPainelOriginal(ImageIcon ico){
        janela.painelImagemOriginal.setIcon(ico); 
        janela.painelImagemOriginal.setText("");           
    }
    
    public void carregarImagemPainelEditada(ImageIcon ico){
        janela.painelImagemEditada.setIcon(ico); 
        janela.painelImagemEditada.setText("");           
    }
}
