import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

//JPanel тоже что и ДЖФРЕЙМ
public class GameField extends JPanel implements ActionListener{
    //Дефолтные игровые параметры
    private final int SIZE = 320; //поле
    private final int DOT_SIZE = 16; //размер секций АНАКОНДЫ
    private final int ALL_DOTS = 400;
    private Image dot;//Картинка точки
    private Image anime;//Картинка АНАКОНДЫ
    private int animeX;//координаты по иксу
    private int animeY;//координаты по Игрику
    private int[] x = new int[ALL_DOTS]; //храним все положения змейки
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer; //таймер)
    private boolean left = false;//-----
    private boolean right = true;//      |
    private boolean up = false;//        | - Движение
    private boolean down = false;//      |
    private boolean inGame = true;//----- //в игре или слились


    public GameField(){
        setBackground(Color.black);//задаем цвет поля
        loadImages(); //метод для загрузки картинок
        initGame();//Метод который инициализирет начало игры
        addKeyListener(new FieldKeyListener()); //
        setFocusable(true);

    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE; //начальная позиция для икса(48 - потому что кратно 16, бо наша точка 16 пикселей)
            y[i] = 48; //старт позиция для игрика
        }
        //скорось тика змейки, чем меньше тем ближе к космому
        timer = new Timer(100,this);
        timer.start();
        createApple();
    }
    //создает поинт в рандомном месте поля
    //Если хотим увеличить к-тво поинтов, то надо увеличить поле, можем поместить только 20
    //Мы прорисовываем из максимального числа только 1 поинт
    public void createApple(){
        animeX = new Random().nextInt(20)*DOT_SIZE;
        animeY = new Random().nextInt(20)*DOT_SIZE;
    }
    //Тот самый метод для картинок
    public void loadImages(){
        ImageIcon iia = new ImageIcon("naruto.jpg");
        anime = iia.getImage();
        ImageIcon iid = new ImageIcon("sharingan.png");
        dot = iid.getImage();
    }
    //Перерисовывет компоненты
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //А вот тут, мы перерисовываем игровые картинки
        if(inGame){
            g.drawImage(anime, animeX, animeY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "Награвся!)";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            // g.setFont(f);
            g.drawString(str,125,SIZE/2);
        }
    }
    //Движение
    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //Тут перемещаем голову
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }
    //Если поели, то увеличиваем массу)
    public void checkApple(){
        if(x[0] == animeX && y[0] == animeY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        //Проверка не врезались ли мы сами в себя
        for (int i = dots; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        //Проверка рамок, если где то доходим до 0, то смерть
        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }
    //метод с интерфейса который обновляет тики
    //Если мы в игре, то вызываем методы
    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();//если вышел за границу, то ггвп, колизия с рамкой
            move();

        }
        repaint();
    }
    //Обработка нажатия клавишь
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }


}