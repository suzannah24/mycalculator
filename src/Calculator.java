import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;






// Model.java
class CalculatorModel {
    private double num1 = 0;  //Initialize num1, num2, result to zero before performing calculations
    private double num2 = 0;
    private double result = 0;
    private char operator; // operator for calculation (+, -, *, /)

    public void setNum1(double num) {
        this.num1 = num;
    }

    public void setNum2(double num) {
        this.num2 = num;
    }

    public void setOperator(char op) {
        this.operator = op;
    }

    public double calculate() { //Perform the calculation based on operator
        switch(operator) {
            case '+': result = num1 + num2; break;
            case '-': result = num1 - num2; break;
            case '*': result = num1 * num2; break;
            case '/': result = num1 / num2; break;
        }
        num1 = result;
        return result;
    }
}

// View.java
class CalculatorView extends JFrame {
    private JTextField textfield;
    private JButton[] numberButtons;
    private JButton[] functionButtons;
    private JPanel panel;
    private JPanel backgroundPanel;
    private JSlider themeSlider;
    private Color darkColor = new Color(0, 0, 50);
    private Color lightColor = new Color(255, 255, 255);
    private Font myFont = new Font("Comic Sans MS", Font.BOLD, 30);
    private JLabel[] cornerLabels;

    public CalculatorView() { //Constructor to initialize the calculator view
        setupFrame();
        setupComponents();
        setupLayout();
        setupTheme();
        setupCornerImages();
        this.setVisible(true);
    }

    private void setupFrame() {
        this.setTitle("Maths is FUN!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 600);
        this.setLayout(null);
    }

    private void setupComponents() {
        // Text field for input and output
        textfield = new JTextField();
        textfield.setBounds(50, 25, 300, 50);
        textfield.setFont(myFont);
        textfield.setEditable(false);

        // Initialize functions buttons for operators and utilities
        functionButtons = new JButton[9];
        String[] functionSymbols = {"+", "-", "*", "/", ".", "=", "Del", "Clr", "(-)"};
        for(int i = 0; i < 9; i++) {
            functionButtons[i] = new JButton(functionSymbols[i]);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false);
        }

        //Initialize number buttons 0-9
        numberButtons = new JButton[10];
        for(int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }
    }

    // Arranging components within the frame
    private void setupLayout() {
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        // Add buttons to panel in the correct order
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(functionButtons[0]); // +
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(functionButtons[1]); // -
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(functionButtons[2]); // *
        panel.add(functionButtons[4]); // .
        panel.add(numberButtons[0]);
        panel.add(functionButtons[5]); // =
        panel.add(functionButtons[3]); // /

        //Position for Del, Clr and (-) buttons
        functionButtons[8].setBounds(50, 410, 100, 50);  // (-)
        functionButtons[6].setBounds(150, 410, 100, 50); // Del
        functionButtons[7].setBounds(250, 410, 100, 50); // Clr

        // Theme slider
        themeSlider = new JSlider(0, 100, 50);
        themeSlider.setBounds(50, 500, 300, 40);
    }

    private void setupTheme() {
        // Creating custom panel for the background
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                float ratio = themeSlider.getValue() / 100f; //Slider value
                Color currentColor = interpolateColor(darkColor, lightColor, ratio); //Interpolated Color
                g2d.setColor(currentColor); //Set background color
                g2d.fillRect(0, 0, getWidth(), getHeight()); //Filling in the panel
            }
        };
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 420, 600); //Covering entire frame

        JLabel sliderLabel = new JLabel("Slide me to change color!");
        sliderLabel.setBounds(50, 470, 300, 20); // Position above the slider
        sliderLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        sliderLabel.setForeground(Color.BLACK);
        sliderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        backgroundPanel.add(panel);
        backgroundPanel.add(functionButtons[8]); // (-)
        backgroundPanel.add(functionButtons[6]); // Del
        backgroundPanel.add(functionButtons[7]); // Clr
        backgroundPanel.add(textfield);
        backgroundPanel.add(themeSlider);
        backgroundPanel.add(sliderLabel);

        this.add(backgroundPanel);
    }

    private void setupCornerImages() {
        try {
            cornerLabels = new JLabel[4];
            InputStream imageStream = getClass().getResourceAsStream("/animal.png");
            if (imageStream == null) {
                System.out.println("Image not found!");
                return;
            }
            BufferedImage originalImage = ImageIO.read(imageStream);
            int cornerSize = 40;
            Image scaledImage = originalImage.getScaledInstance(cornerSize, cornerSize, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            Point[] positions = {
                    new Point(10, 10),   // Top left
                    new Point(370, 10),  // Top right
                    new Point(10, 520),  // Bottom left
                    new Point(370, 520)  // Bottom right
            };

            for(int i = 0; i < 4; i++) {
                cornerLabels[i] = new JLabel(scaledIcon);
                cornerLabels[i].setBounds(positions[i].x, positions[i].y, cornerSize, cornerSize);
                cornerLabels[i].setOpaque(false);
                backgroundPanel.add(cornerLabels[i]);
            }
        } catch (Exception e) {
            System.out.println("Error loading animal.png: " + e.getMessage());
        }
    }

    public void updateTheme(int value) {
        float ratio = value / 100f;
        Color bgColor = interpolateColor(darkColor, lightColor, ratio);
        Color buttonColor = interpolateColor(
                new Color(20, 20, 70),
                new Color(230, 230, 255),
                ratio
        );
        Color textColor = ratio > 0.5 ? Color.BLACK : Color.WHITE;

        backgroundPanel.setBackground(bgColor);
        textfield.setBackground(buttonColor);
        textfield.setForeground(textColor);

        for(JButton btn : numberButtons) {
            if(btn != null) {
                btn.setBackground(buttonColor);
                btn.setForeground(textColor);
            }
        }
        for(JButton btn : functionButtons) {
            btn.setBackground(buttonColor);
            btn.setForeground(textColor);
        }
    }

    private Color interpolateColor(Color c1, Color c2, float ratio) {
        int red = (int)(c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int green = (int)(c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int blue = (int)(c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(red, green, blue);
    }

    // Getters for Controller
    public JTextField getTextField() { return textfield; }
    public JButton[] getNumberButtons() { return numberButtons; }
    public JButton[] getFunctionButtons() { return functionButtons; }
    public JSlider getThemeSlider() { return themeSlider; }
}

// Controller.java
class CalculatorController {
    private CalculatorModel model;
    private CalculatorView view;
    private String currentInput = "";

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        initializeController();
    }

    private void initializeController() {
        // Add number button listeners
        for(int i = 0; i < view.getNumberButtons().length; i++) {
            final int number = i;
            view.getNumberButtons()[i].addActionListener(e -> {
                currentInput += number;
                view.getTextField().setText(currentInput);
            });
        }

        // Add function button listeners
        JButton[] functionButtons = view.getFunctionButtons();

        // Add (+)
        functionButtons[0].addActionListener(e -> handleOperator('+'));

        // Subtract (-)
        functionButtons[1].addActionListener(e -> handleOperator('-'));

        // Multiply (*)
        functionButtons[2].addActionListener(e -> handleOperator('*'));

        // Divide (/)
        functionButtons[3].addActionListener(e -> handleOperator('/'));

        // Decimal (.)
        functionButtons[4].addActionListener(e -> {
            if(!currentInput.contains(".")) {
                currentInput += ".";
                view.getTextField().setText(currentInput);
            }
        });

        // Equals (=)
        functionButtons[5].addActionListener(e -> {
            if(!currentInput.isEmpty()) {
                model.setNum2(Double.parseDouble(currentInput));
                double result = model.calculate();
                view.getTextField().setText(String.valueOf(result));
                currentInput = String.valueOf(result);
            }
        });

        // Delete
        functionButtons[6].addActionListener(e -> {
            if(!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                view.getTextField().setText(currentInput);
            }
        });

        // Clear
        functionButtons[7].addActionListener(e -> {
            currentInput = "";
            view.getTextField().setText("");
        });

        // Negate
        functionButtons[8].addActionListener(e -> {
            if(!currentInput.isEmpty()) {
                double value = Double.parseDouble(currentInput) * -1;
                currentInput = String.valueOf(value);
                view.getTextField().setText(currentInput);
            }
        });

        // Theme slider listener
        view.getThemeSlider().addChangeListener(e ->
                view.updateTheme(view.getThemeSlider().getValue())
        );
    }

    private void handleOperator(char op) {
        if (!currentInput.isEmpty()) {
            model.setNum1(Double.parseDouble(currentInput));
            model.setOperator(op);
            currentInput = "";
            view.getTextField().setText("");
        } else {
            view.getTextField().setText("Enter a number first");
        }
    }
}

// Main.java
public class Calculator {
    public static void main(String[] args) {

        // Get port from environment, default to 10000
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "10000"));
        System.out.println("Starting HTTP server on port: " + port);

        try {
            // Create a basic HTTP server
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new MyHttpHandler());
            server.setExecutor(null); // Default executor
            server.start();
            System.out.println("HTTP Server started at http://localhost:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Needed for GUI to work on certain systems
        System.setProperty("DISPLAY", ":99");

        SwingUtilities.invokeLater(() -> {
            CalculatorModel model = new CalculatorModel();
            CalculatorView view = new CalculatorView();
            CalculatorController controller = new CalculatorController(model, view);
        });
    }

    // Simple HTTP handler to return a response
    static class MyHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Maths is FUN! Calculator is running.";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}