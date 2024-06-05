package bsu.rfe.java.group7.lab6.ravgeysh.varС1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel {
    // Флаг приостановленности движения
    private boolean paused;
    // Динамический список скачущих мячей
    private final BouncingBall[] balls = new BouncingBall[100];
    private int ballIndex = 0;

    private double clickX;
    private double clickY;
    private Optional<BouncingBall> clickedBall = Optional.empty();

    // Конструктор класса BouncingBall
    public Field() {
// Установить цвет заднего фона белым
        setBackground(Color.WHITE);
// Запустить таймер
        // Класс таймер отвечает за регулярную генерацию событий ActionEvent
        // При создании его экземпляра используется анонимный класс,
        // реализующий интерфейс ActionListener
        // Задача обработчика события ActionEvent - перерисовка окна
        Timer repaintTimer = new Timer(10, ev -> {
// Задача обработчика события ActionEvent - перерисовка окна
            //checkCollision();
            repaint();
        });

        addMouseListener(new BallMouseListener());
        addMouseMotionListener(new BallMouseMotionListener());

        repaintTimer.start();
    }

    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
// Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
// Последовательно запросить прорисовку от всех мячей из списка
        for (BouncingBall ball : balls) {
            if (ball != null) {
                ball.paint(canvas);
            }
        }
    }

//    private void checkCollision() {
//        for (int i = 0; i < balls.length - 1; i++) {
//            for (int j = i + 1; j < balls.length; j++) {
//                if (balls[i] == null || balls[j] == null) {
//                    continue;
//                }
//                double distance = Math.pow(
//                        Math.pow(balls[i].getX() - balls[j].getX(), 2) +
//                                Math.pow(balls[i].getY() - balls[j].getY(), 2),
//                        0.5);
//                if (distance <= balls[i].getRadius() + balls[j].getRadius()) {
//
//                    double cx = balls[j].getX() - balls[i].getX();
//                    double cy = balls[j].getY() - balls[i].getY();
//
//                    // Вектор C (вектор, соединяющий центры шаров).
//
//                    double cSqr = cx * cx + cy * cy;
//
//                    // Скалярное произведение векторов.
//                    double scalar1 = balls[i].getSpeedX() * cx +
//                            balls[i].getSpeedY() * cy;
//
//                    double scalar2 = balls[j].getSpeedX() * cx +
//                            balls[j].getSpeedY() * cy;
//
//                    // Разложение скорости шара № 1 на нормальную и тагенсальную.
//
//                    double ball1Nvx = (cx * scalar1) / cSqr;
//                    double ball1Nvy = (cy * scalar2) / cSqr;
//                    double ball1Tvx = balls[i].getSpeedX() - ball1Nvx;
//                    double ball1Tvy = balls[i].getSpeedY() - ball1Nvy;
//
//                    // Разложение скорости шара № 2 на нормальную и тагенсальную.
//
//                    double ball2Nvx = (cx * scalar2) / cSqr;
//                    double ball2Nvy = (cy * scalar2) / cSqr;
//                    double ball2Tvx = balls[j].getSpeedX() - ball2Nvx;
//                    double ball2Tvy = balls[j].getSpeedY() - ball2Nvy;
//
//                    // Реализация обмена нормальными скоростями
//                    // (тагенсальные остаются неизменными).
//
//                    //производим пересчет тангенсальных скоростей
//                    //учитывая массу
//                    //так как масса в нашем случае зависит от объема, а объем
//                    //зависит от куба радиуса, я обозначу
//                    //куб объемя массой
//
//                    double tempX = ball1Nvx;
//                    double tempY = ball1Nvy;
//
//                    double m1 = Math.pow(balls[i].getRadius(), 3);
//                    double m2 = Math.pow(balls[j].getRadius(), 3);
//
//                    ball1Nvx = (m1 - m2) * ball1Nvx / (m1 + m2) +
//                            2 * m2 * ball2Nvx  / (m1 + m2);
//
//                    ball1Nvy = (m1 - m2) * ball1Nvy / (m1 + m2) +
//                            2 * m2 * ball2Nvy  / (m1 + m2);
//
//                    ball2Nvx = 2 * m1 * tempX / (m1 + m2)  +
//                            (m2 - m1) * ball2Nvx / (m1 + m2);
//
//                    ball2Nvy = 2 * m1 * tempY / (m1 + m2)  +
//                            (m2 - m1) * ball2Nvy / (m1 + m2);
//
//                    balls[i].setSpeed(
//                            ball1Nvx + ball1Tvx,
//                            ball1Nvy + ball1Tvy
//                    );
//
//                    balls[j].setSpeed(
//                            ball2Nvx + ball2Tvx,
//                            ball2Nvy + ball2Tvy
//                    );
//                }
//
//            }
//        }
//    }

    // Метод добавления нового мяча в список
    public void addBall() {
//Заключается в добавлении в список нового экземпляра BouncingBall
// Всю инициализацию положения, скорости, размера, цвета
// BouncingBall выполняет сам в конструкторе
        balls[ballIndex] = new BouncingBall(this);
        ballIndex++;
        if (ballIndex == balls.length) {
            ballIndex = 0;
        }
    }

    // Метод синхронизированный, т.е. только один поток может
// одновременно быть внутри
    public synchronized void pause() {
// Включить режим паузы
        paused = true;
    }

    // Метод синхронизированный, т.е. только один поток может
// одновременно быть внутри
    public synchronized void resume() {
// Выключить режим паузы
        paused = false;
// Будим все ожидающие продолжения потоки
        notifyAll();
    }

    // Синхронизированный метод проверки, может ли мяч двигаться
// (не включен ли режим паузы?)
    public synchronized void canMove() throws
            InterruptedException {
        if (paused) {
// Если режим паузы включен, то поток, зашедший
// внутрь данного метода, засыпает
            wait();
        }
    }

    private Optional<BouncingBall> getBallClicked(MouseEvent me) {
        for (BouncingBall ball : balls) {
            if (ball == null) {
                break;
            }
            double distance =
                    Math.pow((me.getX() - ball.getX()), 2) +
                            Math.pow((me.getY() - ball.getY()), 2);
            if (distance <= ball.getRadius() * ball.getRadius()) {
                return Optional.of(ball);
            }
        }
        return Optional.empty();
    }

    public class BallMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            var ball = getBallClicked(mouseEvent);
            if (ball.isPresent()) {
                pause();
                clickedBall = ball;
                clickX = mouseEvent.getX();
                clickY = mouseEvent.getY();
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if (clickedBall.isPresent()) {
                double newSpeedX = (mouseEvent.getX() - clickX) / 20;
                double newSpeedY = (mouseEvent.getY() - clickY) / 20;
                clickedBall.get()
                        .setSpeed(
                                newSpeedX % 15,
                                newSpeedY % 15
                        );
                resume();
                clickedBall = Optional.empty();
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    public class BallMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }
}