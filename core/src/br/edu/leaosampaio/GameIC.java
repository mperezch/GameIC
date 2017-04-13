package br.edu.leaosampaio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameIC extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture fundo,fundo2;
	private Texture[] personagem;
    //private ShapeRenderer shape;


    //Atributos de configuração
    private float alturaDispositivo;
    private float larguraDispositivo;
    private float deltaTime;
    private float posicaoMovimentoHorizontal;
    private float posicaoMovimentoHorizontal2;
    private float variacao =0;
    private float velocidadeQueda =0;
    private float posicaoInicialVertical;
    private boolean pulou = false;
    private Circle circuloPersonagem;
    private Music music;

    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRUAL_WIDTH =1366;
    private final float VIRUAL_HEIGHT =1024;




	@Override
	public void create () {
        batch = new SpriteBatch();

        circuloPersonagem = new Circle();
       // shape = new ShapeRenderer();

        fundo = new Texture("city_night.jpg");
        fundo2 = new Texture("city_night.jpg");
        personagem = new Texture[3];
        personagem[0] = new Texture("man_stand.png");
        personagem[1]= new Texture("man_walk1.png");
        personagem[2] = new Texture("man_walk2.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("musicafunda.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();

      //alturaDispositivo = Gdx.graphics.getHeight();
      //larguraDispositivo = Gdx.graphics.getWidth();
       posicaoInicialVertical = 80;
        larguraDispositivo = VIRUAL_WIDTH;
        alturaDispositivo = VIRUAL_HEIGHT;


        camera = new OrthographicCamera();
        camera.position.set(VIRUAL_WIDTH /2,VIRUAL_HEIGHT /2,0);
        viewport = new StretchViewport(VIRUAL_WIDTH,VIRUAL_HEIGHT,camera);


	}

	@Override
	public void render () {
        camera.update();

        if(posicaoInicialVertical <= 90){
            pulou = false;
        }
        deltaTime = Gdx.graphics.getDeltaTime();

        posicaoMovimentoHorizontal -= deltaTime * 300;
        posicaoMovimentoHorizontal2 -= deltaTime * 300;


            if(Gdx.input.justTouched()){
                if(pulou != true){
                if(posicaoInicialVertical < 300){
                        velocidadeQueda = -20;
                    if(posicaoInicialVertical >= 300){
                        posicaoInicialVertical =300;
                        velocidadeQueda = 0;
                    }

                    }else{
                    pulou = true;
                }
                }
            }


        else{
            velocidadeQueda ++;
            if(posicaoInicialVertical > 90 || velocidadeQueda <0){
            posicaoInicialVertical = posicaoInicialVertical -velocidadeQueda;

            }

        }



        //Animação do personagem
        variacao += deltaTime * 5;
        if(variacao >2) variacao =0;

        //Fundo voltar para o final
        if(posicaoMovimentoHorizontal < - larguraDispositivo){
            posicaoMovimentoHorizontal = larguraDispositivo;
        }

        if(posicaoMovimentoHorizontal2 < -larguraDispositivo - larguraDispositivo){
            posicaoMovimentoHorizontal2 = posicaoMovimentoHorizontal;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(fundo,posicaoMovimentoHorizontal ,0 , larguraDispositivo,alturaDispositivo);
        batch.draw(fundo2,posicaoMovimentoHorizontal2 + larguraDispositivo ,0,larguraDispositivo,alturaDispositivo);
        batch.draw(personagem[(int) variacao],50,posicaoInicialVertical);



        batch.end();

        //Cria o circulo no personagem para detectar colisões
        circuloPersonagem.set(50 + personagem[0].getWidth() /2,posicaoInicialVertical + personagem[0].getHeight() /2 - 10,
                personagem[0].getWidth() /2 + 7);



       /* shape.begin(ShapeRenderer.ShapeType.Filled);

            shape.circle(circuloPersonagem.x,circuloPersonagem.y,circuloPersonagem.radius);
            shape.setColor(Color.RED);
        shape.end();
*/




	}


    @Override
	public void dispose () {
        super.dispose();
        music.dispose();

	}
    public void resize(int width, int height) {
        viewport.update(width,height);
    }
}
