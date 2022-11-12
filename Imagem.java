import java.awt.image.*;
import java.awt.Color;

/**
 * Escreva uma descrição da classe Imagem aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Imagem
{
    private BufferedImage img;
    private Filtros filtro;
    private int[] rgbOriginal;    
    RescaleOp res;
    private static int brilhoEscala = 0;
    private double contrasteEscala = 1.0;
    
    Imagem(BufferedImage i){
        this.img = i;
    }
    Imagem(BufferedImage i, Filtros f){
        this.img = i;
        this.filtro = f;
    }
    
    public void setFiltro(Filtros f){
        this.filtro = f;
    }
    
    public BufferedImage getImagem(){
        return this.img;
    }
    
    public Filtros getFiltro(){
        return this.filtro;
    }
    
    public int ajustarValor(int v){
        if(v > 255){
            v = 255;
        }
        
        else if(v < -255){
            v = -255;
        }
        
        return v;
    }
    
    public void mudarEscalaBrilho(boolean aumentar){
        if(aumentar){
            if(brilhoEscala < 255)
                brilhoEscala++;
        }
        else{
            if(brilhoEscala > 0){
                brilhoEscala--; 
            }
        }
    }
    
    public void aplicarFiltro(int intensidade, boolean aumentar){
        if(this.filtro == Filtros.BRILHO){
            int[] rgb;
        
            /*for(int i = 0; i < this.img.getWidth(); i++){
                for(int j = 0; j < this.img.getHeight(); j++){
                    rgb = img.getRaster().getPixel(i, j, new int[3]);
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    mudarEscalaBrilho(aumentar);
                    red = ajustarValor(rgb[0] + intensidade);
                    green = ajustarValor(rgb[1] + intensidade);
                    blue = ajustarValor(rgb[2] + intensidade);
                    
                    int array[] = {red, green, blue};
                    
                    this.img.getRaster().setPixel(i, j, array);
                }
                
            }*/
            
            BufferedImage img2 = this.img;
            if(aumentar)
                res = new RescaleOp(1.0f, 10, null);
            else
                res = new RescaleOp(1.0f, -10, null);
            this.img = res.filter(this.img, this.img);
        }
        
        else if(this.filtro == Filtros.CONTRASTE){
            if(aumentar)
                contrasteEscala += 0.1f;
            else
                contrasteEscala -= 0.1f;
            
            if(aumentar)
                res = new RescaleOp(1.1f, 0, null);
            else
                res = new RescaleOp(0.9f, 0, null);
            this.img = res.filter(this.img, this.img);
            
        }
        
        else if(this.filtro == Filtros.TONS_CINZA){
            int[] rgb;
            for(int i = 0; i < this.img.getWidth(); i++){
                for(int j = 0; j < this.img.getHeight(); j++){
                    rgb = img.getRaster().getPixel(i, j, new int[3]);
                    Color cor = new Color(this.img.getRGB(i, j));
                    Color novaCor = null;
                    int red = (int)(cor.getRed()*0.299);
                    int green = (int)(cor.getGreen()*0.587);
                    int blue = (int)(cor.getBlue()*0.114);
                    int avg = (rgb[0]+rgb[1]+rgb[2])/3;
                    
                    //if(aumentar)
                        novaCor = new Color(red+green+blue, red+green+blue, red+green+blue);
                    //else 
                        //novaCor = new Color(red-green-blue, red-green-blue, red-green-blue);

                    
                    this.img.setRGB(i, j, novaCor.getRGB());
                }
                
            }         
        }
    
    }
}
