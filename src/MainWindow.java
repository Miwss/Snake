import javax.swing.*;
//JFrame - класс для любого окна, ыыыыы
public class MainWindow extends JFrame {

    //Окно самой игры
    public MainWindow(){
        setTitle("АНАКОНДА");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//кнопка выхода
        setSize(320,345);//размер окна
        setLocation(400,400);//размер игрового поля
        add(new GameField());
        setVisible(true);
    }
    //Создаем экзепляр окна, рофл
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}